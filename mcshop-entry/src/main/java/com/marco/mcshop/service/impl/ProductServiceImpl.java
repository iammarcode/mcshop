package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.product.ProductNotFoundException;
import com.marco.mcshop.service.ProductService;
import com.marco.mcshop.model.dto.product.ProductDto;
import com.marco.mcshop.model.entity.ProductEntity;
import com.marco.mcshop.model.mapper.impl.ProductMapper;
import com.marco.mcshop.model.repository.ProductRepository;
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
