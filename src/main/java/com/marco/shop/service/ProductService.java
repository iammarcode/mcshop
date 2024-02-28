package com.marco.shop.service;

import com.marco.shop.model.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id);

    boolean isExist(Long id);
}
