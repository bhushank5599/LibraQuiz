package com.lq.ai.dto;

import com.lq.ai.entity.AIGeneratedQuestion;

import java.time.LocalDateTime;
import java.util.List;

public class AIQuestionResponse {

    private Long id;
    private String topic;
    private String difficultyLevel;
    private String difficulty;
    private String generatedContentJson;
    private String promptText;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private String explanation;
    private String status;
    private AIGeneratedQuestion.QuestionDraftStatus draftStatus;
    private LocalDateTime createdAt;

    public AIQuestionResponse() {
    }

    public AIQuestionResponse(Long id, String topic, String difficultyLevel, String difficulty, String generatedContentJson, String promptText, String questionText, List<String> options, String correctAnswer, String explanation, String status, AIGeneratedQuestion.QuestionDraftStatus draftStatus, LocalDateTime createdAt) {
        this.id = id;
        this.topic = topic;
        this.difficultyLevel = difficultyLevel != null ? difficultyLevel : difficulty;
        this.difficulty = difficulty != null ? difficulty : difficultyLevel;
        this.generatedContentJson = generatedContentJson;
        this.promptText = promptText != null ? promptText : questionText;
        this.questionText = questionText != null ? questionText : promptText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.status = status;
        this.draftStatus = draftStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getGeneratedContentJson() {
        return generatedContentJson;
    }

    public void setGeneratedContentJson(String generatedContentJson) {
        this.generatedContentJson = generatedContentJson;
    }

    public String getPromptText() {
        return promptText != null ? promptText : questionText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
        this.questionText = promptText;
    }

    public String getQuestionText() {
        return questionText != null ? questionText : promptText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
        this.promptText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AIGeneratedQuestion.QuestionDraftStatus getDraftStatus() {
        return draftStatus;
    }

    public void setDraftStatus(AIGeneratedQuestion.QuestionDraftStatus draftStatus) {
        this.draftStatus = draftStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static AIQuestionResponseBuilder builder() {
        return new AIQuestionResponseBuilder();
    }

    public static class AIQuestionResponseBuilder {
        private Long id;
        private String topic;
        private String difficultyLevel;
        private String difficulty;
        private String generatedContentJson;
        private String promptText;
        private String questionText;
        private List<String> options;
        private String correctAnswer;
        private String explanation;
        private String status;
        private AIGeneratedQuestion.QuestionDraftStatus draftStatus;
        private LocalDateTime createdAt;

        public AIQuestionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AIQuestionResponseBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public AIQuestionResponseBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            this.difficulty = difficultyLevel;
            return this;
        }

        public AIQuestionResponseBuilder difficulty(String difficulty) {
            this.difficulty = difficulty;
            this.difficultyLevel = difficulty;
            return this;
        }

        public AIQuestionResponseBuilder generatedContentJson(String generatedContentJson) {
            this.generatedContentJson = generatedContentJson;
            return this;
        }

        public AIQuestionResponseBuilder promptText(String promptText) {
            this.promptText = promptText;
            this.questionText = promptText;
            return this;
        }

        public AIQuestionResponseBuilder questionText(String questionText) {
            this.questionText = questionText;
            this.promptText = questionText;
            return this;
        }

        public AIQuestionResponseBuilder options(List<String> options) {
            this.options = options;
            return this;
        }

        public AIQuestionResponseBuilder correctAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }

        public AIQuestionResponseBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public AIQuestionResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public AIQuestionResponseBuilder status(AIGeneratedQuestion.QuestionDraftStatus draftStatus) {
            this.draftStatus = draftStatus;
            this.status = draftStatus != null ? draftStatus.name() : null;
            return this;
        }

        public AIQuestionResponseBuilder draftStatus(AIGeneratedQuestion.QuestionDraftStatus draftStatus) {
            this.draftStatus = draftStatus;
            this.status = draftStatus != null ? draftStatus.name() : null;
            return this;
        }

        public AIQuestionResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AIQuestionResponse build() {
            return new AIQuestionResponse(id, topic, difficultyLevel, difficulty, generatedContentJson, promptText, questionText, options, correctAnswer, explanation, status, draftStatus, createdAt);
        }
    }
}
