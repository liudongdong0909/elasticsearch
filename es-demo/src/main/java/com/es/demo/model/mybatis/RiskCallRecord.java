package com.es.demo.model.mybatis;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-07-09
 */
@Table(name = "risk_call_record")
public class RiskCallRecord {

    @Id
    private Long id;

    private Long orderNum;

    private String phone;

    private Date callTime;

    private Long duration;

    private Long type;

    private Date createTime;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
