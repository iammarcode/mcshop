package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.CustomerAddressDto;
import com.marco.mcshop.model.entity.CustomerAddress;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper implements Mapper<CustomerAddress, CustomerAddressDto> {
    private final ModelMapper modelMapper;

    public CustomerAddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerAddressDto toDto(CustomerAddress addressEntity) {
        return modelMapper.map(addressEntity, CustomerAddressDto.class);
    }

    @Override
    public CustomerAddress toEntity(CustomerAddressDto addressDto) {
        return modelMapper.map(addressDto, CustomerAddress.class);
    }
}
