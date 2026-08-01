package com.lq.questionbank.service;

import com.lq.questionbank.dto.QuestionRequest;
import com.lq.questionbank.dto.QuestionResponse;
import com.lq.questionbank.entity.enums.DifficultyLevel;
import com.lq.questionbank.entity.enums.QuestionType;

import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(QuestionRequest request);
    QuestionResponse getQuestionById(Long id);
    List<QuestionResponse> getAllQuestions();
    List<QuestionResponse> filterQuestions(Long categoryId, QuestionType type, DifficultyLevel level);
    QuestionResponse updateQuestion(Long id, QuestionRequest request);
    void deleteQuestion(Long id);
}
