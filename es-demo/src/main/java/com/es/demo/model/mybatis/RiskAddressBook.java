package com.es.demo.model.mybatis;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
@Table(name = "risk_address_book")
public class RiskAddressBook {

    /**
     * 主键ID
     */
    @Id
    private Long id;
    /**
     * 订单编号
     */
    private  String orderNum;
    /**
     * 姓名
     */
    private String  name;
    /**
     * 电话
     */
    private String  phone;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
