package com.es.demo.mapper;

import com.es.demo.model.mybatis.RiskMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
public interface RiskMessageMapper extends Mapper<RiskMessage> {

    List<RiskMessage> queryPageList(@Param("page") Integer page,
                                       @Param("pageSize") Integer pageSize);
}
