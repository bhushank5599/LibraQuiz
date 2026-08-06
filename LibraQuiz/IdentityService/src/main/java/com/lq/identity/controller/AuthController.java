package com.lq.identity.controller;

import com.lq.identity.dto.AuthResponse;
import com.lq.identity.dto.LoginRequest;
import com.lq.identity.dto.RefreshTokenRequest;
import com.lq.identity.dto.RegisterRequest;
import com.lq.identity.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AuthResponse>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<AuthResponse> updateUserStatus(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(authService.updateUserStatus(id, enabled));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String username) {
        authService.logout(username);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
