package com.marco.shop.controller;

import com.marco.shop.model.product.ProductDto;
import com.marco.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> customerDtoList = productService.findAll();

        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        ProductDto customerFoundDto = productService.findById(id);

        return ResponseEntity.ok(customerFoundDto);
    }
}
