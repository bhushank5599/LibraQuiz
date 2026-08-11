package com.lq.quiz.service;

import com.lq.quiz.dto.QuizRequest;
import com.lq.quiz.dto.QuizResponse;
import com.lq.quiz.entity.Quiz;

import java.util.List;

public interface QuizService {
    QuizResponse createQuiz(QuizRequest request);
    QuizResponse getQuizById(Long id);
    List<QuizResponse> getAllQuizzes();
    List<QuizResponse> getPublishedQuizzes();
    QuizResponse publishQuiz(Long id);
    QuizResponse updateQuiz(Long id, QuizRequest request);
    void deleteQuiz(Long id);
}
