package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.order.OrderDto;
import com.marco.mcshop.model.entity.OrderEntity;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements Mapper<OrderEntity, OrderDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto toDto(OrderEntity order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderEntity toEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, OrderEntity.class);
    }


}
