package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerEntity, CustomerDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto mapTo(CustomerEntity customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerEntity mapFrom(CustomerDto customerDto) {
        return modelMapper.map(customerDto, CustomerEntity.class);
    }


}
