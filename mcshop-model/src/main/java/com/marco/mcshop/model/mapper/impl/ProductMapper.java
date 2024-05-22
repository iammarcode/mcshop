package com.marco.mcshop.model.mapper.impl;

import com.marco.mcshop.model.dto.ProductDto;
import com.marco.mcshop.model.entity.Product;
import com.marco.mcshop.model.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductDto> {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }
}
