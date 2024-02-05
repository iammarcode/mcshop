package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressDto;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper implements Mapper<CustomerAddressEntity, CustomerAddressDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerAddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerAddressDto toDto(CustomerAddressEntity addressEntity) {
        return modelMapper.map(addressEntity, CustomerAddressDto.class);
    }

    @Override
    public CustomerAddressEntity toEntity(CustomerAddressDto addressDto) {
        return modelMapper.map(addressDto, CustomerAddressEntity.class);
    }
}
