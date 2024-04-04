package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerService {

    CustomerEntity create(CustomerEntity customerEntity);

    boolean isExist(Long id);

    CustomerDto updateBasicInfo(Long id, CustomerDto customerDto);

    CustomerEntity getCurrentCustomer();

    UserDetails findUserDetailsByEmail(String email);
}
