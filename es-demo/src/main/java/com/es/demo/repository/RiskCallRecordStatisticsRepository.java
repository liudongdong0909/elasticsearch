package com.es.demo.repository;

import com.es.demo.model.elastic.RiskCallRecordStatistics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 通话记录统计
 *
 * @author walle
 */
public interface RiskCallRecordStatisticsRepository extends ElasticsearchRepository<RiskCallRecordStatistics, Long> {
}
