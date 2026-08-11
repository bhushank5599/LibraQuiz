package com.lq.ai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_recommendations")
public class AIRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentUserId;

    private Long userId;

    private String recommendationType;

    private Long itemReferenceId;
    private Long itemId;
    private String itemTitle;

    @Column(length = 2000)
    private String recommendationReason;

    @Column(length = 2000)
    private String reason;

    private Double score = 0.0;
    private LocalDateTime createdAt;

    public AIRecommendation() {
    }

    public AIRecommendation(Long id, Long studentUserId, Long userId, String recommendationType, Long itemReferenceId, Long itemId, String itemTitle, String recommendationReason, String reason, Double score, LocalDateTime createdAt) {
        this.id = id;
        this.studentUserId = studentUserId != null ? studentUserId : userId;
        this.userId = userId != null ? userId : studentUserId;
        this.recommendationType = recommendationType;
        this.itemReferenceId = itemReferenceId != null ? itemReferenceId : itemId;
        this.itemId = itemId != null ? itemId : itemReferenceId;
        this.itemTitle = itemTitle;
        this.recommendationReason = recommendationReason != null ? recommendationReason : reason;
        this.reason = reason != null ? reason : recommendationReason;
        this.score = score;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentUserId() {
        return studentUserId != null ? studentUserId : userId;
    }

    public void setStudentUserId(Long studentUserId) {
        this.studentUserId = studentUserId;
        this.userId = studentUserId;
    }

    public Long getUserId() {
        return userId != null ? userId : studentUserId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.studentUserId = userId;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public Long getItemReferenceId() {
        return itemReferenceId != null ? itemReferenceId : itemId;
    }

    public void setItemReferenceId(Long itemReferenceId) {
        this.itemReferenceId = itemReferenceId;
        this.itemId = itemReferenceId;
    }

    public Long getItemId() {
        return itemId != null ? itemId : itemReferenceId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
        this.itemReferenceId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getRecommendationReason() {
        return recommendationReason != null ? recommendationReason : reason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
        this.reason = recommendationReason;
    }

    public String getReason() {
        return reason != null ? reason : recommendationReason;
    }

    public void setReason(String reason) {
        this.reason = reason;
        this.recommendationReason = reason;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static AIRecommendationBuilder builder() {
        return new AIRecommendationBuilder();
    }

    public static class AIRecommendationBuilder {
        private Long id;
        private Long studentUserId;
        private Long userId;
        private String recommendationType;
        private Long itemReferenceId;
        private Long itemId;
        private String itemTitle;
        private String recommendationReason;
        private String reason;
        private Double score = 0.0;
        private LocalDateTime createdAt;

        public AIRecommendationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AIRecommendationBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            this.userId = studentUserId;
            return this;
        }

        public AIRecommendationBuilder userId(Long userId) {
            this.userId = userId;
            this.studentUserId = userId;
            return this;
        }

        public AIRecommendationBuilder recommendationType(String recommendationType) {
            this.recommendationType = recommendationType;
            return this;
        }

        public AIRecommendationBuilder itemReferenceId(Long itemReferenceId) {
            this.itemReferenceId = itemReferenceId;
            this.itemId = itemReferenceId;
            return this;
        }

        public AIRecommendationBuilder itemId(Long itemId) {
            this.itemId = itemId;
            this.itemReferenceId = itemId;
            return this;
        }

        public AIRecommendationBuilder itemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
            return this;
        }

        public AIRecommendationBuilder recommendationReason(String recommendationReason) {
            this.recommendationReason = recommendationReason;
            this.reason = recommendationReason;
            return this;
        }

        public AIRecommendationBuilder reason(String reason) {
            this.reason = reason;
            this.recommendationReason = reason;
            return this;
        }

        public AIRecommendationBuilder score(Double score) {
            this.score = score;
            return this;
        }

        public AIRecommendationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AIRecommendation build() {
            return new AIRecommendation(id, studentUserId, userId, recommendationType, itemReferenceId, itemId, itemTitle, recommendationReason, reason, score, createdAt);
        }
    }
}
