package com.lq.identity.service;

import com.lq.identity.dto.AuthResponse;
import com.lq.identity.dto.LoginRequest;
import com.lq.identity.dto.RefreshTokenRequest;
import com.lq.identity.dto.RegisterRequest;

import java.util.List;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    void logout(String username);
    List<AuthResponse> getAllUsers();
    AuthResponse updateUserStatus(Long userId, boolean enabled);
    void deleteUser(Long userId);
}
