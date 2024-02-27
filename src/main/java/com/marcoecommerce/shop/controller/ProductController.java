package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.model.product.ProductDto;
import com.marcoecommerce.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
