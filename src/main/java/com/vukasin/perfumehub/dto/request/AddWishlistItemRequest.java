package com.vukasin.perfumehub.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddWishlistItemRequest(

        @NotNull
        Long perfumeId

) {
}
