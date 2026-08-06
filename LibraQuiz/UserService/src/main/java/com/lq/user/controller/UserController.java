package com.lq.user.controller;

import com.lq.user.dto.UserProfileRequest;
import com.lq.user.dto.UserProfileResponse;
import com.lq.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> createUserProfile(@Valid @RequestBody UserProfileRequest request) {
        return new ResponseEntity<>(userService.createUserProfile(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @GetMapping("/identity/{identityUserId}")
    public ResponseEntity<UserProfileResponse> getUserProfileByIdentityUserId(@PathVariable Long identityUserId) {
        return ResponseEntity.ok(userService.getUserProfileByIdentityUserId(identityUserId));
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        userService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
