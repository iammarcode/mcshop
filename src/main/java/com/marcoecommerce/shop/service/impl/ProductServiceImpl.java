package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.exception.product.ProductNotFoundException;
import com.marcoecommerce.shop.mapper.impl.ProductMapper;
import com.marcoecommerce.shop.model.product.ProductDto;
import com.marcoecommerce.shop.model.product.ProductEntity;
import com.marcoecommerce.shop.repository.ProductRepository;
import com.marcoecommerce.shop.service.ProductService;
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
