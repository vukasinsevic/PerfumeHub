package com.vukasin.perfumehub.controller.admin;

import com.vukasin.perfumehub.dto.request.UpdateOrderStatusRequest;
import com.vukasin.perfumehub.dto.response.AdminOrderSummaryResponse;
import com.vukasin.perfumehub.dto.response.OrderDetailsResponse;
import com.vukasin.perfumehub.service.AdminOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(
            AdminOrderService adminOrderService
    ) {
        this.adminOrderService = adminOrderService;
    }

    @GetMapping
    public ResponseEntity<List<AdminOrderSummaryResponse>> getOrders() {

        List<AdminOrderSummaryResponse> response = adminOrderService.getOrders();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDetailsResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
            ) {

        OrderDetailsResponse response = adminOrderService.updateOrderStatus(
                orderId,
                request
        );

        return ResponseEntity.ok(response);
    }

}
