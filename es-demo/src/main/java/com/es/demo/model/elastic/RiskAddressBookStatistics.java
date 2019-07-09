package com.es.demo.model.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 通讯录统计
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_address_book_statistics", type = "riskAddressBookStatistics", replicas = 1, createIndex = false)
public class RiskAddressBookStatistics {

    @Id
    private Long id;

    /**
     * 订单编号
     */
    @Field(type = FieldType.Keyword)
    private Long orderNo;

    /**
     * 存在重复号码个数
     *
     * @description 此笔订单重复号码：所有重复的号码总个数
     */
    @Field(type = FieldType.Integer)
    private Long repeatCount;

    /**
     * 最大重复个数
     *
     * @description 此笔订单最大重复个数：所有重复的号码中，最大的重复个数
     */
    @Field(type = FieldType.Integer)
    private Long maxRepeatCount;

    /**
     * 总的电话个数
     */
    @Field(type = FieldType.Integer)
    private Long phoneCount;

    /**
     * 有效电话号个数
     *
     * @description 此笔订单有效号码个数：重复的号码去重后的10位数，且是规范格式后9/8/7/6开头的数字
     */
    @Field(type = FieldType.Integer)
    private Long validCount;

    /**
     * 无效电话号个数
     */
    @Field(type = FieldType.Integer)
    private Long invalidCount;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
