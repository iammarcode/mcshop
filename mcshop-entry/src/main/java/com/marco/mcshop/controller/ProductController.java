package com.marco.mcshop.controller;

import com.marco.mcshop.service.ProductService;
import com.marco.mcshop.model.dto.product.ProductDto;
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
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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
