package com.alten.productmanagerkata.domain.service;

import com.alten.productmanagerkata.domain.model.CartItem;

import java.util.List;

public interface CartService {

    void addToCart(Long userId, Long productId, int quantity);

    void removeFromCart(Long userId, Long productId);

    List<CartItem> getCart(Long userId);
}
