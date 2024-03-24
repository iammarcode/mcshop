package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;

public interface CustomerAddressService {
    CustomerDto create(CustomerAddressDto addressDto);

    CustomerDto update(CustomerAddressDto addressDto);

    CustomerDto delete(Long addressId);
}
