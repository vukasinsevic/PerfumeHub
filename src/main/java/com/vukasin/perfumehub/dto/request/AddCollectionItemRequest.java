package com.vukasin.perfumehub.dto.request;

import com.vukasin.perfumehub.enums.CollectionStatus;
import jakarta.validation.constraints.NotNull;

public record AddCollectionItemRequest(

        @NotNull
        Long perfumeId,

        @NotNull
        CollectionStatus status

) {
}
