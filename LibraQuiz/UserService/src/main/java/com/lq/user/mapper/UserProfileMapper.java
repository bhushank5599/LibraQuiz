package com.lq.user.mapper;

import com.lq.user.dto.UserProfileRequest;
import com.lq.user.dto.UserProfileResponse;
import com.lq.user.entity.LibrarianProfile;
import com.lq.user.entity.StudentProfile;
import com.lq.user.entity.TeacherProfile;
import com.lq.user.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfile toEntity(UserProfileRequest request) {
        if (request == null) return null;

        return UserProfile.builder()
                .identityUserId(request.getIdentityUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .avatarUrl(request.getAvatarUrl())
                .userRole(request.getUserRole() != null ? request.getUserRole() : UserProfile.UserRoleType.STUDENT)
                .build();
    }

    public UserProfileResponse toDto(UserProfile profile) {
        if (profile == null) return null;

        UserProfileResponse.UserProfileResponseBuilder builder = UserProfileResponse.builder()
                .id(profile.getId())
                .identityUserId(profile.getIdentityUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .avatarUrl(profile.getAvatarUrl())
                .userRole(profile.getUserRole())
                .createdAt(profile.getCreatedAt());

        if (profile.getStudentProfile() != null) {
            StudentProfile sp = profile.getStudentProfile();
            builder.rollNumber(sp.getRollNumber())
                    .department(sp.getDepartment())
                    .batchYear(sp.getBatchYear())
                    .currentBorrowedCount(sp.getCurrentBorrowedCount())
                    .maxBorrowLimit(sp.getMaxBorrowLimit());
        }

        if (profile.getTeacherProfile() != null) {
            TeacherProfile tp = profile.getTeacherProfile();
            builder.employeeId(tp.getEmployeeId())
                    .department(tp.getDepartment())
                    .qualification(tp.getQualification())
                    .specialization(tp.getSpecialization());
        }

        if (profile.getLibrarianProfile() != null) {
            LibrarianProfile lp = profile.getLibrarianProfile();
            builder.employeeId(lp.getEmployeeId())
                    .deskLocation(lp.getDeskLocation())
                    .shiftTiming(lp.getShiftTiming());
        }

        return builder.build();
    }
}
