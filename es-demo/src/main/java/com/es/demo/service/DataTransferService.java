package com.es.demo.service;

import com.es.demo.config.SnowflakeIdWorker;
import com.es.demo.mapper.RiskMessageMapper;
import com.es.demo.model.mybatis.RiskMessage;
import com.es.demo.repository.RiskAddressBookRepository;
import com.es.demo.repository.RiskCallRecordRepository;
import com.es.demo.repository.RiskDeviceInfoRepository;
import com.es.demo.repository.RiskMessageRepository;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
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
 * @create 2019-07-09
 */
@Slf4j
@Service
public class DataTransferService {

    @Autowired
    private RiskAddressBookRepository addressBookRepository;

    @Autowired
    private RiskCallRecordRepository callRecordRepository;

    @Autowired
    private RiskMessageRepository messageRepository;

    @Autowired
    private RiskDeviceInfoRepository deviceInfoRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RiskMessageMapper riskMessageMapper;

    @Autowired
    private ThreadPoolTaskExecutor loadDataTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor importDataTaskExecutor;

    public static final int pageSize = 20000;


    public void startRiskMessage(Date startDate, Integer pageSize2) {
        // 1. 获取数据库数据总数
        RiskMessage riskMessage = new RiskMessage();
        int selectCount = riskMessageMapper.selectCount(riskMessage);
        Long count = (long) selectCount;
        // 2. 10K/page/thread
        Long page = count / pageSize + 1;

        LocalDateTime startTime = LocalDateTime.now();
        log.info("开始执行从 mysql 导入数据到 elastic 中：{}" + startTime);
        for (int i = 1; i <= page; i++) {

            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            int pageI = (i-1) * pageSize;
            CompletableFuture
                    .supplyAsync(() -> this.queryPageList(pageI), loadDataTaskExecutor)
                    .thenAcceptAsync(riskMessageList -> this.saveMessageToES(riskMessageList), importDataTaskExecutor);

        }

        LocalDateTime endTime = LocalDateTime.now();
        log.info("完成执行从 mysql 导入数据到 elastic 中：{}, 耗时： {}" + endTime, Duration.between(startTime, endTime).getSeconds());


    }

    private void saveMessageToES(List<RiskMessage> riskMessageList) {

        List<com.es.demo.model.elastic.RiskMessage> riskMessageArrayList = new ArrayList<>(pageSize);
        int size = riskMessageList.size() ;
        com.es.demo.model.elastic.RiskMessage riskMessage2;
        RiskMessage riskMessage;
        for (int i = 0; i < size; i++) {
            riskMessage = riskMessageList.get(i);
            riskMessage2 = new com.es.demo.model.elastic.RiskMessage();

            riskMessage2.setId(snowflakeIdWorker.nextId());
            riskMessage2.setAddress(riskMessage.getAddress());
            riskMessage2.setBody(riskMessage.getBody());
            riskMessage2.setCreateTime(riskMessage.getCreateTime());
            riskMessage2.setOrderNo(riskMessage.getOrderNum());
            riskMessage2.setRead(riskMessage.getRead());
            riskMessage2.setSendTime(riskMessage.getSendTime());
            riskMessage2.setType(riskMessage.getType());
            riskMessageArrayList.add(riskMessage2);
            }
        messageRepository.saveAll(riskMessageArrayList);
    }

    private List<RiskMessage> queryPageList(Integer page) {
        // PageHelper.startPage(page, pageSize);
        List<RiskMessage> riskMessages = riskMessageMapper.queryPageList(page, pageSize);
        return riskMessages;
    }

}
