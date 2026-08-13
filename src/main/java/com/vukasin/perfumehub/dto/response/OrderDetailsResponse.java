package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(

        Long id,
        LocalDateTime createdAt,
        OrderStatus status,
        BigDecimal totalPrice,
        String shippingAddress,
        List<OrderItemResponse> items

) {
}