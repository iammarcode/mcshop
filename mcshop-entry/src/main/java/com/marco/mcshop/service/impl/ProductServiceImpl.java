package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.product.ProductNotFoundException;
import com.marco.mcshop.model.dto.ProductDto;
import com.marco.mcshop.model.entity.Product;
import com.marco.mcshop.model.mapper.impl.ProductMapper;
import com.marco.mcshop.model.repository.ProductRepository;
import com.marco.mcshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> productList = StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
        return productList.stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id)
        );

        return productMapper.toDto(product);
    }

    @Override
    public boolean isExist(Long id) {
        return productRepository.existsById(id);
    }
}
