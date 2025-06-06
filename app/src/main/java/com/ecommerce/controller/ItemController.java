package com.ecommerce.controller;

import com.ecommerce.dto.CreateItemDTO;
import com.ecommerce.dto.ItemResponseDTO;
import com.ecommerce.dto.UpdateItemDTO;
import com.ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody CreateItemDTO createItemDTO) {
        return ResponseEntity.ok(itemService.createItem(createItemDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> getPaginatedItems(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(itemService.getPaginatedItems(page, size, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(@RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(itemService.updateItem(updateItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(String.format("Item with id %d successfully deleted", id));
    }
}