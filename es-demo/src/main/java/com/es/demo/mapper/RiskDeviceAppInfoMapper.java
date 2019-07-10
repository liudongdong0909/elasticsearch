package com.es.demo.mapper;

import com.es.demo.model.mybatis.RiskDeviceAppInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
public interface RiskDeviceAppInfoMapper extends Mapper<RiskDeviceAppInfo> {

    List<String> queryAppInfoByDeviceId(@Param("deviceId") Long deviceId);

}
