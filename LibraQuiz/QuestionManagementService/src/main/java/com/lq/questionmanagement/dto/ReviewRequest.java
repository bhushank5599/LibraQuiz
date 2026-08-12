package com.lq.questionmanagement.dto;

import com.lq.questionmanagement.entity.enums.ReviewDecision;
import jakarta.validation.constraints.NotNull;

public class ReviewRequest {

    private Long questionManagementId;
    private Long questionId;

    @NotNull(message = "Reviewer User ID is required")
    private Long reviewerUserId;

    @NotNull(message = "Decision is required")
    private ReviewDecision decision;

    private String comments;

    public ReviewRequest() {
    }

    public ReviewRequest(Long questionManagementId, Long questionId, Long reviewerUserId, ReviewDecision decision, String comments) {
        this.questionManagementId = questionManagementId != null ? questionManagementId : questionId;
        this.questionId = questionId != null ? questionId : questionManagementId;
        this.reviewerUserId = reviewerUserId;
        this.decision = decision;
        this.comments = comments;
    }

    public Long getQuestionManagementId() {
        return questionManagementId != null ? questionManagementId : questionId;
    }

    public void setQuestionManagementId(Long questionManagementId) {
        this.questionManagementId = questionManagementId;
        this.questionId = questionManagementId;
    }

    public Long getQuestionId() {
        return questionId != null ? questionId : questionManagementId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
        this.questionManagementId = questionId;
    }

    public Long getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId(Long reviewerUserId) {
        this.reviewerUserId = reviewerUserId;
    }

    public ReviewDecision getDecision() {
        return decision;
    }

    public void setDecision(ReviewDecision decision) {
        this.decision = decision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public static ReviewRequestBuilder builder() {
        return new ReviewRequestBuilder();
    }

    public static class ReviewRequestBuilder {
        private Long questionManagementId;
        private Long questionId;
        private Long reviewerUserId;
        private ReviewDecision decision;
        private String comments;

        public ReviewRequestBuilder questionManagementId(Long questionManagementId) {
            this.questionManagementId = questionManagementId;
            this.questionId = questionManagementId;
            return this;
        }

        public ReviewRequestBuilder questionId(Long questionId) {
            this.questionId = questionId;
            this.questionManagementId = questionId;
            return this;
        }

        public ReviewRequestBuilder reviewerUserId(Long reviewerUserId) {
            this.reviewerUserId = reviewerUserId;
            return this;
        }

        public ReviewRequestBuilder decision(ReviewDecision decision) {
            this.decision = decision;
            return this;
        }

        public ReviewRequestBuilder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public ReviewRequest build() {
            return new ReviewRequest(questionManagementId, questionId, reviewerUserId, decision, comments);
        }
    }
}
