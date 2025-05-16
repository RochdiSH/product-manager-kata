package com.alten.productmanagerkata.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produit introuvable avec l'id : " + id);
    }
}
