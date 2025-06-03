package com.ecommerce.service;

import com.ecommerce.dto.CreateItemDTO;
import com.ecommerce.dto.ItemResponseDTO;
import com.ecommerce.dto.UpdateItemDTO;
import com.ecommerce.exceptions.InvalidRequestException;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Item;
import com.ecommerce.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponseDTO createItem(CreateItemDTO createItemDTO) {
        Item savedItem = itemRepository.save(createItemDTO.toItem());
        return ItemResponseDTO.toItemResponseDTO(savedItem);
    }

    public Page<ItemResponseDTO> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable).map(ItemResponseDTO::toItemResponseDTO);
    }

    public ItemResponseDTO getItemById(Long id) {
        return itemRepository.findById(id).map(ItemResponseDTO::toItemResponseDTO).orElseThrow(
                () -> new ResourceNotFoundException("Item not found with ID: " + id));
    }

    public ItemResponseDTO updateItem(UpdateItemDTO updateItemDTO) {
        Long id = updateItemDTO.getId();
        if (id == null) {
            throw new InvalidRequestException("Item ID cannot be null");
        }

        Item existingItem = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));

        existingItem.setName(updateItemDTO.getName());
        existingItem.setPrice(updateItemDTO.getPrice());
        existingItem.setDescription(updateItemDTO.getDescription());

        return ItemResponseDTO.toItemResponseDTO(itemRepository.save(existingItem));
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}