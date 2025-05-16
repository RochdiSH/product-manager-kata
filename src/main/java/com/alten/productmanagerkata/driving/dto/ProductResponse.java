package com.alten.productmanagerkata.driving.dto;

import com.alten.productmanagerkata.domain.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ProductResponse")
public record ProductResponse(
        Long id,
        String code,
        String name,
        String description,
        String image,
        String category,
        double price,
        int quantity,
        String internalReference,
        Long shellId,
        Product.InventoryStatus inventoryStatus,
        int rating,
        Instant createdAt,
        Instant updatedAt
) {}