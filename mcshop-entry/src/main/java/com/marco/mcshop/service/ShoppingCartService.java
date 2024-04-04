package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.shoppingCart.ShoppingCartDto;
import com.marco.mcshop.model.dto.shoppingCartItem.ShoppingCartItemDto;

public interface ShoppingCartService {
    ShoppingCartDto addItem(ShoppingCartItemDto shoppingCartItemDto);

    ShoppingCartDto updateItem(Long id, ShoppingCartItemDto shoppingCartItemDto);

    ShoppingCartDto deleteItem(Long id) throws Exception;

    ShoppingCartDto clearCart();
}
