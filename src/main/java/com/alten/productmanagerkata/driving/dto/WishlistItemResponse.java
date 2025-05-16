package com.alten.productmanagerkata.driving.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WishlistItemResponse")
public record WishlistItemResponse(
        Long id,
        Long productId
) {}
