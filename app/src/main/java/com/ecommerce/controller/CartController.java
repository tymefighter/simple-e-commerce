package com.ecommerce.controller;

import com.ecommerce.dto.CartItemResponseDTO;
import com.ecommerce.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestParam Long itemId, @RequestParam int quantity) {
        CartItemResponseDTO cartItem = cartService.addItemToCart(itemId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @PostMapping("/reduce")
    public ResponseEntity<Map<String, Object>> reduceItemQuantityInCart(@RequestParam Long itemId, @RequestParam int quantity) {
        CartItemResponseDTO updatedCartItem = cartService.reduceItemQuantityInCart(itemId, quantity);
        String message = (updatedCartItem == null) ?
                String.format("Item with ID %d removed from cart as quantity reached zero.", itemId) :
                String.format("Item with ID %d quantity reduced by %d. New quantity: %d.", itemId, quantity, updatedCartItem.getQuantity());
        return ResponseEntity.ok(Map.of("message", message));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Map<String, String>> removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok(Map.of("message", String.format("Item with ID %d removed from cart.", itemId)));
    }

    @GetMapping("/summary")
    public ResponseEntity<Page<CartItemResponseDTO>> getCartSummary(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Page<CartItemResponseDTO> cartItems = cartService.getCartSummary(PageRequest.of(page, size));
        return ResponseEntity.ok(cartItems);
    }
}