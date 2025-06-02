package com.ecommerce.dto;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Item;

public class CartItemResponseDTO {

    private Long itemId;
    private String itemName;
    private Double itemPrice;
    private int quantity;

    public CartItemResponseDTO(Long itemId, String itemName, Double itemPrice, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public static CartItemResponseDTO fromCartItem(CartItem cartItem) {
        Item item = cartItem.getItem();
        return new CartItemResponseDTO(item.getId(), item.getName(), item.getPrice(), cartItem.getQuantity());
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}