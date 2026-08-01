package com.lq.questionbank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_options")
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String optionText;

    private boolean isCorrect = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public QuestionOption() {
    }

    public QuestionOption(Long id, String optionText, boolean isCorrect, Question question) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.question = question;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public static QuestionOptionBuilder builder() {
        return new QuestionOptionBuilder();
    }

    public static class QuestionOptionBuilder {
        private Long id;
        private String optionText;
        private boolean isCorrect = false;
        private Question question;

        public QuestionOptionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionOptionBuilder optionText(String optionText) {
            this.optionText = optionText;
            return this;
        }

        public QuestionOptionBuilder isCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
            return this;
        }

        public QuestionOptionBuilder question(Question question) {
            this.question = question;
            return this;
        }

        public QuestionOption build() {
            return new QuestionOption(id, optionText, isCorrect, question);
        }
    }
}
