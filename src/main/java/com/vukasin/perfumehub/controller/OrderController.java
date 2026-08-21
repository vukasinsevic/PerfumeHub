package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.response.OrderDetailsResponse;
import com.vukasin.perfumehub.dto.response.OrderSummaryResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CurrentUserProvider currentUserProvider;

    public OrderController(
            OrderService orderService,
            CurrentUserProvider currentUserProvider
    ) {
        this.orderService = orderService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping()
    public ResponseEntity<List<OrderSummaryResponse>> getOrders(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        List<OrderSummaryResponse> response = orderService.getOrders(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<OrderDetailsResponse> createOrder(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        OrderDetailsResponse response = orderService.createOrder(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        OrderDetailsResponse response = orderService.getOrderDetails(userId,orderId);

        return ResponseEntity.ok(response);
    }

}
