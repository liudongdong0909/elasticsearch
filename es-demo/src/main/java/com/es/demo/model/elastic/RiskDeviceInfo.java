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
 * 设备信息
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_device_info", type = "riskDeviceInfo", replicas = 1, createIndex = false)
public class RiskDeviceInfo {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long orderNo;

    /**
     * imiId
     */
    @Field(type = FieldType.Long)
    private String imiId;

    /**
     * ip
     */
    @Field
    private String ip;

    /**
     * 纬度
     */
    @Field
    private String altitude;

    /**
     * 经度
     */
    @Field
    private String longitude;

    /**
     * 电池电力
     */
    @Field
    private String battery;

    /**
     * 是否调试模式 :0：否 1：是
     */
    @Field
    private Long phoneModel;

    /**
     * gps 地址
     */
    @Field
    private String gpsAddress;

    /**
     * 贷款类App数量
     */
    @Field
    private Long financeAppCount;

    /**
     * ip所属城市
     */
    @Field
    private String ipAddress;

    /**
     * IP地址网络供应商
     */
    @Field
    private String ipManager;

    /**
     * 设备指纹
     */
    @Field
    private String fingerPrint;

    /**
     * 网络模式
     */
    @Field
    private String netMode;

    /**
     * 硬件制造商
     */
    @Field
    private String handManager;

    /**
     * mac地址
     */
    @Field
    private String macAddress;

    /**
     * 手机型号
     */
    @Field
    private String phoneType;

    /**
     * 手机IMEI号
     */
    @Field
    private String imeId;

    @Field
    private String isSimulation;

    /**
     * 设备中安装的app
     */
    @Field(type = FieldType.Nested)
    private List<RiskDeviceAppInfo> applicationList;

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
     * 设备已安装APP
     *
     * @author walle
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RiskDeviceAppInfo {
        /**
         * appName
         */
        @Field
        private String appName;

        @Field
        private String packageName;
    }
}
