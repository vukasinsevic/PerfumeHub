package com.vukasin.perfumehub.dto.response;

public record AuthenticationResponse(

        String token,
        UserResponse user

) {
}
