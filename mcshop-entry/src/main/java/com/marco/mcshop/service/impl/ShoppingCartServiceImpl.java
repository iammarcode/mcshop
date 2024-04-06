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

import java.math.BigDecimal;

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
    public ShoppingCartDto addItem(ShoppingCartItemDto shoppingCartItemDto) throws Exception {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        Long productId = shoppingCartItemDto.getProduct().getId();
        this.checkProductValidity(productId);

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

        currentCart.setTotal(this.calculateAmount(currentCart));

        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    @Override
    public ShoppingCartDto partialUpdateItem(Long id, ShoppingCartItemDto updateCartItemDto) throws Exception {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        ShoppingCartItemEntity cartItemFound = currentCart.getShoppingCartItemList().stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new CartItemNotFoundException(id));

        //TODO: update others attributes
        if (updateCartItemDto.getQuantity() != null) cartItemFound.setQuantity(updateCartItemDto.getQuantity());

        currentCart.setTotal(this.calculateAmount(currentCart));

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

        currentCart.removeAssociationCartItem(cartItemToRemove);

        currentCart.setTotal(this.calculateAmount(currentCart));

        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    @Override
    public ShoppingCartDto clearCart() throws Exception {
        ShoppingCartEntity currentCart = customerService.getCurrentCustomer().getShoppingCart();

        currentCart.clearAssociationCartItem();

        currentCart.setTotal(this.calculateAmount(currentCart));
        
        ShoppingCartEntity shoppingCartUpdated = shoppingCartRepository.save(currentCart);

        return shoppingCartMapper.toDto(shoppingCartUpdated);
    }

    private BigDecimal calculateAmount(ShoppingCartEntity currentCart) {
        BigDecimal total = BigDecimal.ZERO;
        for (ShoppingCartItemEntity cartItem : currentCart.getShoppingCartItemList()) {
            BigDecimal totalByItem;
            BigDecimal priceByItem;

            Long productId = cartItem.getProduct().getId();
            ProductDto productFound = productService.findById(productId);

            //TODO: discount
            priceByItem = productFound.getPrice();

            totalByItem = BigDecimal.valueOf(cartItem.getQuantity()).multiply(priceByItem);

            total = total.add(totalByItem);
        }

        return total;
    }

    private void checkProductValidity(Long id) {
        boolean isExist = productService.isExist(id);
        if (isExist == false) {
            throw new ProductNotFoundException(id);
        }
    }
}
