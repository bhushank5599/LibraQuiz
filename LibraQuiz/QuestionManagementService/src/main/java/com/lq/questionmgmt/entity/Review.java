package com.lq.questionmgmt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Long reviewerUserId;

    @Enumerated(EnumType.STRING)
    private ReviewDecision decision;

    private String comments;
    private LocalDateTime reviewedAt;

    public Review() {
    }

    public Review(Long id, Long questionId, Long reviewerUserId, ReviewDecision decision, String comments, LocalDateTime reviewedAt) {
        this.id = id;
        this.questionId = questionId;
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public enum ReviewDecision {
        APPROVE, REJECT, REQUEST_CHANGES
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {
        private Long id;
        private Long questionId;
        private Long reviewerUserId;
        private ReviewDecision decision;
        private String comments;
        private LocalDateTime reviewedAt;

        public ReviewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewBuilder questionId(Long questionId) {
            this.questionId = questionId;
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
            return new Review(id, questionId, reviewerUserId, decision, comments, reviewedAt);
        }
    }
}
