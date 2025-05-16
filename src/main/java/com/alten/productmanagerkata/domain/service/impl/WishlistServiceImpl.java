package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UserNotFoundException;
import com.alten.productmanagerkata.domain.exception.WishlistItemNotFoundException;
import com.alten.productmanagerkata.domain.mapper.WishlistItemMapper;
import com.alten.productmanagerkata.domain.model.WishlistItem;
import com.alten.productmanagerkata.domain.service.WishlistService;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.entity.WishlistItemEntity;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import com.alten.productmanagerkata.driven.persistence.repository.WishlistItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistItemMapper wishlistItemMapper;

    @Override
    public void addToWishlist(Long userId, Long productId) {
        if (wishlistItemRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            return;
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id: " + userId));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        WishlistItemEntity entity = WishlistItemEntity.builder()
                .user(user)
                .product(product)
                .build();

        wishlistItemRepository.save(entity);

        log.info("Produit {} ajouté à la wishlist de l'utilisateur {}", productId, userId);
    }

    @Override
    public void removeFromWishlist(Long userId, Long productId) {
        boolean exists = wishlistItemRepository.findByUserIdAndProductId(userId, productId).isPresent();
        if (!exists) {
            throw new WishlistItemNotFoundException(userId, productId);
        }
        wishlistItemRepository.deleteByUserIdAndProductId(userId, productId);

        log.info("Produit {} supprimé de la wishlist de l'utilisateur {}", productId, userId);
    }

    @Override
    public List<WishlistItem> getWishlist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("id: " + userId);
        }

        return wishlistItemRepository.findByUserId(userId).stream()
                .map(wishlistItemMapper::toDomain)
                .toList();
    }
}
