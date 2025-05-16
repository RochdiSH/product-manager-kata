package com.alten.productmanagerkata.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Utilisateur introuvable avec l'email : " + email);
    }
}
