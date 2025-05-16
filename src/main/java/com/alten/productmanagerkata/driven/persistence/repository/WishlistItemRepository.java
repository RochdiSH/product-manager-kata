package com.alten.productmanagerkata.driven.persistence.repository;

import com.alten.productmanagerkata.driven.persistence.entity.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItemEntity, Long> {
    List<WishlistItemEntity> findByUserId(Long userId);
    Optional<WishlistItemEntity> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
