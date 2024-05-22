package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.entity.Customer;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerService {

    Customer create(Customer customer);

    boolean isExist(Long id);

    CustomerDto updateBasicInfo(Long id, CustomerDto customerDto);

    Customer getCurrentCustomer() throws Exception;

    UserDetails findUserDetailsByEmail(String email);
}
