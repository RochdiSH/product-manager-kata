package com.alten.productmanagerkata.domain.service;

import com.alten.productmanagerkata.domain.model.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
