package com.alten.productmanagerkata.driving.rest.controller;

import com.alten.productmanagerkata.domain.mapper.dto.UserDtoMapper;
import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.domain.service.UserService;
import com.alten.productmanagerkata.driving.dto.LoginRequest;
import com.alten.productmanagerkata.driving.dto.RegisterRequest;
import com.alten.productmanagerkata.driven.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = userDtoMapper.toDomain(request);
        User encoded = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        User created = userService.register(encoded);

        log.info("User successfully registered: {}", created.getEmail());

        return ResponseEntity.ok(Map.of("message", "User created", "email", created.getEmail()));
    }

    @PostMapping("/token")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            String jwt = jwtUtil.generateToken(request.email());
            log.info("Token generated for user: {}", request.email());

            return ResponseEntity.ok(Map.of("token", jwt));
        } catch (AuthenticationException e) {
            log.warn("Échec de login pour {}", request.email());
            return ResponseEntity.status(401).body(Map.of("message", "Identifiants invalides"));
        }
    }

}
