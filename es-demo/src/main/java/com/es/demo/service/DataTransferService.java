package com.es.demo.service;

import com.es.demo.repository.RiskAddressBookRepository;
import com.es.demo.repository.RiskCallRecordRepository;
import com.es.demo.repository.RiskDeviceInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
@Slf4j
@Service
public class DataTransferService {

    @Autowired
    private RiskDeviceInfoService riskDeviceInfoService;

    @Autowired
    private RiskMessageService riskMessageService;

    @Autowired
    private RiskCallRecordService riskCallRecordService;

    @Autowired
    private RiskAddressBookService riskAddressBookService;

    public void startRiskMessage(Date startDate, Integer pageSize) {
        riskMessageService.startRiskMessage(startDate, pageSize);
    }


    public void startRiskDeviceInfo(Date startDate, Integer pageSize) {
        riskDeviceInfoService.startRiskDeviceInfo(startDate, pageSize);
    }

    public void startRiskCallRecord(Date startDate, Integer pageSize) {
        riskCallRecordService.startRiskCallRecord(startDate, pageSize);
    }

    public void startRiskAddressBook(Date startDate, Integer pageSize) {
        riskAddressBookService.startRiskAddressBook(startDate, pageSize);
    }
}
