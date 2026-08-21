package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.AddWishlistItemRequest;
import com.vukasin.perfumehub.dto.response.WishlistItemResponse;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final CurrentUserProvider currentUserProvider;

    public WishlistController(
            WishlistService wishlistService,
            CurrentUserProvider currentUserProvider
    ) {
        this.wishlistService = wishlistService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping()
    public ResponseEntity<List<WishlistItemResponse>> getWishlist(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        List<WishlistItemResponse> items = wishlistService.getWishlistItems(userId);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    public ResponseEntity<WishlistItemResponse> addPerfumeToWishlist(
            @Valid @RequestBody AddWishlistItemRequest request,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        WishlistItemResponse response = wishlistService.addPerfumeToWishlist(
                userId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/items/{perfumeId}")
    public ResponseEntity<Void> removePerfumeFromWishlist(
            @PathVariable Long perfumeId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        wishlistService.removePerfumeFromWishlist(perfumeId,userId);

        return ResponseEntity.noContent().build();
    }

}
