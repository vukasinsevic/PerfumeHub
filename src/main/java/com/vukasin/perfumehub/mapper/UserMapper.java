package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.UserResponse;
import com.vukasin.perfumehub.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(AppUser user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole()
        );

    }

}
