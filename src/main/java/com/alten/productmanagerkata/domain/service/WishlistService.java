package com.alten.productmanagerkata.domain.service;

import com.alten.productmanagerkata.domain.model.WishlistItem;

import java.util.List;

public interface WishlistService {

    void addToWishlist(Long userId, Long productId);

    void removeFromWishlist(Long userId, Long productId);

    List<WishlistItem> getWishlist(Long userId);
}
