package com.lq.questionmanagement.entity;

import com.lq.questionmanagement.entity.enums.ReviewDecision;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_management_id")
    private QuestionManagement questionManagement;

    @Column(name = "question_management_id", insertable = false, updatable = false)
    private Long questionManagementId;

    @Column(nullable = false)
    private Long reviewerUserId;

    @Enumerated(EnumType.STRING)
    private ReviewDecision decision;

    private String comments;
    private LocalDateTime reviewedAt;

    public Review() {
    }

    public Review(Long id, QuestionManagement questionManagement, Long questionManagementId, Long reviewerUserId, ReviewDecision decision, String comments, LocalDateTime reviewedAt) {
        this.id = id;
        this.questionManagement = questionManagement;
        this.questionManagementId = questionManagementId != null ? questionManagementId : (questionManagement != null ? questionManagement.getId() : null);
        this.reviewerUserId = reviewerUserId;
        this.decision = decision;
        this.comments = comments;
        this.reviewedAt = reviewedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.reviewedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionManagement getQuestionManagement() {
        return questionManagement;
    }

    public void setQuestionManagement(QuestionManagement questionManagement) {
        this.questionManagement = questionManagement;
        if (questionManagement != null) {
            this.questionManagementId = questionManagement.getId();
        }
    }

    public Long getQuestionManagementId() {
        return questionManagementId != null ? questionManagementId : (questionManagement != null ? questionManagement.getId() : null);
    }

    public void setQuestionManagementId(Long questionManagementId) {
        this.questionManagementId = questionManagementId;
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

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {
        private Long id;
        private QuestionManagement questionManagement;
        private Long questionManagementId;
        private Long reviewerUserId;
        private ReviewDecision decision;
        private String comments;
        private LocalDateTime reviewedAt;

        public ReviewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewBuilder questionManagement(QuestionManagement questionManagement) {
            this.questionManagement = questionManagement;
            if (questionManagement != null) {
                this.questionManagementId = questionManagement.getId();
            }
            return this;
        }

        public ReviewBuilder questionManagementId(Long questionManagementId) {
            this.questionManagementId = questionManagementId;
            return this;
        }

        public ReviewBuilder reviewerUserId(Long reviewerUserId) {
            this.reviewerUserId = reviewerUserId;
            return this;
        }

        public ReviewBuilder decision(ReviewDecision decision) {
            this.decision = decision;
            return this;
        }

        public ReviewBuilder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public ReviewBuilder reviewedAt(LocalDateTime reviewedAt) {
            this.reviewedAt = reviewedAt;
            return this;
        }

        public Review build() {
            return new Review(id, questionManagement, questionManagementId, reviewerUserId, decision, comments, reviewedAt);
        }
    }
}
