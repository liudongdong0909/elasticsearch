package com.es.demo.mapper;

import com.es.demo.model.mybatis.RiskCallRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
public interface RiskCallRecordMapper extends Mapper<RiskCallRecord> {

    List<RiskCallRecord> queryPageList(@Param("page") Integer page,
                                        @Param("pageSize") Integer pageSize);
}
