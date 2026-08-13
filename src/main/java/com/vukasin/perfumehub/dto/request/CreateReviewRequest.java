package com.vukasin.perfumehub.dto.request;

import jakarta.validation.constraints.*;

public record CreateReviewRequest(

        @NotNull
        @Min(1)
        @Max(5)
        Integer rating,

        @Size(max = 2000)
        String text

) {
}
