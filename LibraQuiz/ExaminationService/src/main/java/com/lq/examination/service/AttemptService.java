package com.lq.examination.service;

import com.lq.examination.dto.AttemptResponse;
import com.lq.examination.dto.StartExamRequest;
import com.lq.examination.dto.SubmitAnswerRequest;

import java.util.List;

public interface AttemptService {
    AttemptResponse startExam(StartExamRequest request);
    AttemptResponse submitExam(SubmitAnswerRequest request);
    AttemptResponse getAttemptById(Long attemptId);
    List<AttemptResponse> getUserAttempts(Long studentUserId);
}
