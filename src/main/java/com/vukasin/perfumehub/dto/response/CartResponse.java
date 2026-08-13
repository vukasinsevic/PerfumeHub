package com.vukasin.perfumehub.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(

        Long id,
        List<CartItemResponse> cartItemResponses,
        BigDecimal totalPrice

) {
}
