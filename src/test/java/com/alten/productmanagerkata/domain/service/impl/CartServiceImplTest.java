package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.CartItemNotFoundException;
import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UserNotFoundException;
import com.alten.productmanagerkata.domain.model.CartItem;
import com.alten.productmanagerkata.domain.service.CartService;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.CartItemRepository;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private Long userId;
    private Long productId;

    @BeforeEach
    void setup() {
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = UserEntity.builder()
                .email("test@user.com")
                .username("testuser")
                .password("hashed")
                .build();

        user = userRepository.save(user);
        userId = user.getId();

        ProductEntity product = ProductEntity.builder()
                .code("TEST123")
                .name("Produit test")
                .price(10.0)
                .quantity(100)
                .build();

        product = productRepository.save(product);
        productId = product.getId();
    }

    @Test
    void shouldIncrementQuantityIfProductAlreadyInCart() {
        cartService.addToCart(userId, productId, 1);
        cartService.addToCart(userId, productId, 3);
        var item = cartItemRepository.findByUserId(userId).get(0);
        assertEquals(4, item.getQuantity());
    }

    @Test
    void shouldThrowIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                cartService.addToCart(9999L, productId, 1));
    }

    @Test
    void shouldThrowIfProductNotFound() {
        assertThrows(ProductNotFoundException.class, () ->
                cartService.addToCart(userId, 9999L, 1));
    }

    @Test
    void shouldThrowIfQuantityIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                cartService.addToCart(userId, productId, 0));
    }

    @Test
    void shouldRemoveProductFromCart() {
        cartService.addToCart(userId, productId, 1);
        cartService.removeFromCart(userId, productId);
        assertTrue(cartItemRepository.findByUserId(userId).isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistingItem() {
        assertThrows(CartItemNotFoundException.class, () ->
                cartService.removeFromCart(userId, productId));
    }

    @Test
    void shouldThrowIfUserNotFoundOnGetCart() {
        assertThrows(UserNotFoundException.class, () ->
                cartService.getCart(9999L));
    }
}
