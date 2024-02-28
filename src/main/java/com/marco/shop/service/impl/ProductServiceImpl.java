package com.marco.shop.service.impl;

import com.marco.shop.exception.product.ProductNotFoundException;
import com.marco.shop.mapper.impl.ProductMapper;
import com.marco.shop.repository.ProductRepository;
import com.marco.shop.service.ProductService;
import com.marco.shop.model.product.ProductDto;
import com.marco.shop.model.product.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> productEntityList = StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
        return productEntityList.stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductDto findById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Product not found with ID: " + id);
                    return new ProductNotFoundException(id);
                }
        );

        return productMapper.toDto(productEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return productRepository.existsById(id);
    }
}
