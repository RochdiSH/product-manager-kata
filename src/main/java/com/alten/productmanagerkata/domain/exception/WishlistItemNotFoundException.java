package com.alten.productmanagerkata.domain.exception;

public class WishlistItemNotFoundException extends RuntimeException {
    public WishlistItemNotFoundException(Long userId, Long productId) {
        super("Aucun élément dans la wishlist pour userId = " + userId + ", productId = " + productId);
    }
}

