package com.lq.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class AIQuestionRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    private String difficulty = "MEDIUM";
    private Integer count = 3;
    private Long teacherUserId;

    public AIQuestionRequest() {
    }

    public AIQuestionRequest(String topic, String difficulty, Integer count, Long teacherUserId) {
        this.topic = topic;
        if (difficulty != null) this.difficulty = difficulty;
        if (count != null) this.count = count;
        this.teacherUserId = teacherUserId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(Long teacherUserId) {
        this.teacherUserId = teacherUserId;
    }

    public static AIQuestionRequestBuilder builder() {
        return new AIQuestionRequestBuilder();
    }

    public static class AIQuestionRequestBuilder {
        private String topic;
        private String difficulty = "MEDIUM";
        private Integer count = 3;
        private Long teacherUserId;

        public AIQuestionRequestBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public AIQuestionRequestBuilder difficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public AIQuestionRequestBuilder count(Integer count) {
            this.count = count;
            return this;
        }

        public AIQuestionRequestBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            return this;
        }

        public AIQuestionRequest build() {
            return new AIQuestionRequest(topic, difficulty, count, teacherUserId);
        }
    }
}
