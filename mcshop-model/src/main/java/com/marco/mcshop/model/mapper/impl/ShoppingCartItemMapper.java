package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.shoppingCartItem.ShoppingCartItemDto;
import com.marco.mcshop.model.entity.ShoppingCartItemEntity;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartItemMapper implements Mapper<ShoppingCartItemEntity, ShoppingCartItemDto> {
    private final ModelMapper modelMapper;

    public ShoppingCartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartItemDto toDto(ShoppingCartItemEntity shoppingCartItemEntity) {
        return modelMapper.map(shoppingCartItemEntity, ShoppingCartItemDto.class);
    }

    @Override
    public ShoppingCartItemEntity toEntity(ShoppingCartItemDto shoppingCartItemDto) {
        return modelMapper.map(shoppingCartItemDto, ShoppingCartItemEntity.class);
    }
}
