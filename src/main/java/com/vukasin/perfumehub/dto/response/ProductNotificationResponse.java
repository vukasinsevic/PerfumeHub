package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.NotificationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductNotificationResponse(

        Long id,
        NotificationType type,
        boolean active,
        LocalDateTime createdAt,
        BigDecimal lastKnownPrice,
        Long productVariantId,
        Integer volumeMl,
        BigDecimal currentPrice,
        Long perfumeId,
        String perfumeName,
        String brandName,
        String imageUrl

) {
}
