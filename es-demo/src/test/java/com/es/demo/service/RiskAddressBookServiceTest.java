package com.es.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.es.demo.EsDemoApplicationTests;
import com.es.demo.mapper.RiskMessageMapper;
import com.es.demo.model.mybatis.RiskMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
public class RiskAddressBookServiceTest extends EsDemoApplicationTests {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RiskMessageMapper riskMessageMapper;

    @Test
    public void test(){
        RiskMessage riskMessage = new RiskMessage();
        riskMessage.setOrderNum(569889824601800704l);
        List<RiskMessage> riskMessageList = riskMessageMapper.queryPageList(1, 3);
        riskMessageList.forEach(riskMessage1 -> {
            System.out.println(JSONObject.toJSONString(riskMessage1));
        });
    }

}