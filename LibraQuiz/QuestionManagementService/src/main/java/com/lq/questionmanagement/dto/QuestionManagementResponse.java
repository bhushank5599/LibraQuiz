package com.lq.questionmanagement.dto;

import com.lq.questionmanagement.entity.enums.QuestionStatus;

import java.time.LocalDateTime;

public class QuestionManagementResponse {

    private Long id;
    private Long questionId;
    private QuestionStatus status;
    private Integer currentVersion;
    private Long authorTeacherId;
    private Long submittedByTeacherId;
    private Long assignedReviewerId;
    private String reviewFeedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestionManagementResponse() {
    }

    public QuestionManagementResponse(Long id, Long questionId, QuestionStatus status, Integer currentVersion, Long authorTeacherId, Long submittedByTeacherId, Long assignedReviewerId, String reviewFeedback, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.questionId = questionId;
        this.status = status;
        this.currentVersion = currentVersion;
        this.authorTeacherId = authorTeacherId != null ? authorTeacherId : submittedByTeacherId;
        this.submittedByTeacherId = submittedByTeacherId != null ? submittedByTeacherId : authorTeacherId;
        this.assignedReviewerId = assignedReviewerId;
        this.reviewFeedback = reviewFeedback;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public Integer getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Long getAuthorTeacherId() {
        return authorTeacherId != null ? authorTeacherId : submittedByTeacherId;
    }

    public void setAuthorTeacherId(Long authorTeacherId) {
        this.authorTeacherId = authorTeacherId;
        this.submittedByTeacherId = authorTeacherId;
    }

    public Long getSubmittedByTeacherId() {
        return submittedByTeacherId != null ? submittedByTeacherId : authorTeacherId;
    }

    public void setSubmittedByTeacherId(Long submittedByTeacherId) {
        this.submittedByTeacherId = submittedByTeacherId;
        this.authorTeacherId = submittedByTeacherId;
    }

    public Long getAssignedReviewerId() {
        return assignedReviewerId;
    }

    public void setAssignedReviewerId(Long assignedReviewerId) {
        this.assignedReviewerId = assignedReviewerId;
    }

    public String getReviewFeedback() {
        return reviewFeedback;
    }

    public void setReviewFeedback(String reviewFeedback) {
        this.reviewFeedback = reviewFeedback;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static QuestionManagementResponseBuilder builder() {
        return new QuestionManagementResponseBuilder();
    }

    public static class QuestionManagementResponseBuilder {
        private Long id;
        private Long questionId;
        private QuestionStatus status;
        private Integer currentVersion;
        private Long authorTeacherId;
        private Long submittedByTeacherId;
        private Long assignedReviewerId;
        private String reviewFeedback;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public QuestionManagementResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionManagementResponseBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionManagementResponseBuilder status(QuestionStatus status) {
            this.status = status;
            return this;
        }

        public QuestionManagementResponseBuilder currentVersion(Integer currentVersion) {
            this.currentVersion = currentVersion;
            return this;
        }

        public QuestionManagementResponseBuilder authorTeacherId(Long authorTeacherId) {
            this.authorTeacherId = authorTeacherId;
            this.submittedByTeacherId = authorTeacherId;
            return this;
        }

        public QuestionManagementResponseBuilder submittedByTeacherId(Long submittedByTeacherId) {
            this.submittedByTeacherId = submittedByTeacherId;
            this.authorTeacherId = submittedByTeacherId;
            return this;
        }

        public QuestionManagementResponseBuilder assignedReviewerId(Long assignedReviewerId) {
            this.assignedReviewerId = assignedReviewerId;
            return this;
        }

        public QuestionManagementResponseBuilder reviewFeedback(String reviewFeedback) {
            this.reviewFeedback = reviewFeedback;
            return this;
        }

        public QuestionManagementResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuestionManagementResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public QuestionManagementResponse build() {
            return new QuestionManagementResponse(id, questionId, status, currentVersion, authorTeacherId, submittedByTeacherId, assignedReviewerId, reviewFeedback, createdAt, updatedAt);
        }
    }
}
