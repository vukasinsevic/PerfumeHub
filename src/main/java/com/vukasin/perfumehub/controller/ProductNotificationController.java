package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.CreateProductNotificationRequest;
import com.vukasin.perfumehub.dto.request.UpdateProductNotificationRequest;
import com.vukasin.perfumehub.dto.response.ProductNotificationResponse;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.ProductNotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-notifications")
public class ProductNotificationController {

    private final ProductNotificationService productNotificationService;
    private final CurrentUserProvider currentUserProvider;

    public ProductNotificationController(
            ProductNotificationService productNotificationService,
            CurrentUserProvider currentUserProvider
    ) {
        this.productNotificationService = productNotificationService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping()
    public ResponseEntity<List<ProductNotificationResponse>> getProductNotifications(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        List<ProductNotificationResponse> response = productNotificationService.getProductNotifications(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ProductNotificationResponse> createProductNotification(
            @Valid @RequestBody CreateProductNotificationRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        ProductNotificationResponse response = productNotificationService.createProductNotification(
                userId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<ProductNotificationResponse> updateProductNotification(
            @PathVariable Long notificationId,
            @Valid @RequestBody UpdateProductNotificationRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        ProductNotificationResponse response = productNotificationService.updateProductNotification(
                userId,
                notificationId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long notificationId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        productNotificationService.deleteProductNotification(userId, notificationId);

        return ResponseEntity.noContent().build();
    }

}
