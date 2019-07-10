package com.es.demo.model.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "risk_device_info")
public class RiskDeviceInfo {

    @Id
    private Long id;

    private Long orderNum;

    private String imiId;

    private String ip;

    private String altitude;

    private String longitude;

    private String battery;

    private Long phoneModel;

    private String gpsAddress;

    private Date createTime;

    private Date updateTime;

}
