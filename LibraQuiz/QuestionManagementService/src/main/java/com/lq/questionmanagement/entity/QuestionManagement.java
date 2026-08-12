package com.lq.questionmanagement.entity;

import com.lq.questionmanagement.entity.enums.QuestionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_management")
public class QuestionManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long questionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionStatus status = QuestionStatus.DRAFT;

    private Integer currentVersion = 1;
    private Long authorTeacherId;
    private Long submittedByTeacherId;
    private Long assignedReviewerId;
    private String reviewFeedback;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestionManagement() {
    }

    public QuestionManagement(Long id, Long questionId, QuestionStatus status, Integer currentVersion, Long authorTeacherId, Long submittedByTeacherId, Long assignedReviewerId, String reviewFeedback, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.questionId = questionId;
        if (status != null) this.status = status;
        if (currentVersion != null) this.currentVersion = currentVersion;
        this.authorTeacherId = authorTeacherId != null ? authorTeacherId : submittedByTeacherId;
        this.submittedByTeacherId = submittedByTeacherId != null ? submittedByTeacherId : authorTeacherId;
        this.assignedReviewerId = assignedReviewerId;
        this.reviewFeedback = reviewFeedback;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

    public static QuestionManagementBuilder builder() {
        return new QuestionManagementBuilder();
    }

    public static class QuestionManagementBuilder {
        private Long id;
        private Long questionId;
        private QuestionStatus status = QuestionStatus.DRAFT;
        private Integer currentVersion = 1;
        private Long authorTeacherId;
        private Long submittedByTeacherId;
        private Long assignedReviewerId;
        private String reviewFeedback;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public QuestionManagementBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionManagementBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionManagementBuilder status(QuestionStatus status) {
            this.status = status;
            return this;
        }

        public QuestionManagementBuilder currentVersion(Integer currentVersion) {
            this.currentVersion = currentVersion;
            return this;
        }

        public QuestionManagementBuilder authorTeacherId(Long authorTeacherId) {
            this.authorTeacherId = authorTeacherId;
            this.submittedByTeacherId = authorTeacherId;
            return this;
        }

        public QuestionManagementBuilder submittedByTeacherId(Long submittedByTeacherId) {
            this.submittedByTeacherId = submittedByTeacherId;
            this.authorTeacherId = submittedByTeacherId;
            return this;
        }

        public QuestionManagementBuilder assignedReviewerId(Long assignedReviewerId) {
            this.assignedReviewerId = assignedReviewerId;
            return this;
        }

        public QuestionManagementBuilder reviewFeedback(String reviewFeedback) {
            this.reviewFeedback = reviewFeedback;
            return this;
        }

        public QuestionManagementBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuestionManagementBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public QuestionManagement build() {
            return new QuestionManagement(id, questionId, status, currentVersion, authorTeacherId, submittedByTeacherId, assignedReviewerId, reviewFeedback, createdAt, updatedAt);
        }
    }
}
