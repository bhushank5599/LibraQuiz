package com.lq.quiz.repository;

import com.lq.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByStatus(Quiz.QuizStatus status);
    List<Quiz> findByCourseId(Long courseId);
    List<Quiz> findByCategoryId(Long categoryId);
}
