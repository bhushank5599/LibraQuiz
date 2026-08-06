package com.lq.user.service.impl;

import com.lq.user.dto.UserProfileRequest;
import com.lq.user.dto.UserProfileResponse;
import com.lq.user.entity.LibrarianProfile;
import com.lq.user.entity.StudentProfile;
import com.lq.user.entity.TeacherProfile;
import com.lq.user.entity.UserProfile;
import com.lq.user.exception.ResourceNotFoundException;
import com.lq.user.mapper.UserProfileMapper;
import com.lq.user.repository.UserProfileRepository;
import com.lq.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    @Transactional
    public UserProfileResponse createUserProfile(UserProfileRequest request) {
        UserProfile userProfile = userProfileMapper.toEntity(request);

        if (request.getUserRole() == UserProfile.UserRoleType.STUDENT) {
            StudentProfile studentProfile = StudentProfile.builder()
                    .userProfile(userProfile)
                    .rollNumber(request.getRollNumber())
                    .department(request.getDepartment())
                    .batchYear(request.getBatchYear())
                    .build();
            userProfile.setStudentProfile(studentProfile);
        } else if (request.getUserRole() == UserProfile.UserRoleType.TEACHER) {
            TeacherProfile teacherProfile = TeacherProfile.builder()
                    .userProfile(userProfile)
                    .employeeId(request.getEmployeeId())
                    .department(request.getDepartment())
                    .qualification(request.getQualification())
                    .specialization(request.getSpecialization())
                    .build();
            userProfile.setTeacherProfile(teacherProfile);
        } else if (request.getUserRole() == UserProfile.UserRoleType.LIBRARIAN) {
            LibrarianProfile librarianProfile = LibrarianProfile.builder()
                    .userProfile(userProfile)
                    .employeeId(request.getEmployeeId())
                    .deskLocation(request.getDeskLocation())
                    .shiftTiming(request.getShiftTiming())
                    .build();
            userProfile.setLibrarianProfile(librarianProfile);
        }

        UserProfile saved = userProfileRepository.save(userProfile);
        return userProfileMapper.toDto(saved);
    }

    @Override
    public UserProfileResponse getUserProfileById(Long id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id: " + id));
        return userProfileMapper.toDto(profile);
    }

    @Override
    public UserProfileResponse getUserProfileByIdentityUserId(Long identityUserId) {
        UserProfile profile = userProfileRepository.findByIdentityUserId(identityUserId)
                .orElseGet(() -> {
                    // Fallback create default student profile for existing identity user
                    UserProfile newProfile = UserProfile.builder()
                            .identityUserId(identityUserId)
                            .firstName("User")
                            .lastName(String.valueOf(identityUserId))
                            .email("user" + identityUserId + "@libraquiz.com")
                            .userRole(UserProfile.UserRoleType.STUDENT)
                            .build();
                    StudentProfile studentProfile = StudentProfile.builder()
                            .userProfile(newProfile)
                            .rollNumber("STU-" + identityUserId)
                            .department("Computer Science")
                            .batchYear("2024")
                            .build();
                    newProfile.setStudentProfile(studentProfile);
                    return userProfileRepository.save(newProfile);
                });
        return userProfileMapper.toDto(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(Long id, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id: " + id));

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setAvatarUrl(request.getAvatarUrl());

        UserProfile updated = userProfileRepository.save(profile);
        return userProfileMapper.toDto(updated);
    }

    @Override
    public List<UserProfileResponse> getAllUsers() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserProfile(Long id) {
        if (!userProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserProfile not found with id: " + id);
        }
        userProfileRepository.deleteById(id);
    }
}
