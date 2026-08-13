package com.vukasin.perfumehub.dto.response;

import java.math.BigDecimal;

public record PerfumeSummaryResponse(

        Long id,
        String name,
        String brandName,
        String concentrationName,
        String genderName,
        String imageUrl,
        BigDecimal lowestPrice,
        Double averageRating,
        Long reviewCount

) {
}
