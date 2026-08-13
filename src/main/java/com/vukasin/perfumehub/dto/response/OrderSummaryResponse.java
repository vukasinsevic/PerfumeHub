package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryResponse(

        Long id,
        LocalDateTime createdAt,
        OrderStatus status,
        BigDecimal totalPrice

) {
}
