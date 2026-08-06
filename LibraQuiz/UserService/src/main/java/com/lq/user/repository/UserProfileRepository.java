package com.lq.user.repository;

import com.lq.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByIdentityUserId(Long identityUserId);
    Optional<UserProfile> findByEmail(String email);
}
