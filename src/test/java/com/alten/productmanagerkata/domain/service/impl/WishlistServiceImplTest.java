package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UserNotFoundException;
import com.alten.productmanagerkata.domain.exception.WishlistItemNotFoundException;
import com.alten.productmanagerkata.domain.model.WishlistItem;
import com.alten.productmanagerkata.domain.service.WishlistService;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import com.alten.productmanagerkata.driven.persistence.repository.WishlistItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishlistServiceImplTest {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userId;
    private Long productId;

    @BeforeEach
    void setup() {
        wishlistItemRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = UserEntity.builder()
                .email("wish@user.com")
                .username("wishuser")
                .password("hashed")
                .build();
        user = userRepository.save(user);
        userId = user.getId();

        ProductEntity product = ProductEntity.builder()
                .code("W001")
                .name("Wish product")
                .build();
        product = productRepository.save(product);
        productId = product.getId();
    }

    @Test
    void shouldAddProductToWishlistSuccessfully() {
        wishlistService.addToWishlist(userId, productId);
        assertEquals(1, wishlistItemRepository.findByUserId(userId).size());
    }

    @Test
    void shouldNotDuplicateWishlistItem() {
        wishlistService.addToWishlist(userId, productId);
        wishlistService.addToWishlist(userId, productId);
        assertEquals(1, wishlistItemRepository.findByUserId(userId).size());
    }

    @Test
    void shouldThrowIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            wishlistService.addToWishlist(9999L, productId);
        });
    }

    @Test
    void shouldThrowIfProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> {
            wishlistService.addToWishlist(userId, 9999L);
        });
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        wishlistService.addToWishlist(userId, productId);
        wishlistService.removeFromWishlist(userId, productId);
        assertTrue(wishlistItemRepository.findByUserId(userId).isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistingItem() {
        assertThrows(WishlistItemNotFoundException.class, () ->
                wishlistService.removeFromWishlist(userId, productId));
    }

    @Test
    void shouldReturnWishlistItems() {
        wishlistService.addToWishlist(userId, productId);
        List<WishlistItem> items = wishlistService.getWishlist(userId);
        assertEquals(1, items.size());
        assertEquals(productId, items.get(0).getProductId());
    }

    @Test
    void shouldThrowIfUserNotFoundWhenGettingWishlist() {
        assertThrows(UserNotFoundException.class, () -> {
            wishlistService.getWishlist(9999L);
        });
    }
}
