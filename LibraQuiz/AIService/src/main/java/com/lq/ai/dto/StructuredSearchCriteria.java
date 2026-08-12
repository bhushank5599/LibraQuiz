package com.lq.ai.dto;

import java.util.List;

public class StructuredSearchCriteria {

    private String originalPrompt;
    private String rawQuery;
    private String intent;
    private String topic;
    private String category;
    private String subcategory;
    private String difficultyLevel;
    private String difficulty;
    private List<String> keywords;

    public StructuredSearchCriteria() {
    }

    public StructuredSearchCriteria(String originalPrompt, String rawQuery, String intent, String topic, String category, String subcategory, String difficultyLevel, String difficulty, List<String> keywords) {
        this.originalPrompt = originalPrompt != null ? originalPrompt : rawQuery;
        this.rawQuery = rawQuery != null ? rawQuery : originalPrompt;
        this.intent = intent;
        this.topic = topic;
        this.category = category;
        this.subcategory = subcategory;
        this.difficultyLevel = difficultyLevel != null ? difficultyLevel : difficulty;
        this.difficulty = difficulty != null ? difficulty : difficultyLevel;
        this.keywords = keywords;
    }

    public String getOriginalPrompt() {
        return originalPrompt != null ? originalPrompt : rawQuery;
    }

    public void setOriginalPrompt(String originalPrompt) {
        this.originalPrompt = originalPrompt;
        this.rawQuery = originalPrompt;
    }

    public String getRawQuery() {
        return rawQuery != null ? rawQuery : originalPrompt;
    }

    public void setRawQuery(String rawQuery) {
        this.rawQuery = rawQuery;
        this.originalPrompt = rawQuery;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDifficultyLevel() {
        return difficultyLevel != null ? difficultyLevel : difficulty;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        this.difficulty = difficultyLevel;
    }

    public String getDifficulty() {
        return difficulty != null ? difficulty : difficultyLevel;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        this.difficultyLevel = difficulty;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public static StructuredSearchCriteriaBuilder builder() {
        return new StructuredSearchCriteriaBuilder();
    }

    public static class StructuredSearchCriteriaBuilder {
        private String originalPrompt;
        private String rawQuery;
        private String intent;
        private String topic;
        private String category;
        private String subcategory;
        private String difficultyLevel;
        private String difficulty;
        private List<String> keywords;

        public StructuredSearchCriteriaBuilder originalPrompt(String originalPrompt) {
            this.originalPrompt = originalPrompt;
            this.rawQuery = originalPrompt;
            return this;
        }

        public StructuredSearchCriteriaBuilder rawQuery(String rawQuery) {
            this.rawQuery = rawQuery;
            this.originalPrompt = rawQuery;
            return this;
        }

        public StructuredSearchCriteriaBuilder intent(String intent) {
            this.intent = intent;
            return this;
        }

        public StructuredSearchCriteriaBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public StructuredSearchCriteriaBuilder category(String category) {
            this.category = category;
            return this;
        }

        public StructuredSearchCriteriaBuilder subcategory(String subcategory) {
            this.subcategory = subcategory;
            return this;
        }

        public StructuredSearchCriteriaBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            this.difficulty = difficultyLevel;
            return this;
        }

        public StructuredSearchCriteriaBuilder difficulty(String difficulty) {
            this.difficulty = difficulty;
            this.difficultyLevel = difficulty;
            return this;
        }

        public StructuredSearchCriteriaBuilder keywords(List<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public StructuredSearchCriteria build() {
            return new StructuredSearchCriteria(originalPrompt, rawQuery, intent, topic, category, subcategory, difficultyLevel, difficulty, keywords);
        }
    }
}
