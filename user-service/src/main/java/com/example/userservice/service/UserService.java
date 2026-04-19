package com.example.userservice.service;

import com.example.userservice.dto.user.UserProfileResponse;
import com.example.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserProfileResponse getProfile(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
