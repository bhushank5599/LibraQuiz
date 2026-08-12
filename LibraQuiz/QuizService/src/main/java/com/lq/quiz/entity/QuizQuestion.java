package com.lq.quiz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(nullable = false)
    private Long questionBankId;

    private Long questionId;

    private Integer sequenceOrder;
    private Double marks = 1.0;
    private Double weightageMarks = 1.0;
    private Double negativeMarks = 0.0;

    public QuizQuestion() {
    }

    public QuizQuestion(Long id, Quiz quiz, Long questionBankId, Long questionId, Integer sequenceOrder, Double marks, Double weightageMarks, Double negativeMarks) {
        this.id = id;
        this.quiz = quiz;
        this.questionBankId = questionBankId != null ? questionBankId : questionId;
        this.questionId = questionId != null ? questionId : questionBankId;
        this.sequenceOrder = sequenceOrder;
        this.marks = marks != null ? marks : (weightageMarks != null ? weightageMarks : 1.0);
        this.weightageMarks = weightageMarks != null ? weightageMarks : this.marks;
        if (negativeMarks != null) this.negativeMarks = negativeMarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getQuestionBankId() {
        return questionBankId != null ? questionBankId : questionId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
        this.questionId = questionBankId;
    }

    public Long getQuestionId() {
        return questionId != null ? questionId : questionBankId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
        this.questionBankId = questionId;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public Double getMarks() {
        return marks != null ? marks : weightageMarks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
        this.weightageMarks = marks;
    }

    public Double getWeightageMarks() {
        return weightageMarks != null ? weightageMarks : marks;
    }

    public void setWeightageMarks(Double weightageMarks) {
        this.weightageMarks = weightageMarks;
        this.marks = weightageMarks;
    }

    public Double getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(Double negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public static QuizQuestionBuilder builder() {
        return new QuizQuestionBuilder();
    }

    public static class QuizQuestionBuilder {
        private Long id;
        private Quiz quiz;
        private Long questionBankId;
        private Long questionId;
        private Integer sequenceOrder;
        private Double marks = 1.0;
        private Double weightageMarks = 1.0;
        private Double negativeMarks = 0.0;

        public QuizQuestionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuizQuestionBuilder quiz(Quiz quiz) {
            this.quiz = quiz;
            return this;
        }

        public QuizQuestionBuilder questionBankId(Long questionBankId) {
            this.questionBankId = questionBankId;
            this.questionId = questionBankId;
            return this;
        }

        public QuizQuestionBuilder questionId(Long questionId) {
            this.questionId = questionId;
            this.questionBankId = questionId;
            return this;
        }

        public QuizQuestionBuilder sequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public QuizQuestionBuilder marks(Double marks) {
            this.marks = marks;
            this.weightageMarks = marks;
            return this;
        }

        public QuizQuestionBuilder weightageMarks(Double weightageMarks) {
            this.weightageMarks = weightageMarks;
            this.marks = weightageMarks;
            return this;
        }

        public QuizQuestionBuilder negativeMarks(Double negativeMarks) {
            this.negativeMarks = negativeMarks;
            return this;
        }

        public QuizQuestion build() {
            return new QuizQuestion(id, quiz, questionBankId, questionId, sequenceOrder, marks, weightageMarks, negativeMarks);
        }
    }
}
