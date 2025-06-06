package com.ecommerce.service;

import com.ecommerce.dto.CartItemResponseDTO;
import com.ecommerce.exceptions.InvalidRequestException;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Item;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ItemRepository itemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
    }

    public CartItemResponseDTO addItemToCart(long itemId, int quantity) {
        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

        CartItem cartItem = cartItemRepository.findByItemId(itemId);
        if (cartItem == null) {
            cartItem = new CartItem(item, quantity);
        } else {
            cartItem.incrementQuantity(quantity);
        }

        return CartItemResponseDTO.fromCartItem(cartItemRepository.save(cartItem));
    }

    public Optional<CartItemResponseDTO> reduceItemQuantityInCart(long itemId, int quantity) {
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
            return Optional.empty();
        }

        cartItem.setQuantity(newQuantity);
        return Optional.of(CartItemResponseDTO.fromCartItem(cartItemRepository.save(cartItem)));
    }

    public void removeItemFromCart(long itemId) {
        CartItem cartItem = cartItemRepository.findByItemId(itemId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Item not found in cart with ID: " + itemId);
        }

        cartItemRepository.delete(cartItem);
    }

    public Page<CartItemResponseDTO> getPaginatedCartItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cartItemRepository.findAll(pageable).map(CartItemResponseDTO::fromCartItem);
    }
}