package com.vukasin.perfumehub.dto.response;

import java.time.LocalDateTime;

public record WishlistItemResponse(

        Long id,
        LocalDateTime addedAt,
        Long perfumeId,
        String perfumeName,
        String brandName,
        String imageUrl

) {
}
