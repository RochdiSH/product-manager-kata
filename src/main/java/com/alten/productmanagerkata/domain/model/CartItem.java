package com.alten.productmanagerkata.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
}
