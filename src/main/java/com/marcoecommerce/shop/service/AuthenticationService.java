package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;

public interface AuthenticationService {

    String getOtp(String email);

    CustomerEntity register(CustomerRegisterDto customer);

    CustomerEntity login(CustomerLoginDto customer);
}
