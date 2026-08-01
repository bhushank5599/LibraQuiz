package com.lq.questionbank.repository;

import com.lq.questionbank.entity.Question;
import com.lq.questionbank.entity.enums.DifficultyLevel;
import com.lq.questionbank.entity.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryId(Long categoryId);
    List<Question> findByQuestionType(QuestionType questionType);
    List<Question> findByDifficultyLevel(DifficultyLevel difficultyLevel);
    List<Question> findByTopicContainingIgnoreCase(String topic);
}
