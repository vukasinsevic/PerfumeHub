package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.entity.Accord;
import com.vukasin.perfumehub.entity.Note;
import com.vukasin.perfumehub.entity.ProductVariant;
import com.vukasin.perfumehub.entity.Season;

import java.util.List;

public record PerfumeDetailsResponse(

        Long id,
        String name,
        String description,
        Integer releaseYear,
        String imageUrl,
        String brandName,
        String genderName,
        String concentrationName,
        List<String> notes,
        List<String> accords,
        List<String> seasons,
        List<ProductVariantResponse> productVariants,
        Double averageRating,
        Long reviewCount

) {
}
