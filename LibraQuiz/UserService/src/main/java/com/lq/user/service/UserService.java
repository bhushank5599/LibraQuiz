package com.lq.user.service;

import com.lq.user.dto.UserProfileRequest;
import com.lq.user.dto.UserProfileResponse;

import java.util.List;

public interface UserService {
    UserProfileResponse createUserProfile(UserProfileRequest request);
    UserProfileResponse getUserProfileById(Long id);
    UserProfileResponse getUserProfileByIdentityUserId(Long identityUserId);
    UserProfileResponse updateUserProfile(Long id, UserProfileRequest request);
    List<UserProfileResponse> getAllUsers();
    void deleteUserProfile(Long id);
}
