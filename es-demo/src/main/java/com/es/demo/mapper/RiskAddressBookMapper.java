package com.es.demo.mapper;

import com.es.demo.model.mybatis.RiskAddressBook;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
public interface RiskAddressBookMapper extends Mapper<RiskAddressBook> {

    List<RiskAddressBook> queryPageList(@Param("page") Integer page,
                                        @Param("pageSize") Integer pageSize);
}
