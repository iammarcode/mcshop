package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.ShoppingCartDto;
import com.marco.mcshop.model.dto.ShoppingCartItemDto;

public interface ShoppingCartService {
    ShoppingCartDto addItem(ShoppingCartItemDto shoppingCartItemDto) throws Exception;

    ShoppingCartDto partialUpdateItem(Long id, ShoppingCartItemDto shoppingCartItemDto) throws Exception;

    ShoppingCartDto deleteItem(Long id) throws Exception;

    ShoppingCartDto clearCart() throws Exception;
}
