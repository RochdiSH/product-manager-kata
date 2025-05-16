package com.alten.productmanagerkata.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class User {

    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String password; // hash password
    private Instant createdAt;
    private Instant updatedAt;
}
