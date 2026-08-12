package com.lq.questionmanagement.repository;

import com.lq.questionmanagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByQuestionManagementId(Long questionManagementId);
}
