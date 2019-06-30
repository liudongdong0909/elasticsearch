package com.es.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.es.demo.mapper.RiskAddressBookMapper;
import com.es.demo.model.elastic.AddressBook;
import com.es.demo.model.mybatis.RiskAddressBook;
import com.es.demo.repository.RiskAddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-25
 */
@Service
public class RiskAddressBookService {

    @Autowired
    private RiskAddressBookMapper riskAddressBookMapper;

    @Autowired
    private RiskAddressBookRepository addressBookRepository;

    public void save(AddressBook addressBook) {
        addressBookRepository.save(addressBook);
    }

    public void save(List<RiskAddressBook> riskAddressBookList) {

        List<AddressBook> addressBookList = riskAddressBookList.stream()
                .map(riskAddressBook -> {
                    AddressBook addressBook = new AddressBook();
                    addressBook.setId(riskAddressBook.getId());
                    addressBook.setName(riskAddressBook.getName());
                    addressBook.setOrderNo(Long.valueOf(riskAddressBook.getOrderNum()));
                    addressBook.setPhone(riskAddressBook.getPhone());
                    addressBook.setBelongingTo("");
                    addressBook.setCreateTime(riskAddressBook.getCreateTime());
                    return addressBook;
                })
                .collect(Collectors.toList());

        addressBookRepository.saveAll(addressBookList);

    }

}
