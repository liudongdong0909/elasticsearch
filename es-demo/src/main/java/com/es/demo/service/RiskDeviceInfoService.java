package com.es.demo.service;

import com.es.demo.config.SnowflakeIdWorker;
import com.es.demo.mapper.RiskDeviceAppInfoMapper;
import com.es.demo.mapper.RiskDeviceInfoMapper;
import com.es.demo.model.mybatis.RiskDeviceInfo;
import com.es.demo.repository.RiskDeviceInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RiskDeviceInfoService {

    @Autowired
    private RiskDeviceInfoRepository deviceInfoRepository;

    @Autowired
    private ThreadPoolTaskExecutor loadDataTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor importDataTaskExecutor;

    @Autowired
    private RiskDeviceInfoMapper riskDeviceInfoMapper;

    @Autowired
    private RiskDeviceAppInfoMapper riskDeviceAppInfoMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public static final int pageSize = 5000;


    public void startRiskDeviceInfo(Date startDate, Integer pageSize2) {
        RiskDeviceInfo riskDeviceInfo = new RiskDeviceInfo();
        int selectCount = riskDeviceInfoMapper.selectCount(riskDeviceInfo);
        Long count = (long) selectCount;

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
            CompletableFuture<Void> completableFuture = CompletableFuture
                    .supplyAsync(() -> this.queryPageList(pageI), loadDataTaskExecutor)
                    .thenAcceptAsync(riskDeviceInfoList -> this.saveDeviceToES(riskDeviceInfoList), importDataTaskExecutor);

        }
    }

    private void saveDeviceToES(List<RiskDeviceInfo> riskDeviceInfoList) {

        List<com.es.demo.model.elastic.RiskDeviceInfo> esRiskDeviceInfoList = new ArrayList<>(pageSize);

        int size = riskDeviceInfoList.size();
        RiskDeviceInfo riskDeviceInfo;
        com.es.demo.model.elastic.RiskDeviceInfo esDeviceInfo;

        for (int i = 0; i < size; i++) {
            riskDeviceInfo = riskDeviceInfoList.get(i);
            esDeviceInfo = new com.es.demo.model.elastic.RiskDeviceInfo();
            esDeviceInfo.setId(snowflakeIdWorker.nextId());
            esDeviceInfo.setOrderNo(riskDeviceInfo.getOrderNum());
            esDeviceInfo.setBattery(riskDeviceInfo.getBattery());
            esDeviceInfo.setAltitude(riskDeviceInfo.getAltitude());
            esDeviceInfo.setLongitude(riskDeviceInfo.getLongitude());
            esDeviceInfo.setImiId(riskDeviceInfo.getImiId());
            esDeviceInfo.setIp(riskDeviceInfo.getIp());
            esDeviceInfo.setPhoneModel(riskDeviceInfo.getPhoneModel());
            esDeviceInfo.setGpsAddress(riskDeviceInfo.getGpsAddress());
            esDeviceInfo.setCreateTime(riskDeviceInfo.getCreateTime());
            esDeviceInfo.setUpdateTime(riskDeviceInfo.getUpdateTime());
            esDeviceInfo.setFinanceAppCount(0L);
            esDeviceInfo.setFingerPrint("");
            esDeviceInfo.setPhoneType("");
            esDeviceInfo.setNetMode("");
            esDeviceInfo.setMacAddress("");
            esDeviceInfo.setIsSimulation("");
            esDeviceInfo.setIpManager("");
            esDeviceInfo.setIpAddress("");
            esDeviceInfo.setImeId("");
            esDeviceInfo.setHandManager("");

            List<String> appInfoList = riskDeviceAppInfoMapper.queryAppInfoByDeviceId(riskDeviceInfo.getId());
            List<com.es.demo.model.elastic.RiskDeviceInfo.RiskDeviceAppInfo> esAppInfoList = new ArrayList<>(appInfoList.size());
            com.es.demo.model.elastic.RiskDeviceInfo.RiskDeviceAppInfo esAppInfo;
            for (int j = 0; j < appInfoList.size(); j++) {
                esAppInfo = new com.es.demo.model.elastic.RiskDeviceInfo.RiskDeviceAppInfo();
                esAppInfo.setAppName("");
                esAppInfo.setPackageName(appInfoList.get(j));
                esAppInfoList.add(esAppInfo);
            }
            log.info("设备{} 有 {} APP", riskDeviceInfo.getId(), esAppInfoList.size());
            esDeviceInfo.setApplicationList(esAppInfoList);

            esRiskDeviceInfoList.add(esDeviceInfo);
        }
        log.info("保存到 es : {}", esRiskDeviceInfoList.size());
        deviceInfoRepository.saveAll(esRiskDeviceInfoList);
    }

    private List<RiskDeviceInfo> queryPageList(int pageI) {
        return riskDeviceInfoMapper.queryPageList(pageI, pageSize);
    }
}
