package com.vukasin.perfumehub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePerfumeRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 2000)
        String description,

        @NotNull
        @Positive
        Integer releaseYear,

        @NotBlank
        @Size(max = 255)
        String imageUrl,

        @NotNull
        @Positive
        Long brandId,

        @NotNull
        @Positive
        Long genderId,

        @NotNull
        @Positive
        Long concentrationId,

        List<@Positive Long> noteIds,

        List<@Positive Long> accordIds,

        List<@Positive Long> seasonIds

) {
}
