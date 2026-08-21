package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminOrderSummaryResponse(
        Long id,
        LocalDateTime createdAt,
        OrderStatus status,
        BigDecimal totalPrice,
        OrderCustomerResponse customer
) {
}
