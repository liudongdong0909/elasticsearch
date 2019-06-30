package com.es.demo.repository;

import com.es.demo.model.elastic.AddressBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
public interface RiskAddressBookRepository extends ElasticsearchCrudRepository<AddressBook, Long> {
}
