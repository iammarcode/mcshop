package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
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

    public CustomerEntity toEntityLogin(CustomerLoginDto customerLoginDto) {
        return modelMapper.map(customerLoginDto, CustomerEntity.class);
    }



}
