package com.es.demo.service;

import com.es.demo.EsDemoApplicationTests;
import com.es.demo.model.elastic.AddressBook;
import com.es.demo.model.elastic.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
public class RiskAddressBookServiceTest extends EsDemoApplicationTests {

    @Autowired
    private RiskAddressBookService addressBookService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RiskAddressBookService riskAddressBookService;

    @Test
    public void save() {
        AddressBook addressBook = AddressBook.builder()
                .orderNo(32222L)
                .name("test24444")
                .belongingTo("+91")
                .phone("123456677")
                .createTime(new Date())
                .build();
        addressBookService.save(addressBook);
    }

    @Test
    public void save2(){

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(new User().setAge(18)
                        .setHobby("演戏")
                        .setName("张敏")
                        .setBirthday(LocalDate.of(1990, 10,01))
                        .setCreateTime(LocalDateTime.now()))
                .build();
        elasticsearchTemplate.index(indexQuery);
    }

}