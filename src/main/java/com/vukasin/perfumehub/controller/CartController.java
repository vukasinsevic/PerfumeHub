package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.AddCartItemRequest;
import com.vukasin.perfumehub.dto.request.UpdateCartItemQuantityRequest;
import com.vukasin.perfumehub.dto.response.CartResponse;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CurrentUserProvider currentUserProvider;

    public CartController(
            CartService cartService,
            CurrentUserProvider currentUserProvider
    ) {
        this.cartService = cartService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping()
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        CartResponse response = cartService.getCart(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addCartItem(
            @Valid @RequestBody AddCartItemRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        CartResponse response = cartService.addItemToCart(userId,request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemQuantityRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        CartResponse response = cartService.updateCartItemQuantity(
                userId,
                cartItemId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable Long cartItemId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        cartService.removeCartItem(userId,cartItemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        cartService.clearCart(userId);

        return ResponseEntity.noContent().build();
    }

}
