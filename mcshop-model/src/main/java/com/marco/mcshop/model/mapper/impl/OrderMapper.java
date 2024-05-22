package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.OrderDto;
import com.marco.mcshop.model.entity.Order;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto toDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }


}
