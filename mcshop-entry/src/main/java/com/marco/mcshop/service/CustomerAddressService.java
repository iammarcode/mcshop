package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;

import java.util.List;

public interface CustomerAddressService {
    List<CustomerAddressDto> create(CustomerAddressDto addressDto);

    List<CustomerAddressDto> partialUpdate(Long id, CustomerAddressDto addressDto);

    List<CustomerAddressDto> delete(Long addressId);
}
