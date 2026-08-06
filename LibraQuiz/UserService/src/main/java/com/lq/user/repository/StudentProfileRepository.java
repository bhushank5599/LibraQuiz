package com.lq.user.repository;

import com.lq.user.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByRollNumber(String rollNumber);
    Optional<StudentProfile> findByUserProfileId(Long userProfileId);
}
