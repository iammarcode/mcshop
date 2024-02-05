package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressDto;

public interface CustomerAddressService {
    CustomerDto create(CustomerAddressDto addressDto);

    CustomerDto update(CustomerAddressDto addressDto);

    CustomerDto delete(Long addressId);
}
