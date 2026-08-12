package com.lq.questionbank.dto;

import com.lq.questionbank.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionResponse {

    private Long id;
    private String questionText;
    private Question.QuestionType questionType;
    private Long categoryId;
    private String topic;
    private String difficultyLevel;
    private Double defaultMarks;
    private Double marks;
    private Double negativeMarks;
    private Long createdByTeacherId;
    private String explanation;
    private Long bookReferenceId;
    private LocalDateTime createdAt;
    private List<OptionResponse> options;

    public QuestionResponse() {
    }

    public QuestionResponse(Long id, String questionText, Question.QuestionType questionType, Long categoryId, String topic, String difficultyLevel, Double defaultMarks, Double marks, Double negativeMarks, Long createdByTeacherId, String explanation, Long bookReferenceId, LocalDateTime createdAt, List<OptionResponse> options) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.categoryId = categoryId;
        this.topic = topic;
        this.difficultyLevel = difficultyLevel;
        this.defaultMarks = defaultMarks;
        this.marks = marks != null ? marks : defaultMarks;
        this.negativeMarks = negativeMarks;
        this.createdByTeacherId = createdByTeacherId;
        this.explanation = explanation;
        this.bookReferenceId = bookReferenceId;
        this.createdAt = createdAt;
        this.options = options;
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

    public Question.QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Question.QuestionType questionType) {
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
        return marks != null ? marks : defaultMarks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
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

    public List<OptionResponse> getOptions() {
        return options;
    }

    public void setOptions(List<OptionResponse> options) {
        this.options = options;
    }

    public static QuestionResponseBuilder builder() {
        return new QuestionResponseBuilder();
    }

    public static class QuestionResponseBuilder {
        private Long id;
        private String questionText;
        private Question.QuestionType questionType;
        private Long categoryId;
        private String topic;
        private String difficultyLevel;
        private Double defaultMarks;
        private Double marks;
        private Double negativeMarks;
        private Long createdByTeacherId;
        private String explanation;
        private Long bookReferenceId;
        private LocalDateTime createdAt;
        private List<OptionResponse> options;

        public QuestionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionResponseBuilder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public QuestionResponseBuilder questionType(Question.QuestionType questionType) {
            this.questionType = questionType;
            return this;
        }

        public QuestionResponseBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuestionResponseBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public QuestionResponseBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            return this;
        }

        public QuestionResponseBuilder defaultMarks(Double defaultMarks) {
            this.defaultMarks = defaultMarks;
            return this;
        }

        public QuestionResponseBuilder marks(Double marks) {
            this.marks = marks;
            return this;
        }

        public QuestionResponseBuilder negativeMarks(Double negativeMarks) {
            this.negativeMarks = negativeMarks;
            return this;
        }

        public QuestionResponseBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            return this;
        }

        public QuestionResponseBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public QuestionResponseBuilder bookReferenceId(Long bookReferenceId) {
            this.bookReferenceId = bookReferenceId;
            return this;
        }

        public QuestionResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuestionResponseBuilder options(List<OptionResponse> options) {
            this.options = options;
            return this;
        }

        public QuestionResponse build() {
            return new QuestionResponse(id, questionText, questionType, categoryId, topic, difficultyLevel, defaultMarks, marks, negativeMarks, createdByTeacherId, explanation, bookReferenceId, createdAt, options);
        }
    }

    public static class OptionResponse {
        private Long id;
        private String optionText;

        @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
        private boolean isCorrect;

        public OptionResponse() {
        }

        public OptionResponse(Long id, String optionText, boolean isCorrect) {
            this.id = id;
            this.optionText = optionText;
            this.isCorrect = isCorrect;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOptionText() {
            return optionText;
        }

        public void setOptionText(String optionText) {
            this.optionText = optionText;
        }

        @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            this.isCorrect = correct;
        }

        public void setIsCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
        }

        public static OptionResponseBuilder builder() {
            return new OptionResponseBuilder();
        }

        public static class OptionResponseBuilder {
            private Long id;
            private String optionText;
            private boolean isCorrect;

            public OptionResponseBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public OptionResponseBuilder optionText(String optionText) {
                this.optionText = optionText;
                return this;
            }

            public OptionResponseBuilder isCorrect(boolean isCorrect) {
                this.isCorrect = isCorrect;
                return this;
            }

            public OptionResponse build() {
                return new OptionResponse(id, optionText, isCorrect);
            }
        }
    }
}
