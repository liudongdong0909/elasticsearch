package com.es.demo.service;

import com.es.demo.EsDemoApplicationTests;
import com.es.demo.mapper.RiskAddressBookMapper;
import com.es.demo.model.mybatis.RiskAddressBook;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
@Slf4j
public class MySQL2ElasticTest extends EsDemoApplicationTests {

    @Autowired
    private RiskAddressBookMapper riskAddressBookMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RiskAddressBookService riskAddressBookService;

    @Autowired
    private ThreadPoolTaskExecutor loadDataTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor importDataTaskExecutor;

    public static final int pageSize = 8000;

    @Test
    public void DataTransfer() {
        // 1. 获取数据库数据总数
        RiskAddressBook riskAddressBook = new RiskAddressBook();
        int count = riskAddressBookMapper.selectCount(riskAddressBook);
        // 2. 10K/page/thread
        int page = count / pageSize + 1;

        List<Boolean> executeResult = new ArrayList<>(page);
        LocalDateTime startTime = LocalDateTime.now();
        log.info("开始执行从 mysql 导入数据到 elastic 中：{}" + startTime);
        for (int i = 1; i <= page; i++) {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int pageI = i;
            CompletableFuture<Void> completableFuture = CompletableFuture
                    .supplyAsync(() -> this.queryPageList(pageI), loadDataTaskExecutor)
                    .thenAcceptAsync(riskAddressBookList -> riskAddressBookService.save(riskAddressBookList), importDataTaskExecutor);

            executeResult.add(completableFuture.isDone() && !completableFuture.isCancelled());
        }

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (executeResult.stream().noneMatch(result -> result == false)){
                LocalDateTime endTime = LocalDateTime.now();
                log.info("完成执行从 mysql 导入数据到 elastic 中：{}, 耗时： {}" + endTime, Duration.between(startTime, endTime).getSeconds());
            }
            System.out.println("！！！");
        }
    }

    private List<RiskAddressBook> queryPageList(Integer page) {
        PageHelper.startPage(page, pageSize);
        return riskAddressBookMapper.selectAll();
    }

}
