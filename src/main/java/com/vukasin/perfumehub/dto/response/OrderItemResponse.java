package com.vukasin.perfumehub.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long id,
        Long productVariantId,
        Long perfumeId,
        String perfumeName,
        String brandName,
        String imageUrl,
        Integer volumeMl,
        BigDecimal pricePerItem,
        Integer quantity,
        BigDecimal subtotal

) {
}