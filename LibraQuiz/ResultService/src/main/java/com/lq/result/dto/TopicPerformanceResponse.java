package com.lq.result.dto;

public class TopicPerformanceResponse {

    private String topic;
    private Long totalQuestions;
    private Long correctQuestions;
    private Double accuracyPercentage;

    public TopicPerformanceResponse() {
    }

    public TopicPerformanceResponse(String topic, Long totalQuestions, Long correctQuestions, Double accuracyPercentage) {
        this.topic = topic;
        this.totalQuestions = totalQuestions;
        this.correctQuestions = correctQuestions;
        this.accuracyPercentage = accuracyPercentage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Long getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(Long correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public Double getAccuracyPercentage() {
        return accuracyPercentage;
    }

    public void setAccuracyPercentage(Double accuracyPercentage) {
        this.accuracyPercentage = accuracyPercentage;
    }

    public static TopicPerformanceResponseBuilder builder() {
        return new TopicPerformanceResponseBuilder();
    }

    public static class TopicPerformanceResponseBuilder {
        private String topic;
        private Long totalQuestions;
        private Long correctQuestions;
        private Double accuracyPercentage;

        public TopicPerformanceResponseBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public TopicPerformanceResponseBuilder totalQuestions(Long totalQuestions) {
            this.totalQuestions = totalQuestions;
            return this;
        }

        public TopicPerformanceResponseBuilder totalQuestions(Integer totalQuestions) {
            this.totalQuestions = totalQuestions != null ? totalQuestions.longValue() : null;
            return this;
        }

        public TopicPerformanceResponseBuilder totalQuestions(int totalQuestions) {
            this.totalQuestions = (long) totalQuestions;
            return this;
        }

        public TopicPerformanceResponseBuilder correctQuestions(Long correctQuestions) {
            this.correctQuestions = correctQuestions;
            return this;
        }

        public TopicPerformanceResponseBuilder correctQuestions(Integer correctQuestions) {
            this.correctQuestions = correctQuestions != null ? correctQuestions.longValue() : null;
            return this;
        }

        public TopicPerformanceResponseBuilder correctQuestions(int correctQuestions) {
            this.correctQuestions = (long) correctQuestions;
            return this;
        }

        public TopicPerformanceResponseBuilder accuracyPercentage(Double accuracyPercentage) {
            this.accuracyPercentage = accuracyPercentage;
            return this;
        }

        public TopicPerformanceResponse build() {
            return new TopicPerformanceResponse(topic, totalQuestions, correctQuestions, accuracyPercentage);
        }
    }
}
