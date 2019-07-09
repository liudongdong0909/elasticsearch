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
 * 短信
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_message", type = "riskMessage", replicas = 1, createIndex = false)
public class RiskMessage {

    @Id
    private Long id;

    /**
     * 订单编号
     */
    @Field(type = FieldType.Keyword)
    private Long orderNo;

    /**
     * 收信人地址
     */
    @Field
    private String address;

    /**
     * 短信内容
     */
    @Field(type = FieldType.Text)
    private String body;

    /**
     * 已读未读 ：0:未读1：已读
     */
    @Field(type = FieldType.Long)
    private Long read;

    /**
     * type:短信类型1是接收到的;2是已发出
     */
    @Field(type = FieldType.Long)
    private Long type;

    /**
     * 发送短信时间
     */
    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

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
