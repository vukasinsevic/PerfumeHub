package com.vukasin.perfumehub.exception;

import com.vukasin.perfumehub.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);

    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);

    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(
            InvalidRequestException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);

    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(
            InsufficientStockException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);

    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenOperation(
            ForbiddenOperationException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);

    }

    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrderStatusTransition(
            InvalidOrderStatusTransitionException exception,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse = createErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request
    ) {

        ErrorResponse response = createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password",
                request
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField()
                                + ": "
                                + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        ErrorResponse response = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    private ErrorResponse createErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

}
