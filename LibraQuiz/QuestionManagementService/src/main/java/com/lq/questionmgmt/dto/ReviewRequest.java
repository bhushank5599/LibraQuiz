package com.lq.questionmgmt.dto;

import com.lq.questionmgmt.entity.Review;
import jakarta.validation.constraints.NotNull;

public class ReviewRequest {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Reviewer User ID is required")
    private Long reviewerUserId;

    @NotNull(message = "Decision is required")
    private Review.ReviewDecision decision;

    private String comments;

    public ReviewRequest() {
    }

    public ReviewRequest(Long questionId, Long reviewerUserId, Review.ReviewDecision decision, String comments) {
        this.questionId = questionId;
        this.reviewerUserId = reviewerUserId;
        this.decision = decision;
        this.comments = comments;
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

    public Review.ReviewDecision getDecision() {
        return decision;
    }

    public void setDecision(Review.ReviewDecision decision) {
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
        private Long questionId;
        private Long reviewerUserId;
        private Review.ReviewDecision decision;
        private String comments;

        public ReviewRequestBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public ReviewRequestBuilder reviewerUserId(Long reviewerUserId) {
            this.reviewerUserId = reviewerUserId;
            return this;
        }

        public ReviewRequestBuilder decision(Review.ReviewDecision decision) {
            this.decision = decision;
            return this;
        }

        public ReviewRequestBuilder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public ReviewRequest build() {
            return new ReviewRequest(questionId, reviewerUserId, decision, comments);
        }
    }
}
