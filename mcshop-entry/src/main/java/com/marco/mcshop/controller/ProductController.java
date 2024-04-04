package com.marco.mcshop.controller;

import com.marco.mcshop.model.dto.product.ProductDto;
import com.marco.mcshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> customerDtoList = productService.findAll();

        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        ProductDto customerFoundDto = productService.findById(id);

        return ResponseEntity.ok(customerFoundDto);
    }
}
