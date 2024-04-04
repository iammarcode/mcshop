package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.product.ProductNotFoundException;
import com.marco.mcshop.exception.shoppingCart.CartItemNotFoundException;
import com.marco.mcshop.model.dto.product.ProductDto;
import com.marco.mcshop.model.dto.shoppingCart.ShoppingCartDto;
import com.marco.mcshop.model.dto.shoppingCartItem.ShoppingCartItemDto;
import com.marco.mcshop.model.entity.ShoppingCartEntity;
import com.marco.mcshop.model.entity.ShoppingCartItemEntity;
import com.marco.mcshop.model.mapper.impl.ProductMapper;
import com.marco.mcshop.model.mapper.impl.ShoppingCartItemMapper;
import com.marco.mcshop.model.mapper.impl.ShoppingCartMapper;
import com.marco.mcshop.model.repository.ShoppingCartRepository;
import com.marco.mcshop.service.CustomerService;
import com.marco.mcshop.service.ProductService;
import com.marco.mcshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(CustomerService customerService, ProductService productService, ProductMapper productMapper, ShoppingCartMapper shoppingCartMapper, ShoppingCartItemMapper shoppingCartItemMapper, ShoppingCartRepository shoppingCartRepository) {
        this.customerService = customerService;
        this.productService = productService;
        this.productMapper = productMapper;
        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCartDto addItem(ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        Long productId = shoppingCartItemDto.getProduct().getId();
        ProductDto productFound = productService.findById(productId);
        if (productFound == null) {
            throw new ProductNotFoundException(productId);
        }

        Boolean isProductExist = false;
        for (ShoppingCartItemEntity existingCartItem: currentCart.getShoppingCartItemList()) {
            // modify quantity for existing item
            if (existingCartItem.getProduct().getId() == productId) {
                isProductExist = true;
                existingCartItem.setQuantity(existingCartItem.getQuantity() + shoppingCartItemDto.getQuantity());
            }
        }

        // add new item
        if (isProductExist == false) {
            currentCart.addAssociationCartItem(shoppingCartItemMapper.toEntity(shoppingCartItemDto));
        }

        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    @Override
    public ShoppingCartDto updateItem(Long id, ShoppingCartItemDto updateCartItemDto) {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        // update quantity
        for (ShoppingCartItemEntity existingCartItem: currentCart.getShoppingCartItemList()) {
            if (existingCartItem.getId() == id) {
                existingCartItem.setQuantity(updateCartItemDto.getQuantity());
            }
        }

        //TODO: update others attributes

        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    @Override
    public ShoppingCartDto deleteItem(Long id) throws Exception {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        ShoppingCartItemEntity cartItemToRemove = currentCart.getShoppingCartItemList()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new CartItemNotFoundException(id));

        if (cartItemToRemove != null) {
            currentCart.removeAssociationCartItem(cartItemToRemove);
        }

        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    @Override
    public ShoppingCartDto clearCart() {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        currentCart.clearAssociationCartItem();
        
        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }
}
