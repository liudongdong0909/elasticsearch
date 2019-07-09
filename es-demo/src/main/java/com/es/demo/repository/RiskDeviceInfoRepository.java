package com.es.demo.repository;

import com.es.demo.model.elastic.RiskDeviceInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 设备信息
 */
public interface RiskDeviceInfoRepository extends ElasticsearchRepository<RiskDeviceInfo, Long> {

}
