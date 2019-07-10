package com.es.demo.service;

import com.es.demo.config.SnowflakeIdWorker;
import com.es.demo.mapper.RiskAddressBookMapper;
import com.es.demo.model.mybatis.RiskAddressBook;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
@Slf4j
@Service
public class RiskAddressBookService {

    @Autowired
    private RiskAddressBookMapper riskAddressBookMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private ThreadPoolTaskExecutor loadDataTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor importDataTaskExecutor;

    @Autowired
    private RiskCallRecordService riskCallRecordService;

    public static final int pageSize = 50000;

    public void startRiskAddressBook(Date startDate, Integer pageSize) {
        // 1. 获取数据库数据总数
        RiskAddressBook riskAddressBook = new RiskAddressBook();
        int selectCount = riskAddressBookMapper.selectCount(riskAddressBook);
        Long count = (long) selectCount;
        // 2. 10K/page/thread
        Long page = count / pageSize + 1;

        LocalDateTime startTime = LocalDateTime.now();
        log.info("开始执行从 mysql 导入数据到 elastic 中：{}" + startTime);
        for (int i = 1; i <= page; i++) {

            if (i != 1) {
                try {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            int pageI = (i - 1) * pageSize;
            CompletableFuture
                    .supplyAsync(() -> this.queryPageList(pageI), loadDataTaskExecutor)
                    .thenAcceptAsync(riskAddressBookList -> this.saveMessageToES(riskAddressBookList), importDataTaskExecutor);

        }

        LocalDateTime endTime = LocalDateTime.now();
        log.info("完成执行从 mysql 导入数据到 elastic 中：{}, 耗时： {}" + endTime, Duration.between(startTime, endTime).getSeconds());
    }

    private void saveMessageToES(List<RiskAddressBook> riskAddressBookList) {

        // List<com.es.demo.model.elastic.RiskAddressBook> esRiskAddressBookList = new ArrayList<>(pageSize);
        int size = riskAddressBookList.size();

        com.es.demo.model.elastic.RiskAddressBook riskAddressBook2;
        RiskAddressBook riskAddressBook;
        IndexQuery indexQuery;

        List<IndexQuery> indexQueryList = new ArrayList<>(pageSize);
        Date date = new Date();
        String matchNewPhone;
        for (int i = 0; i < size; i++) {

            riskAddressBook = riskAddressBookList.get(i);
            matchNewPhone = riskCallRecordService.matchNewPhone(riskAddressBook.getPhone());
            if (StringUtils.isBlank(matchNewPhone)) {
                continue;
            }

            indexQuery = new IndexQuery();
            riskAddressBook2 = new com.es.demo.model.elastic.RiskAddressBook();
            riskAddressBook2.setId(snowflakeIdWorker.nextId());
            riskAddressBook2.setUpdateTime(date);
            riskAddressBook2.setRepeatCount(0L);
            riskAddressBook2.setRelation(0L);
            riskAddressBook2.setRegion("");
            riskAddressBook2.setPhone("");
            riskAddressBook2.setOrderNo(riskAddressBook.getOrderNum());
            riskAddressBook2.setName(riskAddressBook.getName());
            riskAddressBook2.setLastContactTime(null);
            riskAddressBook2.setCreateTime(riskAddressBook.getCreateTime());
            riskAddressBook2.setContactCount(0L);

            indexQuery.setIndexName("risk_address_book");
            indexQuery.setObject(riskAddressBook2);
            indexQueryList.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(indexQueryList);
    }

    private List<RiskAddressBook> queryPageList(Integer page) {
        return riskAddressBookMapper.queryPageList(page, pageSize);
    }


}
