package com.ecommerce.dto;

import com.ecommerce.model.Item;

public class CreateItemDTO {

    private String name;
    private Double price;
    private String description;

    public CreateItemDTO() {
    }

    public CreateItemDTO(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item toItem() {
        return new Item(this.name, this.price, this.description);
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
