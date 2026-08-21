package com.vukasin.perfumehub.dto.response;

public record OrderCustomerResponse(
        Long id,
        String name,
        String email
) {
}
