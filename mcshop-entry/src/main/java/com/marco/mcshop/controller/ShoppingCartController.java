package com.marco.mcshop.controller;

import com.marco.mcshop.model.dto.shoppingCart.ShoppingCartDto;
import com.marco.mcshop.model.dto.shoppingCartItem.ShoppingCartItemDto;
import com.marco.mcshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart/items")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDto> addItem(@RequestBody ShoppingCartItemDto shoppingCartItemDto) throws Exception {
        ShoppingCartDto shoppingCartDto = shoppingCartService.addItem(shoppingCartItemDto);

        return ResponseEntity.ok(shoppingCartDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShoppingCartDto> partialUpdateItem(
            @PathVariable("id") Long id,
            @RequestBody ShoppingCartItemDto shoppingCartItemDto
    ) throws Exception {
        ShoppingCartDto shoppingCartDto = shoppingCartService.partialUpdateItem(id, shoppingCartItemDto);

        return ResponseEntity.ok(shoppingCartDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItemById(@PathVariable("id") Long id) throws Exception {
        ShoppingCartDto shoppingCartDto = shoppingCartService.deleteItem(id);

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartDto);
    }

    @DeleteMapping("/clear")
    public ResponseEntity clearItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShoppingCartDto shoppingCartDto = shoppingCartService.clearCart();

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartDto);
    }
}
