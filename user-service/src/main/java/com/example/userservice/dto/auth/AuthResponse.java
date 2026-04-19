package com.example.userservice.dto.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String userId,
        String email,
        String fullName,
        String role
) {
}
