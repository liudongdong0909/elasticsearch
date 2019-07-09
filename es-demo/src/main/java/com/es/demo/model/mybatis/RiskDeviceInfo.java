package com.es.demo.model.mybatis;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
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

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getImiId() {
        return imiId;
    }

    public void setImiId(String imiId) {
        this.imiId = imiId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public Long getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(Long phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getGpsAddress() {
        return gpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        this.gpsAddress = gpsAddress;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
