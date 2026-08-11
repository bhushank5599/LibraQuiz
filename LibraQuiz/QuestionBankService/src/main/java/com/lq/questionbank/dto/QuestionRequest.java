package com.lq.questionbank.dto;

import com.lq.questionbank.entity.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuestionRequest {

    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotNull(message = "Question type is required")
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
    private List<OptionDto> options;

    public QuestionRequest() {
    }

    public QuestionRequest(String questionText, Question.QuestionType questionType, Long categoryId, String topic, String difficultyLevel, Double defaultMarks, Double marks, Double negativeMarks, Long createdByTeacherId, String explanation, Long bookReferenceId, List<OptionDto> options) {
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
        this.options = options;
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

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public static QuestionRequestBuilder builder() {
        return new QuestionRequestBuilder();
    }

    public static class QuestionRequestBuilder {
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
        private List<OptionDto> options;

        public QuestionRequestBuilder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public QuestionRequestBuilder questionType(Question.QuestionType questionType) {
            this.questionType = questionType;
            return this;
        }

        public QuestionRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuestionRequestBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public QuestionRequestBuilder difficultyLevel(String difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
            return this;
        }

        public QuestionRequestBuilder defaultMarks(Double defaultMarks) {
            this.defaultMarks = defaultMarks;
            return this;
        }

        public QuestionRequestBuilder marks(Double marks) {
            this.marks = marks;
            return this;
        }

        public QuestionRequestBuilder negativeMarks(Double negativeMarks) {
            this.negativeMarks = negativeMarks;
            return this;
        }

        public QuestionRequestBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            return this;
        }

        public QuestionRequestBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public QuestionRequestBuilder bookReferenceId(Long bookReferenceId) {
            this.bookReferenceId = bookReferenceId;
            return this;
        }

        public QuestionRequestBuilder options(List<OptionDto> options) {
            this.options = options;
            return this;
        }

        public QuestionRequest build() {
            return new QuestionRequest(questionText, questionType, categoryId, topic, difficultyLevel, defaultMarks, marks, negativeMarks, createdByTeacherId, explanation, bookReferenceId, options);
        }
    }

    public static class OptionDto {
        private String optionText;

        @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
        private boolean isCorrect;

        public OptionDto() {
        }

        public OptionDto(String optionText, boolean isCorrect) {
            this.optionText = optionText;
            this.isCorrect = isCorrect;
        }

        public String getOptionText() {
            return optionText;
        }

        public void setOptionText(String optionText) {
            this.optionText = optionText;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            this.isCorrect = correct;
        }

        public void setIsCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
        }
    }
}
