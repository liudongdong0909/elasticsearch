package com.es.demo.repository;

import com.es.demo.model.elastic.RiskAddressBookStatistics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 通讯录统计
 *
 * @author walle
 */
public interface RiskAddressBookStatisticsRepository extends ElasticsearchRepository<RiskAddressBookStatistics, Long> {

}
