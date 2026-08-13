package com.vukasin.perfumehub.dto.response;

import com.vukasin.perfumehub.enums.UserRole;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String address,
        UserRole role
) {
}
