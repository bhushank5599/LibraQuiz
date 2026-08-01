package com.lq.questionbank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType = QuestionType.SINGLE_CHOICE;

    private Long categoryId;
    private String topic;
    private String difficultyLevel = "MEDIUM";
    private Double defaultMarks = 1.0;
    private Double negativeMarks = 0.0;
    private Long createdByTeacherId;
    private String explanation;
    private Long bookReferenceId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options = new ArrayList<>();

    public Question() {
    }

    public Question(Long id, String questionText, QuestionType questionType, Long categoryId, String topic, String difficultyLevel, Double defaultMarks, Double negativeMarks, Long createdByTeacherId, String explanation, Long bookReferenceId, LocalDateTime createdAt, LocalDateTime updatedAt, List<QuestionOption> options) {
        this.id = id;
        this.questionText = questionText;
        if (questionType != null) this.questionType = questionType;
        this.categoryId = categoryId;
        this.topic = topic;
        if (difficultyLevel != null) this.difficultyLevel = difficultyLevel;
        if (defaultMarks != null) this.defaultMarks = defaultMarks;
        if (negativeMarks != null) this.negativeMarks = negativeMarks;
        this.createdByTeacherId = createdByTeacherId;
        this.explanation = explanation;
        this.bookReferenceId = bookReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        if (options != null) {
            this.options = options;
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Double getDefaultMarks() {
        return defaultMarks;
    }

    public void setDefaultMarks(Double defaultMarks) {
        this.defaultMarks = defaultMarks;
    }

    public Double getMarks() {
        return defaultMarks;
    }

    public void setMarks(Double marks) {
        this.defaultMarks = marks;
    }

    public Double getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(Double negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public Long getCreatedByTeacherId() {
        return createdByTeacherId;
    }

    public void setCreatedByTeacherId(Long createdByTeacherId) {
        this.createdByTeacherId = createdByTeacherId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Long getBookReferenceId() {
        return bookReferenceId;
    }

    public void setBookReferenceId(Long bookReferenceId) {
        this.bookReferenceId = bookReferenceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }

    public enum QuestionType {
        SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FILL_IN_BLANK, DESCRIPTIVE, CODING
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {
        private Long id;
        private String questionText;
        private QuestionType questionType = QuestionType.SINGLE_CHOICE;
        private Long categoryId;
        private String topic;
        private String difficultyLevel = "MEDIUM";
        private Double defaultMarks = 1.0;
        private Double negativeMarks = 0.0;
        private Long createdByTeacherId;
        private String explanation;
        private Long bookReferenceId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<QuestionOption> options = new ArrayList<>();

        public QuestionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public QuestionBuilder questionType(QuestionType questionType) {
            this.questionType = questionType;
            return this;
        }

        public QuestionBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuestionBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public QuestionBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            return this;
        }

        public QuestionBuilder defaultMarks(Double defaultMarks) {
            this.defaultMarks = defaultMarks;
            return this;
        }

        public QuestionBuilder marks(Double marks) {
            this.defaultMarks = marks;
            return this;
        }

        public QuestionBuilder negativeMarks(Double negativeMarks) {
            this.negativeMarks = negativeMarks;
            return this;
        }

        public QuestionBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            return this;
        }

        public QuestionBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public QuestionBuilder bookReferenceId(Long bookReferenceId) {
            this.bookReferenceId = bookReferenceId;
            return this;
        }

        public QuestionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuestionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public QuestionBuilder options(List<QuestionOption> options) {
            this.options = options;
            return this;
        }

        public Question build() {
            return new Question(id, questionText, questionType, categoryId, topic, difficultyLevel, defaultMarks, negativeMarks, createdByTeacherId, explanation, bookReferenceId, createdAt, updatedAt, options);
        }
    }
}
