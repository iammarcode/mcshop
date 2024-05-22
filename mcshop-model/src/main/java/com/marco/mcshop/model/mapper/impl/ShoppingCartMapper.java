package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.ShoppingCartDto;
import com.marco.mcshop.model.entity.ShoppingCart;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper implements Mapper<ShoppingCart, ShoppingCartDto> {
    private final ModelMapper modelMapper;

    public ShoppingCartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return modelMapper.map(shoppingCart, ShoppingCartDto.class);
    }

    @Override
    public ShoppingCart toEntity(ShoppingCartDto shoppingCartDto) {
        return modelMapper.map(shoppingCartDto, ShoppingCart.class);
    }
}
