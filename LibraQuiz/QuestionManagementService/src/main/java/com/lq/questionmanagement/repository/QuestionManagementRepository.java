package com.lq.questionmanagement.repository;

import com.lq.questionmanagement.entity.QuestionManagement;
import com.lq.questionmanagement.entity.enums.QuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionManagementRepository extends JpaRepository<QuestionManagement, Long> {
    Optional<QuestionManagement> findByQuestionId(Long questionId);
    List<QuestionManagement> findByStatus(QuestionStatus status);
    List<QuestionManagement> findByAuthorTeacherId(Long authorTeacherId);
}
