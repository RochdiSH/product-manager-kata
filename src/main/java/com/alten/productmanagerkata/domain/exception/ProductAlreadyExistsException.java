package com.alten.productmanagerkata.domain.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String code) {
        super("Un produit avec le code '" + code + "' existe déjà.");
    }
}
