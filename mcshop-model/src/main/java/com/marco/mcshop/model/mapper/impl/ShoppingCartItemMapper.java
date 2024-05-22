package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.ShoppingCartItemDto;
import com.marco.mcshop.model.entity.ShoppingCartItem;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartItemMapper implements Mapper<ShoppingCartItem, ShoppingCartItemDto> {
    private final ModelMapper modelMapper;

    public ShoppingCartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartItemDto toDto(ShoppingCartItem shoppingCartItem) {
        return modelMapper.map(shoppingCartItem, ShoppingCartItemDto.class);
    }

    @Override
    public ShoppingCartItem toEntity(ShoppingCartItemDto shoppingCartItemDto) {
        return modelMapper.map(shoppingCartItemDto, ShoppingCartItem.class);
    }
}
