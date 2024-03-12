package com.marco.shop.mapper.impl;

import com.marco.shop.mapper.Mapper;
import com.marco.shop.model.order.OrderDto;
import com.marco.shop.model.order.OrderEntity;
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
