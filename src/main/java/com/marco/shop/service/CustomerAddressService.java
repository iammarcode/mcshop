package com.marco.shop.service;

import com.marco.shop.model.customer.CustomerDto;
import com.marco.shop.model.customerAddress.CustomerAddressDto;

public interface CustomerAddressService {
    CustomerDto create(CustomerAddressDto addressDto);

    CustomerDto update(CustomerAddressDto addressDto);

    CustomerDto delete(Long addressId);
}
