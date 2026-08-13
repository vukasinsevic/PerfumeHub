package com.vukasin.perfumehub.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(

        Long id,
        Long productVariantId,
        Long perfumeId,
        String perfumeName,
        String brandName,
        String imageUrl,
        Integer volumeMl,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal

) {
}
