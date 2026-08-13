package com.vukasin.perfumehub.dto.request;

import com.vukasin.perfumehub.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductNotificationRequest(

        @NotNull
        @Positive
        Long productVariantId,

        @NotNull
        NotificationType type

) {
}
