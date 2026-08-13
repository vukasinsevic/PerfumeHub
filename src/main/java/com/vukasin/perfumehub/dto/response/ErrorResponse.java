package com.vukasin.perfumehub.dto.response;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public record ErrorResponse(

        LocalDateTime timeStamp,
        Integer status,
        String error,
        String message,
        String path

) {
}
