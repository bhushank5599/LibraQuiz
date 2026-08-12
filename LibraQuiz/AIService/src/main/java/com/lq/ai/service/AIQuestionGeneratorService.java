package com.lq.ai.service;

import com.lq.ai.dto.AIQuestionRequest;
import com.lq.ai.dto.AIQuestionResponse;

import java.util.List;

public interface AIQuestionGeneratorService {
    List<AIQuestionResponse> generateQuestions(AIQuestionRequest request);
    List<AIQuestionResponse> getDraftQuestions(Long teacherUserId);
}
