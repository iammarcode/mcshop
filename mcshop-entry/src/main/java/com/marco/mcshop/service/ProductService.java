package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id);

    boolean isExist(Long id);
}
