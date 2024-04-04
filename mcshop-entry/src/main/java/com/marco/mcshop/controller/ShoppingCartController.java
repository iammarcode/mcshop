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
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/item")
    public ResponseEntity<ShoppingCartDto> addItem(@RequestBody ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartDto shoppingCartDto = shoppingCartService.addItem(shoppingCartItemDto);

        return ResponseEntity.ok(shoppingCartDto);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<ShoppingCartDto> updateItem(
            @PathVariable("id") Long id,
            @RequestBody ShoppingCartItemDto shoppingCartItemDto
    ) {
        ShoppingCartDto shoppingCartDto = shoppingCartService.updateItem(id, shoppingCartItemDto);

        return ResponseEntity.ok(shoppingCartDto);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id) throws Exception {
        shoppingCartService.deleteItem(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear")
    public ResponseEntity clearCart(HttpServletRequest request, HttpServletResponse response) {
        shoppingCartService.clearCart();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
