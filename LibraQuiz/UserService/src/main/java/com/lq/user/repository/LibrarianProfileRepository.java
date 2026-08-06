package com.lq.user.repository;

import com.lq.user.entity.LibrarianProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianProfileRepository extends JpaRepository<LibrarianProfile, Long> {
    Optional<LibrarianProfile> findByEmployeeId(String employeeId);
}
