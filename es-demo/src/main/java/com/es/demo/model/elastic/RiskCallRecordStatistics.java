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
import java.util.List;

/**
 * 通话记录
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_call_record_statistics", type = "RiskCallRecordStatistics", replicas = 1, createIndex = false)
public class RiskCallRecordStatistics {

    @Id
    private Long id;

    /**
     * 订单编号
     */
    @Field(type = FieldType.Keyword)
    private Long orderNo;

    /**
     * 联系人统计
     */
    @Field(type = FieldType.Nested)
    private List<Contact> contactList;

    /**
     * 累计通话时长
     */
    @Field
    private Integer durationSum;

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

    /**
     * 联系人
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {
        /**
         * 联系人关系
         */
        @Field(type = FieldType.Integer)
        private Integer relation;

        /**
         * 联系人数量
         */
        @Field
        private Integer contactCount;
    }
}
