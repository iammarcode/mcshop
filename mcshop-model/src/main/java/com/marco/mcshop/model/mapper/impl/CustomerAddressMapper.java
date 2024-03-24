package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;
import com.marco.mcshop.model.entity.CustomerAddressEntity;
import com.marco.mcshop.model.mapper.Mapper;
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
