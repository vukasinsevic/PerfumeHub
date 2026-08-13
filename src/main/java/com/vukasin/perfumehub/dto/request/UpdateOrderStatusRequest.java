package com.vukasin.perfumehub.dto.request;

import com.vukasin.perfumehub.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(

        @NotNull
        OrderStatus status

) {
}
