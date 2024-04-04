package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.shoppingCart.ShoppingCartDto;
import com.marco.mcshop.model.entity.ShoppingCartEntity;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper implements Mapper<ShoppingCartEntity, ShoppingCartDto> {
    private final ModelMapper modelMapper;

    public ShoppingCartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartDto toDto(ShoppingCartEntity shoppingCartEntity) {
        return modelMapper.map(shoppingCartEntity, ShoppingCartDto.class);
    }

    @Override
    public ShoppingCartEntity toEntity(ShoppingCartDto shoppingCartDto) {
        return modelMapper.map(shoppingCartDto, ShoppingCartEntity.class);
    }
}
