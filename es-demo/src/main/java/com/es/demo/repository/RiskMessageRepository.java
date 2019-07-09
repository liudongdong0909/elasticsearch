package com.es.demo.repository;

import com.es.demo.model.elastic.RiskMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 短信
 *
 * @author walle
 */
public interface RiskMessageRepository extends ElasticsearchRepository<RiskMessage, Long> {

}
