package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerEntity save(CustomerEntity customerEntity);

    List<CustomerEntity> findAll();

    Optional<CustomerEntity> findById(Long id);

    boolean isExist(Long id);

    void deleteById(Long id);

    CustomerEntity partialUpdate(Long id, CustomerEntity authorEntity);
}
