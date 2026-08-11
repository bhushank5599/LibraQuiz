package com.lq.ai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_queries")
public class AIQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 2000)
    private String userPrompt;

    @Column(length = 2000)
    private String queryText;

    @Column(length = 4000)
    private String aiResponseText;

    @Column(length = 4000)
    private String structuredCriteriaJson;

    private String queryType;
    private LocalDateTime createdAt;

    public AIQuery() {
    }

    public AIQuery(Long id, Long userId, String userPrompt, String queryText, String aiResponseText, String structuredCriteriaJson, String queryType, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userPrompt = userPrompt != null ? userPrompt : queryText;
        this.queryText = queryText != null ? queryText : userPrompt;
        this.aiResponseText = aiResponseText;
        this.structuredCriteriaJson = structuredCriteriaJson;
        this.queryType = queryType;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPrompt() {
        return userPrompt != null ? userPrompt : queryText;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
        this.queryText = userPrompt;
    }

    public String getQueryText() {
        return queryText != null ? queryText : userPrompt;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
        this.userPrompt = queryText;
    }

    public String getAiResponseText() {
        return aiResponseText;
    }

    public void setAiResponseText(String aiResponseText) {
        this.aiResponseText = aiResponseText;
    }

    public String getStructuredCriteriaJson() {
        return structuredCriteriaJson;
    }

    public void setStructuredCriteriaJson(String structuredCriteriaJson) {
        this.structuredCriteriaJson = structuredCriteriaJson;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static AIQueryBuilder builder() {
        return new AIQueryBuilder();
    }

    public static class AIQueryBuilder {
        private Long id;
        private Long userId;
        private String userPrompt;
        private String queryText;
        private String aiResponseText;
        private String structuredCriteriaJson;
        private String queryType;
        private LocalDateTime createdAt;

        public AIQueryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AIQueryBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public AIQueryBuilder userPrompt(String userPrompt) {
            this.userPrompt = userPrompt;
            this.queryText = userPrompt;
            return this;
        }

        public AIQueryBuilder queryText(String queryText) {
            this.queryText = queryText;
            this.userPrompt = queryText;
            return this;
        }

        public AIQueryBuilder aiResponseText(String aiResponseText) {
            this.aiResponseText = aiResponseText;
            return this;
        }

        public AIQueryBuilder structuredCriteriaJson(String structuredCriteriaJson) {
            this.structuredCriteriaJson = structuredCriteriaJson;
            return this;
        }

        public AIQueryBuilder queryType(String queryType) {
            this.queryType = queryType;
            return this;
        }

        public AIQueryBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AIQuery build() {
            return new AIQuery(id, userId, userPrompt, queryText, aiResponseText, structuredCriteriaJson, queryType, createdAt);
        }
    }
}
