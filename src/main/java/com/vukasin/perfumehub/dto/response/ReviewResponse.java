package com.vukasin.perfumehub.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(

        Long id,
        Integer rating,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId,
        String userName

) {
}
