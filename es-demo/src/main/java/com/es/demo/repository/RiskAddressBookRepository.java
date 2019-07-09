package com.es.demo.repository;


import com.es.demo.model.elastic.RiskAddressBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 通讯录
 * @author walle
 */
public interface RiskAddressBookRepository extends ElasticsearchRepository<RiskAddressBook, Long> {



}
