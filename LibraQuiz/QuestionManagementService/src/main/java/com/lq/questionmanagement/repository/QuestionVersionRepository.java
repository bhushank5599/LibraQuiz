package com.lq.questionmanagement.repository;

import com.lq.questionmanagement.entity.QuestionVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionVersionRepository extends JpaRepository<QuestionVersion, Long> {
    List<QuestionVersion> findByQuestionManagementIdOrderByVersionNumberDesc(Long questionManagementId);
    List<QuestionVersion> findByQuestionIdOrderByVersionNumberDesc(Long questionId);
}
