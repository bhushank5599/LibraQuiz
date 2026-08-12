package com.lq.questionmanagement.service;

import com.lq.questionmanagement.dto.QuestionManagementResponse;
import com.lq.questionmanagement.dto.ReviewRequest;
import com.lq.questionmanagement.entity.enums.QuestionStatus;

import java.util.List;

public interface QuestionWorkflowService {
    QuestionManagementResponse submitForReview(Long questionId, Long teacherUserId);
    QuestionManagementResponse reviewQuestion(ReviewRequest request);
    QuestionManagementResponse updateStatus(Long questionManagementId, QuestionStatus status);
    List<QuestionManagementResponse> getPendingReviews();
    List<QuestionManagementResponse> getQuestionsByStatus(QuestionStatus status);
}
