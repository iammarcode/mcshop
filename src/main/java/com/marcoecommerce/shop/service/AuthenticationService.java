package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerEntity;

public interface AuthenticationService {

    CustomerEntity register(CustomerEntity customer);

    CustomerEntity login(CustomerEntity customer);
}
