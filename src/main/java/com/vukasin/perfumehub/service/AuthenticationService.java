package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.LoginRequest;
import com.vukasin.perfumehub.dto.request.RegisterRequest;
import com.vukasin.perfumehub.dto.response.AuthenticationResponse;
import com.vukasin.perfumehub.dto.response.UserResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.enums.UserRole;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.UserMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.security.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    public AuthenticationService(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserMapper userMapper,
            JWTService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("User with this email already exists");
        }

        String passwordHash = passwordEncoder.encode(request.password());

        AppUser user = new AppUser(
                request.name(),
                request.email(),
                passwordHash,
                request.phoneNumber(),
                request.address(),
                UserRole.USER
        );

        AppUser savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        AppUser user = userRepository.findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        UserResponse userResponse = userMapper.toResponse(user);

        return new AuthenticationResponse(
                token,
                userResponse
        );
    }

}
