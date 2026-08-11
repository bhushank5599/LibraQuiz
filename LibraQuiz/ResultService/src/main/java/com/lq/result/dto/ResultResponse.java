package com.lq.result.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResultResponse {

    private Long id;
    private Long attemptId;
    private Long studentUserId;
    private Long quizId;
    private Double totalScoreObtained;
    private Double marksObtained;
    private Double maxMarks;
    private Double totalPossibleMarks;
    private Double percentage;
    private boolean isPassed;

    private Integer totalQuestions;
    private Integer correctAnswersCount;
    private Integer incorrectAnswersCount;
    private Integer unansweredCount;

    private String grade;
    private LocalDateTime generatedAt;
    private LocalDateTime calculatedAt;
    private List<TopicPerformanceResponse> topicBreakdown;

    public ResultResponse() {
    }

    public ResultResponse(Long id, Long attemptId, Long studentUserId, Long quizId, Double totalScoreObtained, Double marksObtained, Double maxMarks, Double totalPossibleMarks, Double percentage, boolean isPassed, Integer totalQuestions, Integer correctAnswersCount, Integer incorrectAnswersCount, Integer unansweredCount, String grade, LocalDateTime generatedAt, LocalDateTime calculatedAt, List<TopicPerformanceResponse> topicBreakdown) {
        this.id = id;
        this.attemptId = attemptId;
        this.studentUserId = studentUserId;
        this.quizId = quizId;
        this.totalScoreObtained = totalScoreObtained != null ? totalScoreObtained : marksObtained;
        this.marksObtained = marksObtained != null ? marksObtained : totalScoreObtained;
        this.maxMarks = maxMarks != null ? maxMarks : totalPossibleMarks;
        this.totalPossibleMarks = totalPossibleMarks != null ? totalPossibleMarks : maxMarks;
        this.percentage = percentage;
        this.isPassed = isPassed;
        this.totalQuestions = totalQuestions;
        this.correctAnswersCount = correctAnswersCount;
        this.incorrectAnswersCount = incorrectAnswersCount;
        this.unansweredCount = unansweredCount;
        this.grade = grade;
        this.generatedAt = generatedAt != null ? generatedAt : calculatedAt;
        this.calculatedAt = calculatedAt != null ? calculatedAt : generatedAt;
        this.topicBreakdown = topicBreakdown;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Long studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Double getTotalScoreObtained() {
        return totalScoreObtained != null ? totalScoreObtained : marksObtained;
    }

    public void setTotalScoreObtained(Double totalScoreObtained) {
        this.totalScoreObtained = totalScoreObtained;
        this.marksObtained = totalScoreObtained;
    }

    public Double getMarksObtained() {
        return marksObtained != null ? marksObtained : totalScoreObtained;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
        this.totalScoreObtained = marksObtained;
    }

    public Double getMaxMarks() {
        return maxMarks != null ? maxMarks : totalPossibleMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
        this.totalPossibleMarks = maxMarks;
    }

    public Double getTotalPossibleMarks() {
        return totalPossibleMarks != null ? totalPossibleMarks : maxMarks;
    }

    public void setTotalPossibleMarks(Double totalPossibleMarks) {
        this.totalPossibleMarks = totalPossibleMarks;
        this.maxMarks = totalPossibleMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public boolean getIsPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(Integer correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public Integer getIncorrectAnswersCount() {
        return incorrectAnswersCount;
    }

    public void setIncorrectAnswersCount(Integer incorrectAnswersCount) {
        this.incorrectAnswersCount = incorrectAnswersCount;
    }

    public Integer getUnansweredCount() {
        return unansweredCount;
    }

    public void setUnansweredCount(Integer unansweredCount) {
        this.unansweredCount = unansweredCount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt != null ? generatedAt : calculatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
        this.calculatedAt = generatedAt;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt != null ? calculatedAt : generatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
        this.generatedAt = calculatedAt;
    }

    public List<TopicPerformanceResponse> getTopicBreakdown() {
        return topicBreakdown;
    }

    public void setTopicBreakdown(List<TopicPerformanceResponse> topicBreakdown) {
        this.topicBreakdown = topicBreakdown;
    }

    public static ResultResponseBuilder builder() {
        return new ResultResponseBuilder();
    }

    public static class ResultResponseBuilder {
        private Long id;
        private Long attemptId;
        private Long studentUserId;
        private Long quizId;
        private Double totalScoreObtained;
        private Double marksObtained;
        private Double maxMarks;
        private Double totalPossibleMarks;
        private Double percentage;
        private boolean isPassed;
        private Integer totalQuestions;
        private Integer correctAnswersCount;
        private Integer incorrectAnswersCount;
        private Integer unansweredCount;
        private String grade;
        private LocalDateTime generatedAt;
        private LocalDateTime calculatedAt;
        private List<TopicPerformanceResponse> topicBreakdown;

        public ResultResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResultResponseBuilder attemptId(Long attemptId) {
            this.attemptId = attemptId;
            return this;
        }

        public ResultResponseBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            return this;
        }

        public ResultResponseBuilder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public ResultResponseBuilder totalScoreObtained(Double totalScoreObtained) {
            this.totalScoreObtained = totalScoreObtained;
            this.marksObtained = totalScoreObtained;
            return this;
        }

        public ResultResponseBuilder marksObtained(Double marksObtained) {
            this.marksObtained = marksObtained;
            this.totalScoreObtained = marksObtained;
            return this;
        }

        public ResultResponseBuilder maxMarks(Double maxMarks) {
            this.maxMarks = maxMarks;
            this.totalPossibleMarks = maxMarks;
            return this;
        }

        public ResultResponseBuilder totalPossibleMarks(Double totalPossibleMarks) {
            this.totalPossibleMarks = totalPossibleMarks;
            this.maxMarks = totalPossibleMarks;
            return this;
        }

        public ResultResponseBuilder percentage(Double percentage) {
            this.percentage = percentage;
            return this;
        }

        public ResultResponseBuilder isPassed(boolean isPassed) {
            this.isPassed = isPassed;
            return this;
        }

        public ResultResponseBuilder totalQuestions(Integer totalQuestions) {
            this.totalQuestions = totalQuestions;
            return this;
        }

        public ResultResponseBuilder correctAnswersCount(Integer correctAnswersCount) {
            this.correctAnswersCount = correctAnswersCount;
            return this;
        }

        public ResultResponseBuilder incorrectAnswersCount(Integer incorrectAnswersCount) {
            this.incorrectAnswersCount = incorrectAnswersCount;
            return this;
        }

        public ResultResponseBuilder unansweredCount(Integer unansweredCount) {
            this.unansweredCount = unansweredCount;
            return this;
        }

        public ResultResponseBuilder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public ResultResponseBuilder generatedAt(LocalDateTime generatedAt) {
            this.generatedAt = generatedAt;
            this.calculatedAt = generatedAt;
            return this;
        }

        public ResultResponseBuilder calculatedAt(LocalDateTime calculatedAt) {
            this.calculatedAt = calculatedAt;
            this.generatedAt = calculatedAt;
            return this;
        }

        public ResultResponseBuilder topicBreakdown(List<TopicPerformanceResponse> topicBreakdown) {
            this.topicBreakdown = topicBreakdown;
            return this;
        }

        public ResultResponse build() {
            return new ResultResponse(id, attemptId, studentUserId, quizId, totalScoreObtained, marksObtained, maxMarks, totalPossibleMarks, percentage, isPassed, totalQuestions, correctAnswersCount, incorrectAnswersCount, unansweredCount, grade, generatedAt, calculatedAt, topicBreakdown);
        }
    }
}
