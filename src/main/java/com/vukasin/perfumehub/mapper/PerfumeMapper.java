package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.PerfumeDetailsResponse;
import com.vukasin.perfumehub.dto.response.PerfumeSummaryResponse;
import com.vukasin.perfumehub.dto.response.ProductVariantResponse;
import com.vukasin.perfumehub.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PerfumeMapper {

    private final ProductVariantMapper productVariantMapper;

    public PerfumeMapper(ProductVariantMapper productVariantMapper) {
        this.productVariantMapper = productVariantMapper;
    }

    public PerfumeDetailsResponse toDetailsResponse(
            Perfume perfume,
            List<ProductVariant> productVariants,
            Double averageRating,
            Long reviewCount) {

        List<ProductVariantResponse> items = productVariantMapper.toResponseList(productVariants);

        List<String> notes = perfume.getNotes().stream()
                .map(Note::getName)
                .toList();

        List<String> accords = perfume.getAccords().stream()
                .map(Accord::getName)
                .toList();

        List<String> seasons = perfume.getSeasons().stream()
                .map(Season::getName)
                .toList();

        return new PerfumeDetailsResponse(
                perfume.getId(),
                perfume.getName(),
                perfume.getDescription(),
                perfume.getReleaseYear(),
                perfume.getImageUrl(),
                perfume.getBrand().getName(),
                perfume.getGender().getName(),
                perfume.getConcentration().getName(),
                notes,
                accords,
                seasons,
                items,
                averageRating,
                reviewCount
        );

    }

    public PerfumeSummaryResponse toSummaryResponse(
            Perfume perfume,
            BigDecimal lowestPrice,
            Double averageRating,
            Long reviewCount) {

        return new PerfumeSummaryResponse(
                perfume.getId(),
                perfume.getName(),
                perfume.getBrand().getName(),
                perfume.getConcentration().getName(),
                perfume.getGender().getName(),
                perfume.getImageUrl(),
                lowestPrice,
                averageRating,
                reviewCount
        );

    }

}
