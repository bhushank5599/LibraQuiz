package com.lq.ai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_generated_questions")
public class AIGeneratedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherUserId;

    private String topic;
    private String difficultyLevel;
    private String difficulty;

    @Column(length = 4000)
    private String promptText;

    @Column(length = 4000)
    private String questionText;

    @Column(length = 4000)
    private String generatedContentJson;

    @Column(length = 4000)
    private String optionsJson;

    @Column(length = 2000)
    private String explanation;

    @Enumerated(EnumType.STRING)
    private QuestionDraftStatus status = QuestionDraftStatus.GENERATED;

    private LocalDateTime createdAt;

    public AIGeneratedQuestion() {
    }

    public AIGeneratedQuestion(Long id, Long teacherUserId, String topic, String difficultyLevel, String difficulty, String promptText, String questionText, String generatedContentJson, String optionsJson, String explanation, QuestionDraftStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.teacherUserId = teacherUserId;
        this.topic = topic;
        this.difficultyLevel = difficultyLevel != null ? difficultyLevel : difficulty;
        this.difficulty = difficulty != null ? difficulty : difficultyLevel;
        this.promptText = promptText != null ? promptText : questionText;
        this.questionText = questionText != null ? questionText : promptText;
        this.generatedContentJson = generatedContentJson != null ? generatedContentJson : optionsJson;
        this.optionsJson = optionsJson != null ? optionsJson : generatedContentJson;
        this.explanation = explanation;
        if (status != null) this.status = status;
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

    public Long getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(Long teacherUserId) {
        this.teacherUserId = teacherUserId;
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

    public String getGeneratedContentJson() {
        return generatedContentJson != null ? generatedContentJson : optionsJson;
    }

    public void setGeneratedContentJson(String generatedContentJson) {
        this.generatedContentJson = generatedContentJson;
        this.optionsJson = generatedContentJson;
    }

    public String getOptionsJson() {
        return optionsJson != null ? optionsJson : generatedContentJson;
    }

    public void setOptionsJson(String optionsJson) {
        this.optionsJson = optionsJson;
        this.generatedContentJson = optionsJson;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public QuestionDraftStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionDraftStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum QuestionDraftStatus {
        DRAFT, GENERATED, SAVED, DISCARDED
    }

    public static AIGeneratedQuestionBuilder builder() {
        return new AIGeneratedQuestionBuilder();
    }

    public static class AIGeneratedQuestionBuilder {
        private Long id;
        private Long teacherUserId;
        private String topic;
        private String difficultyLevel;
        private String difficulty;
        private String promptText;
        private String questionText;
        private String generatedContentJson;
        private String optionsJson;
        private String explanation;
        private QuestionDraftStatus status = QuestionDraftStatus.GENERATED;
        private LocalDateTime createdAt;

        public AIGeneratedQuestionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AIGeneratedQuestionBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            return this;
        }

        public AIGeneratedQuestionBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public AIGeneratedQuestionBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            this.difficulty = difficultyLevel;
            return this;
        }

        public AIGeneratedQuestionBuilder difficulty(String difficulty) {
            this.difficulty = difficulty;
            this.difficultyLevel = difficulty;
            return this;
        }

        public AIGeneratedQuestionBuilder promptText(String promptText) {
            this.promptText = promptText;
            this.questionText = promptText;
            return this;
        }

        public AIGeneratedQuestionBuilder questionText(String questionText) {
            this.questionText = questionText;
            this.promptText = questionText;
            return this;
        }

        public AIGeneratedQuestionBuilder generatedContentJson(String generatedContentJson) {
            this.generatedContentJson = generatedContentJson;
            this.optionsJson = generatedContentJson;
            return this;
        }

        public AIGeneratedQuestionBuilder optionsJson(String optionsJson) {
            this.optionsJson = optionsJson;
            this.generatedContentJson = optionsJson;
            return this;
        }

        public AIGeneratedQuestionBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public AIGeneratedQuestionBuilder status(QuestionDraftStatus status) {
            this.status = status;
            return this;
        }

        public AIGeneratedQuestionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AIGeneratedQuestion build() {
            return new AIGeneratedQuestion(id, teacherUserId, topic, difficultyLevel, difficulty, promptText, questionText, generatedContentJson, optionsJson, explanation, status, createdAt);
        }
    }
}
