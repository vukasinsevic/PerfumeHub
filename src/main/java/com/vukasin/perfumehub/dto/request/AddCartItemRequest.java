package com.vukasin.perfumehub.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddCartItemRequest(

        @NotNull
        @Positive
        Long productVariantId,

        @NotNull
        @Positive
        Integer quantity

) {
}
