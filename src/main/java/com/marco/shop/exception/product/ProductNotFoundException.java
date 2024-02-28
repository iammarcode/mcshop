package com.marco.shop.exception.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product not found with ID: " + productId);
    }
}
