package com.example.userservice.dto.user;

public record UserProfileResponse(
        String id,
        String fullName,
        String email,
        String role
) {
}
