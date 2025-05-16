package com.alten.productmanagerkata.driving.dto;

import com.alten.productmanagerkata.domain.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateProductRequest")
public record CreateProductRequest(
        @NotBlank String code,
        @NotBlank String name,
        String description,
        String image,
        String category,
        @DecimalMin("0.01") double price,
        @Min(0) int quantity,
        String internalReference,
        Long shellId,
        @NotNull Product.InventoryStatus inventoryStatus,
        int rating
) {}
