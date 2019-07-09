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
@Table(name = "risk_message")
public class RiskMessage {

    @Id
    private Long id;

    private Long orderNum;

    private Date sendTime;

    private Long type;

    private String address;

    private String body;

    private Date createTime;

    private Long read;

}
