package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.product.ProductDto;
import com.marco.mcshop.model.entity.ProductEntity;
import com.marco.mcshop.model.mapper.Mapper;
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
