package com.ecommerce.controller;

import com.ecommerce.dto.CartItemResponseDTO;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestParam long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(itemId, quantity));
    }

    @PostMapping("/reduce")
    public ResponseEntity<String> reduceItemQuantityInCart(@RequestParam long itemId, @RequestParam int quantity) {
        Optional<CartItemResponseDTO> updatedCartItem = cartService.reduceItemQuantityInCart(itemId, quantity);

        String message = updatedCartItem
            .map(cartItemResponseDTO -> String.format("Item with ID %d quantity reduced by %d. New quantity: %d",
                                                      itemId, quantity, cartItemResponseDTO.quantity()))
            .orElseGet(() -> String.format("Item with ID %d removed from cart as quantity reached zero", itemId));

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable long itemId) {
        cartService.removeItemFromCart(itemId);

        return ResponseEntity.ok(String.format("Item with ID %d removed from cart", itemId));
    }

    @GetMapping("/items")
    public ResponseEntity<Page<CartItemResponseDTO>> getPaginatedCartItems(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartService.getPaginatedCartItems(page, size));
    }
}