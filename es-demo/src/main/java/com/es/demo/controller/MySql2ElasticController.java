package com.es.demo.controller;

import com.es.demo.service.DataTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
@RestController
@RequestMapping("/transfer")
public class MySql2ElasticController {

    @Autowired
    private DataTransferService dataTransferService;

    @RequestMapping("/message")
    public void startRiskMessage(Date startDate, Integer pageSize) {
        dataTransferService.startRiskMessage(startDate, pageSize);
    }

    public void startRiskDevieInfo(Date startDate, Integer pageSize) {

    }

    public void startRiskAddressBook(Date startDate, Integer pageSize) {

    }

    public void startRiskCallRecord(Date startDate, Integer pageSize) {

    }
}
