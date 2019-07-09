package com.es.demo.repository;

import com.es.demo.model.elastic.RiskCallRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 通话记录
 *
 * @author walle
 */
public interface RiskCallRecordRepository extends ElasticsearchRepository<RiskCallRecord, Long> {



}
