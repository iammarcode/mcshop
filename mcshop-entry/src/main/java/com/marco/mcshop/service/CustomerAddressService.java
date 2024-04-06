package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;

import java.util.List;

public interface CustomerAddressService {
    List<CustomerAddressDto> create(CustomerAddressDto addressDto) throws Exception;

    List<CustomerAddressDto> partialUpdate(Long id, CustomerAddressDto addressDto) throws Exception;

    List<CustomerAddressDto> delete(Long addressId) throws Exception;
}
