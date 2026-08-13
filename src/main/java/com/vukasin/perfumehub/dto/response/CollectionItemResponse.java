package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.CollectionStatus;

import java.time.LocalDateTime;

public record CollectionItemResponse(

        Long id,
        LocalDateTime addedAt,
        CollectionStatus status,
        Long perfumeId,
        String perfumeName,
        String brandName,
        String imageUrl

) {
}
