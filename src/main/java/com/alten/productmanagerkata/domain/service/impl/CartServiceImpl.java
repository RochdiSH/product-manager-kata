package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.CartItemNotFoundException;
import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UserNotFoundException;
import com.alten.productmanagerkata.domain.mapper.CartItemMapper;
import com.alten.productmanagerkata.domain.model.CartItem;
import com.alten.productmanagerkata.domain.service.CartService;
import com.alten.productmanagerkata.driven.persistence.entity.CartItemEntity;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.CartItemRepository;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à zéro.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id: " + userId));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        CartItemEntity existing = cartItemRepository.findByUserIdAndProductId(userId, productId).orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
        } else {
            CartItemEntity item = CartItemEntity.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(item);
        }

        log.info(" {} produit avec l'id {} est ajouté au panier du user {}", quantity, productId, userId);
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        boolean exists = cartItemRepository.findByUserIdAndProductId(userId, productId).isPresent();
        if (!exists) {
            throw new CartItemNotFoundException(userId, productId);
        }
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);

        log.info("produit supprimé du panier " , productId);
    }

    @Override
    public List<CartItem> getCart(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("id: " + userId);
        }

        return cartItemRepository.findByUserId(userId).stream()
                .map(cartItemMapper::toDomain)
                .toList();
    }
}
