package com.vukasin.perfumehub.security;

import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    private final AppUserRepository userRepository;

    public CurrentUserProvider(
            AppUserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public AppUser getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    public Long getCurrentUserId(Authentication authentication) {
        return getCurrentUser(authentication).getId();
    }

}
