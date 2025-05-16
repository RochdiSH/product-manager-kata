package com.alten.productmanagerkata.driving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest")
public record LoginRequest(
        @Email @NotBlank @Schema(example = "admin@admin.com") String email,
        @NotBlank @Schema(example = "admin123") String password
) {}
