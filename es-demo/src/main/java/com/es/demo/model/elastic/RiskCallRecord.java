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
 * 通话记录
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_call_record", type = "riskCallRecord", replicas = 1, createIndex = false)
public class RiskCallRecord {

    @Id
    private Long id;

    /**
     * 订单编号
     */
    @Field(type = FieldType.Keyword)
    private Long orderNo;

    /**
     * 所属国家/地区
     */
    @Field
    private String region;

    /**
     * 电话号
     */
    @Field(type = FieldType.Keyword)
    private String phone;

    /**
     * 通话类型
     */
    @Field(type = FieldType.Keyword)
    private Long type;

    /**
     * 联系人
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 亲属关系
     */
    @Field(type = FieldType.Keyword)
    private String relation;

    /**
     * 通话时间
     */
    @Field(type = FieldType.Integer)
    private Long duration;

    /**
     * 呼入次数
     */
    @Field
    private Long inCount;

    /**
     * 呼出次数
     */
    @Field
    private Long outCount;

    /**
     * 未接
     */
    private Long missedCount;

    /**
     * 联系次数
     */
    @Field(type = FieldType.Integer)
    private Long contactCount;

    /**
     * 开始呼叫联系人时间
     */
    @Field(type = FieldType.Date)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date callTime;

    /**
     * 最后联系时间
     */
    @Field(type = FieldType.Date)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastContactTime;

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
