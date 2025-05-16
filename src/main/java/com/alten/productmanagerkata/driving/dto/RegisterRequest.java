package com.alten.productmanagerkata.driving.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "RegisterRequest")
public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String firstname,
        @Email @NotBlank String email,
        @Size(min = 6) String password
) {}
