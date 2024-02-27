package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id);

    boolean isExist(Long id);
}
