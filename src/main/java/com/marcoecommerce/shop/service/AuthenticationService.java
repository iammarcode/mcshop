package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;

public interface AuthenticationService {

    void requestOtp(String email);

    CustomerEntity register(CustomerRegisterDto customer);

    CustomerEntity login(CustomerLoginDto customer);
}
