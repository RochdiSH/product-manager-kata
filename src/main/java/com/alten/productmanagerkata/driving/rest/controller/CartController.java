package com.alten.productmanagerkata.driving.rest.controller;

import com.alten.productmanagerkata.domain.mapper.dto.CartItemDtoMapper;
import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.domain.service.CartService;
import com.alten.productmanagerkata.domain.service.UserService;
import com.alten.productmanagerkata.driving.dto.CartItemResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final CartItemDtoMapper cartItemDtoMapper;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addToCart(@AuthenticationPrincipal UserDetails user,
                                          @PathVariable Long productId,
                                          @RequestParam(defaultValue = "1") @Min(1) int quantity) {
        Long userId = resolveUserId(user.getUsername());
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromCart(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable Long productId) {
        Long userId = resolveUserId(user.getUsername());
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(@AuthenticationPrincipal UserDetails user) {
        Long userId = resolveUserId(user.getUsername());
        List<CartItemResponse> cart = cartService.getCart(userId).stream()
                .map(cartItemDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(cart);
    }

    private Long resolveUserId(String email) {
        return userService.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
    }
}
