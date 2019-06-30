package com.es.demo.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "risk_address_book", type = "riskAddressBook", replicas = 1)
public class AddressBook {

    @Id
    private Long id;

    private Long orderNo;

    private String belongingTo;

    private String phone;

    private String name;

    private Date createTime;
}
