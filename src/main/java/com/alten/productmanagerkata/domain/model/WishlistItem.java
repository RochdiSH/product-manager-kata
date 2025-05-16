package com.alten.productmanagerkata.domain.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistItem {
    private Long id;
    private Long userId;
    private Long productId;
}