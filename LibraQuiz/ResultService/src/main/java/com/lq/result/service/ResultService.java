package com.lq.result.service;

import com.lq.result.dto.ResultResponse;

import java.util.List;

public interface ResultService {
    ResultResponse generateResult(Long attemptId, Long quizId, Long studentUserId, Double marksObtained, Double totalPossibleMarks);
    ResultResponse getResultById(Long id);
    ResultResponse getResultByAttemptId(Long attemptId);
    List<ResultResponse> getUserResults(Long studentUserId);
    List<ResultResponse> getQuizResults(Long quizId);
}
