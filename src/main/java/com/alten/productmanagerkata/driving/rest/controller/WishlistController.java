package com.alten.productmanagerkata.driving.rest.controller;

import com.alten.productmanagerkata.domain.mapper.dto.WishlistItemDtoMapper;
import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.domain.service.UserService;
import com.alten.productmanagerkata.domain.service.WishlistService;
import com.alten.productmanagerkata.driving.dto.WishlistItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final WishlistItemDtoMapper wishlistItemDtoMapper;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addToWishlist(@AuthenticationPrincipal UserDetails user,
                                              @PathVariable Long productId) {
        Long userId = resolveUserId(user.getUsername());
        wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@AuthenticationPrincipal UserDetails user,
                                                   @PathVariable Long productId) {
        Long userId = resolveUserId(user.getUsername());
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WishlistItemResponse>> getWishlist(@AuthenticationPrincipal UserDetails user) {
        Long userId = resolveUserId(user.getUsername());
        List<WishlistItemResponse> result = wishlistService.getWishlist(userId).stream()
                .map(wishlistItemDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(result);
    }

    private Long resolveUserId(String email) {
        return userService.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
    }
}
