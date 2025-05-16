package com.alten.productmanagerkata.domain.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id, Long productId) {
        super("Article de panier introuvable avec l'id : " + id + " et le produit id : " + productId);
    }
}
