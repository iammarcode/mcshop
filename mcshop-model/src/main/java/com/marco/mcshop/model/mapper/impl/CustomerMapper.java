package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.dto.customer.CustomerRegisterDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.Mapper;
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
