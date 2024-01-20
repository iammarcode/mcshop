package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.auth.LoginRequest;
import com.marcoecommerce.shop.model.auth.RegisterRequest;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerEntity, CustomerResponse> {
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerResponse toResponse(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerResponse.class);
    }

    @Override
    public CustomerEntity toEntity(CustomerResponse customerResponse) {
        return modelMapper.map(customerResponse, CustomerEntity.class);
    }

    public CustomerEntity toEntityRegister(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, CustomerEntity.class);
    }

    public CustomerEntity toEntityLogin(LoginRequest loginRequest) {
        return modelMapper.map(loginRequest, CustomerEntity.class);
    }



}
