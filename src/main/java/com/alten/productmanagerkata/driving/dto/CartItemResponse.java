package com.alten.productmanagerkata.driving.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CartItemResponse")
public record CartItemResponse(
        Long id,
        Long productId,
        int quantity
) {}
