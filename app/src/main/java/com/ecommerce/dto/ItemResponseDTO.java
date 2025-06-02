package com.ecommerce.dto;

import com.ecommerce.model.Item;

public class ItemResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;

    public ItemResponseDTO() {
    }

    public ItemResponseDTO(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static ItemResponseDTO toItemResponseDTO(Item item) {
        return new ItemResponseDTO(item.getId(), item.getName(), item.getPrice(), item.getDescription());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}