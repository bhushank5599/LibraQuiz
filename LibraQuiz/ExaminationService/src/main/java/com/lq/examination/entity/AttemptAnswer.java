package com.lq.examination.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attempt_answers")
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private Attempt attempt;

    @Column(nullable = false)
    private Long questionId;

    private Long selectedOptionId;
    private String textAnswer;

    private Boolean isCorrect;
    private Double scoreAwarded = 0.0;
    private Double marksObtained = 0.0;

    public AttemptAnswer() {
    }

    public AttemptAnswer(Long id, Attempt attempt, Long questionId, Long selectedOptionId, String textAnswer, Boolean isCorrect, Double scoreAwarded, Double marksObtained) {
        this.id = id;
        this.attempt = attempt;
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
        this.scoreAwarded = scoreAwarded != null ? scoreAwarded : marksObtained;
        this.marksObtained = marksObtained != null ? marksObtained : scoreAwarded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Double getScoreAwarded() {
        return scoreAwarded != null ? scoreAwarded : marksObtained;
    }

    public void setScoreAwarded(Double scoreAwarded) {
        this.scoreAwarded = scoreAwarded;
        this.marksObtained = scoreAwarded;
    }

    public Double getMarksObtained() {
        return marksObtained != null ? marksObtained : scoreAwarded;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
        this.scoreAwarded = marksObtained;
    }

    public static AttemptAnswerBuilder builder() {
        return new AttemptAnswerBuilder();
    }

    public static class AttemptAnswerBuilder {
        private Long id;
        private Attempt attempt;
        private Long questionId;
        private Long selectedOptionId;
        private String textAnswer;
        private Boolean isCorrect;
        private Double scoreAwarded = 0.0;
        private Double marksObtained = 0.0;

        public AttemptAnswerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AttemptAnswerBuilder attempt(Attempt attempt) {
            this.attempt = attempt;
            return this;
        }

        public AttemptAnswerBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public AttemptAnswerBuilder selectedOptionId(Long selectedOptionId) {
            this.selectedOptionId = selectedOptionId;
            return this;
        }

        public AttemptAnswerBuilder textAnswer(String textAnswer) {
            this.textAnswer = textAnswer;
            return this;
        }

        public AttemptAnswerBuilder isCorrect(Boolean isCorrect) {
            this.isCorrect = isCorrect;
            return this;
        }

        public AttemptAnswerBuilder scoreAwarded(Double scoreAwarded) {
            this.scoreAwarded = scoreAwarded;
            this.marksObtained = scoreAwarded;
            return this;
        }

        public AttemptAnswerBuilder marksObtained(Double marksObtained) {
            this.marksObtained = marksObtained;
            this.scoreAwarded = marksObtained;
            return this;
        }

        public AttemptAnswer build() {
            return new AttemptAnswer(id, attempt, questionId, selectedOptionId, textAnswer, isCorrect, scoreAwarded, marksObtained);
        }
    }
}
