package com.es.demo.service;

import com.es.demo.config.SnowflakeIdWorker;
import com.es.demo.mapper.RiskCallRecordMapper;
import com.es.demo.model.mybatis.RiskCallRecord;
import com.es.demo.repository.RiskCallRecordRepository;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RiskCallRecordService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RiskCallRecordRepository riskCallRecordRepository;

    @Autowired
    private RiskCallRecordMapper riskCallRecordMapper;

    @Autowired
    private ThreadPoolTaskExecutor loadDataTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor importDataTaskExecutor;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public static final int pageSize = 50000;

    public void startRiskCallRecord(Date startDate, Integer pageSize2) {
        // 1. 获取数据库数据总数
        RiskCallRecord riskCallRecord = new RiskCallRecord();
        int selectCount = riskCallRecordMapper.selectCount(riskCallRecord);
        Long count = (long) selectCount;
        // 2. 10K/page/thread
        Long page = count / pageSize + 1;

        LocalDateTime startTime = LocalDateTime.now();
        log.info("开始执行从 mysql 导入数据到 elastic 中：{}" + startTime);
        for (int i = 1; i <= page; i++) {

            if (i != 1) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int pageI = (i - 1) * pageSize;
            CompletableFuture
                    .supplyAsync(() -> this.queryPageList(pageI), loadDataTaskExecutor)
                    .thenAcceptAsync(riskCallRecordList -> this.saveMessageToES(riskCallRecordList), importDataTaskExecutor);

        }

        LocalDateTime endTime = LocalDateTime.now();
        log.info("完成执行从 mysql 导入数据到 elastic 中：{}, 耗时： {}" + endTime, Duration.between(startTime, endTime).getSeconds());


    }

    private void saveMessageToES(List<RiskCallRecord> riskCallRecordList) {

        // List<com.es.demo.model.elastic.RiskCallRecord> esRiskMessageArrayList = new ArrayList<>(pageSize);
        int size = riskCallRecordList.size();
        com.es.demo.model.elastic.RiskCallRecord riskCallRecord2;
        RiskCallRecord riskCallRecord;
        List<IndexQuery> indexQueryList = new ArrayList<>(pageSize);
        IndexQuery indexQuery;
        String matchNewPhone;
        for (int i = 0; i < size; i++) {

            indexQuery = new IndexQuery();
            riskCallRecord = riskCallRecordList.get(i);
            matchNewPhone = this.matchNewPhone(riskCallRecord.getPhone());
            if (StringUtils.isBlank(matchNewPhone)) {
                continue;
            }

            riskCallRecord2 = new com.es.demo.model.elastic.RiskCallRecord();
            riskCallRecord2.setId(snowflakeIdWorker.nextId());
            riskCallRecord2.setOrderNo(riskCallRecord.getOrderNum());
            riskCallRecord2.setPhone(matchNewPhone);
            riskCallRecord2.setCallTime(riskCallRecord.getCallTime());
            riskCallRecord2.setDuration(riskCallRecord.getDuration());
            riskCallRecord2.setType(riskCallRecord.getType());
            riskCallRecord2.setCreateTime(riskCallRecord.getCreateTime());
            riskCallRecord2.setUpdateTime(new Date());
            riskCallRecord2.setRegion("");
            riskCallRecord2.setContactCount(0L);
            riskCallRecord2.setRelation("");
            riskCallRecord2.setOutCount(0L);
            riskCallRecord2.setInCount(0L);
            riskCallRecord2.setName("");
            riskCallRecord2.setMissedCount(0L);
            riskCallRecord2.setLastContactTime(null);
            // esRiskMessageArrayList.add(riskCallRecord2);

            indexQuery.setIndexName("risk_call_record");
            indexQuery.setObject(riskCallRecord2);
            indexQueryList.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(indexQueryList);
        // riskCallRecordRepository.saveAll(esRiskMessageArrayList);
    }

    private List<RiskCallRecord> queryPageList(Integer page) {
        return riskCallRecordMapper.queryPageList(page, pageSize);
    }

    public String matchNewPhone(String phoneStr) {
        String phoneDigits = StringUtils.getDigits(phoneStr);
        if (!(StringUtils.isNotBlank(phoneDigits) && phoneDigits.length() > 6)) {
            return null;
        }

        // 通讯录号码格式：当号码开头有+91、09/08/07/06开头、或空格、斜杠格式时，默认把+91、09/08/07/06前的“0”、空格、斜杆删除。显示剩余后的号码格式。
        // String phoneDigits = StringUtils.getDigits(phoneStr);

        String[] phoneStartChar = {"09", "08", "07", "06"};
        String internationalPhoneFlag = "91";
        String symbolFlg = "+";

        String newPhone;
        if (StringUtils.startsWithAny(phoneDigits, phoneStartChar)) {
            newPhone = StringUtils.substring(phoneDigits, phoneDigits.indexOf("0") + 1);
        } else if (StringUtils.startsWithAny(phoneDigits, internationalPhoneFlag)) {
            newPhone = StringUtils.substringAfter(phoneDigits, internationalPhoneFlag);
        } else if (StringUtils.startsWith(phoneDigits, symbolFlg)) {
            newPhone = StringUtils.substringAfter(phoneDigits, symbolFlg);
        } else {
            newPhone = phoneDigits;
        }
        return newPhone;
    }
}
