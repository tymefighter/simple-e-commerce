package com.ecommerce.service;

import com.ecommerce.dto.CartItemResponseDTO;
import com.ecommerce.exceptions.InvalidRequestException;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Item;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    public CartService(CartItemRepository cartItemRepository, ItemRepository itemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
    }

    public CartItemResponseDTO addItemToCart(Long itemId, int quantity) {
        if (itemId == null) {
            throw new InvalidRequestException("Item ID must not be null");
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

        CartItem cartItem = cartItemRepository.findByItemId(itemId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem(item, quantity);
        }

        return CartItemResponseDTO.fromCartItem(cartItemRepository.save(cartItem));
    }

    public CartItemResponseDTO reduceItemQuantityInCart(Long itemId, int quantity) {
        if (itemId == null) {
            throw new InvalidRequestException("Item ID must not be null");
        }

        CartItem cartItem = cartItemRepository.findByItemId(itemId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("Item not found in cart with ID: " + itemId);
        }

        int newQuantity = cartItem.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new InvalidRequestException("Item count in cart cannot be negative");
        }

        if (newQuantity == 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        cartItem.setQuantity(newQuantity);
        return CartItemResponseDTO.fromCartItem(cartItemRepository.save(cartItem));
    }

    public void removeItemFromCart(Long itemId) {
        if (itemId == null) {
            throw new InvalidRequestException("Item ID must not be null");
        }

        CartItem cartItem = cartItemRepository.findByItemId(itemId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("Item not found in cart with ID: " + itemId);
        }

        cartItemRepository.delete(cartItem);
    }

    public Page<CartItemResponseDTO> getCartSummary(Pageable pageable) {
        return cartItemRepository.findAll(pageable).map(CartItemResponseDTO::fromCartItem);
    }
}