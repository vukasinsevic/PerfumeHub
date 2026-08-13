package com.vukasin.perfumehub.dto.response;

import java.math.BigDecimal;

public record ProductVariantResponse(

        Long id,
        Integer volumeMl,
        BigDecimal price,
        Integer stock,
        boolean active,
        Long perfumeId,
        String perfumeName

) {
}
