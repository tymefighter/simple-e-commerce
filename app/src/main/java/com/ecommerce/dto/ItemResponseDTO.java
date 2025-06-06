package com.ecommerce.dto;

import com.ecommerce.model.Item;

public record ItemResponseDTO(Long id, String name, Double price, String description) {

    public static ItemResponseDTO fromItem(Item item) {
        return new ItemResponseDTO(item.getId(), item.getName(), item.getPrice(), item.getDescription());
    }
}