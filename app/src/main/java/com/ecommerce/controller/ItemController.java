package com.ecommerce.controller;

import com.ecommerce.dto.CreateItemDTO;
import com.ecommerce.dto.ItemResponseDTO;
import com.ecommerce.dto.UpdateItemDTO;
import com.ecommerce.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody CreateItemDTO createItemDTO) {
        return ResponseEntity.ok(itemService.createItem(createItemDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> getAllItems(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(itemService.getAllItems(PageRequest.of(page, size, Sort.by(sortBy))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(@RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(itemService.updateItem(updateItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(String.format("Item with id %d deleted", id));
    }
}