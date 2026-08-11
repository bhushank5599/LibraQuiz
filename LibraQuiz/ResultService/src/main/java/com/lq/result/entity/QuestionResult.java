package com.lq.result.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_results")
public class QuestionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private Result result;

    private Long questionId;
    private String topic;
    private boolean isCorrect;
    private Double marksObtained;
    private Double maxMarks;

    public QuestionResult() {
    }

    public QuestionResult(Long id, Result result, Long questionId, String topic, boolean isCorrect, Double marksObtained, Double maxMarks) {
        this.id = id;
        this.result = result;
        this.questionId = questionId;
        this.topic = topic;
        this.isCorrect = isCorrect;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public static QuestionResultBuilder builder() {
        return new QuestionResultBuilder();
    }

    public static class QuestionResultBuilder {
        private Long id;
        private Result result;
        private Long questionId;
        private String topic;
        private boolean isCorrect;
        private Double marksObtained;
        private Double maxMarks;

        public QuestionResultBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionResultBuilder result(Result result) {
            this.result = result;
            return this;
        }

        public QuestionResultBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionResultBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public QuestionResultBuilder isCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
            return this;
        }

        public QuestionResultBuilder marksObtained(Double marksObtained) {
            this.marksObtained = marksObtained;
            return this;
        }

        public QuestionResultBuilder maxMarks(Double maxMarks) {
            this.maxMarks = maxMarks;
            return this;
        }

        public QuestionResult build() {
            return new QuestionResult(id, result, questionId, topic, isCorrect, marksObtained, maxMarks);
        }
    }
}
