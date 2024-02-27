package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.product.ProductDto;
import com.marcoecommerce.shop.model.product.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<ProductEntity, ProductDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto toDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override
    public ProductEntity toEntity(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }
}
