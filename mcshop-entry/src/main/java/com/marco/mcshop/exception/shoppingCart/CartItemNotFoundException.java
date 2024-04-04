package com.marco.mcshop.exception.shoppingCart;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long itemId) {
        super("ShoppingCart Item not found with ID: " + itemId);
    }
}
