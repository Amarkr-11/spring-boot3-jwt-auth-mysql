package com.loginmodulejwt.dto;

import com.loginmodulejwt.model.Role;
public record RegisterResponse(
        Long id,
        String username,
        String email,
        Role role,
        String token
) { }
