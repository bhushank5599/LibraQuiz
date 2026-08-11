package com.lq.ai.repository;

import com.lq.ai.entity.AIGeneratedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIQuestionRepository extends JpaRepository<AIGeneratedQuestion, Long> {
    List<AIGeneratedQuestion> findByTeacherUserId(Long teacherUserId);
    List<AIGeneratedQuestion> findByStatus(AIGeneratedQuestion.QuestionDraftStatus status);
}
