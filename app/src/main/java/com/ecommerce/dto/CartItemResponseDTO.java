package com.ecommerce.dto;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Item;

public record CartItemResponseDTO(Long itemId, String itemName, Double itemPrice, int quantity) {

    public static CartItemResponseDTO fromCartItem(CartItem cartItem) {
        Item item = cartItem.getItem();
        return new CartItemResponseDTO(item.getId(), item.getName(), item.getPrice(), cartItem.getQuantity());
    }
}