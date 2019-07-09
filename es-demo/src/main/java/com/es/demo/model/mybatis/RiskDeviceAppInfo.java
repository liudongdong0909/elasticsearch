package com.es.demo.model.mybatis;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
@Table(name = "risk_device_app_info")
public class RiskDeviceAppInfo {

    @Id
    private Long id;

    private Long deviceId;

    private String appName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
