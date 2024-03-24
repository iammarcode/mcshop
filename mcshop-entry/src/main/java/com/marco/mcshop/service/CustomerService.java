package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import org.springframework.security.core.userdetails.UserDetails;

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

    UserDetails findUserDetailsByEmail(String email);
}
