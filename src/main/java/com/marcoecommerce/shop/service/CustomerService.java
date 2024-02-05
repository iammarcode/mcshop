package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;

import java.util.List;

public interface CustomerService {

    CustomerEntity create(CustomerEntity customerEntity);

    List<CustomerDto> findAll();

    CustomerDto findById(Long id);

    boolean isExist(Long id);

    void deleteById(Long id);

    CustomerDto updateInfo(Long id, CustomerDto customerDto);

    CustomerEntity findByEmail(String email);

    CustomerEntity getCurrentCustomer();
}
