package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.LoginRequest;
import com.vukasin.perfumehub.dto.request.RegisterRequest;
import com.vukasin.perfumehub.dto.response.AuthenticationResponse;
import com.vukasin.perfumehub.dto.response.UserResponse;
import com.vukasin.perfumehub.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
            ) {

        UserResponse response = authenticationService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request
            ) {

        AuthenticationResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

}
