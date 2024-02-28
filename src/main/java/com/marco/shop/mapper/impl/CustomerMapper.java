package com.marco.shop.mapper.impl;

import com.marco.shop.mapper.Mapper;
import com.marco.shop.model.customer.CustomerRegisterDto;
import com.marco.shop.model.customer.CustomerEntity;
import com.marco.shop.model.customer.CustomerDto;
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
    public CustomerDto toDto(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerDto.class);
    }

    @Override
    public CustomerEntity toEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, CustomerEntity.class);
    }

    public CustomerEntity toEntityRegister(CustomerRegisterDto customerRegisterDto) {
        return modelMapper.map(customerRegisterDto, CustomerEntity.class);
    }
}
