package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.entity.Customer;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDto> {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public Customer toEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
