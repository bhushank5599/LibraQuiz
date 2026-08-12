package com.lq.questionmgmt.dto;

import com.lq.questionmgmt.entity.QuestionManagement;

import java.time.LocalDateTime;

public class QuestionManagementResponse {

    private Long id;
    private Long questionId;
    private QuestionManagement.WorkflowStatus status;
    private Integer currentVersion;
    private Long submittedByTeacherId;
    private Long assignedReviewerId;
    private String reviewFeedback;
    private LocalDateTime updatedAt;

    public QuestionManagementResponse() {
    }

    public QuestionManagementResponse(Long id, Long questionId, QuestionManagement.WorkflowStatus status, Integer currentVersion, Long submittedByTeacherId, Long assignedReviewerId, String reviewFeedback, LocalDateTime updatedAt) {
        this.id = id;
        this.questionId = questionId;
        this.status = status;
        this.currentVersion = currentVersion;
        this.submittedByTeacherId = submittedByTeacherId;
        this.assignedReviewerId = assignedReviewerId;
        this.reviewFeedback = reviewFeedback;
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

    public QuestionManagement.WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionManagement.WorkflowStatus status) {
        this.status = status;
    }

    public Integer getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Long getSubmittedByTeacherId() {
        return submittedByTeacherId;
    }

    public void setSubmittedByTeacherId(Long submittedByTeacherId) {
        this.submittedByTeacherId = submittedByTeacherId;
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
        private QuestionManagement.WorkflowStatus status;
        private Integer currentVersion;
        private Long submittedByTeacherId;
        private Long assignedReviewerId;
        private String reviewFeedback;
        private LocalDateTime updatedAt;

        public QuestionManagementResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionManagementResponseBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionManagementResponseBuilder status(QuestionManagement.WorkflowStatus status) {
            this.status = status;
            return this;
        }

        public QuestionManagementResponseBuilder currentVersion(Integer currentVersion) {
            this.currentVersion = currentVersion;
            return this;
        }

        public QuestionManagementResponseBuilder submittedByTeacherId(Long submittedByTeacherId) {
            this.submittedByTeacherId = submittedByTeacherId;
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

        public QuestionManagementResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public QuestionManagementResponse build() {
            return new QuestionManagementResponse(id, questionId, status, currentVersion, submittedByTeacherId, assignedReviewerId, reviewFeedback, updatedAt);
        }
    }
}
