package com.lq.quiz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_settings")
public class QuizSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private boolean shuffleQuestions = true;
    private boolean shuffleOptions = true;
    private boolean showResultsImmediately = true;
    private Integer maxAttempts = 3;
    private boolean allowBacktracking = true;

    public QuizSetting() {
    }

    public QuizSetting(Long id, Quiz quiz, boolean shuffleQuestions, boolean shuffleOptions, boolean showResultsImmediately, Integer maxAttempts, boolean allowBacktracking) {
        this.id = id;
        this.quiz = quiz;
        this.shuffleQuestions = shuffleQuestions;
        this.shuffleOptions = shuffleOptions;
        this.showResultsImmediately = showResultsImmediately;
        if (maxAttempts != null) this.maxAttempts = maxAttempts;
        this.allowBacktracking = allowBacktracking;
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

    public boolean isShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public boolean isShuffleOptions() {
        return shuffleOptions;
    }

    public void setShuffleOptions(boolean shuffleOptions) {
        this.shuffleOptions = shuffleOptions;
    }

    public boolean isShowResultsImmediately() {
        return showResultsImmediately;
    }

    public void setShowResultsImmediately(boolean showResultsImmediately) {
        this.showResultsImmediately = showResultsImmediately;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public boolean isAllowBacktracking() {
        return allowBacktracking;
    }

    public void setAllowBacktracking(boolean allowBacktracking) {
        this.allowBacktracking = allowBacktracking;
    }

    public static QuizSettingBuilder builder() {
        return new QuizSettingBuilder();
    }

    public static class QuizSettingBuilder {
        private Long id;
        private Quiz quiz;
        private boolean shuffleQuestions = true;
        private boolean shuffleOptions = true;
        private boolean showResultsImmediately = true;
        private Integer maxAttempts = 3;
        private boolean allowBacktracking = true;

        public QuizSettingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuizSettingBuilder quiz(Quiz quiz) {
            this.quiz = quiz;
            return this;
        }

        public QuizSettingBuilder shuffleQuestions(boolean shuffleQuestions) {
            this.shuffleQuestions = shuffleQuestions;
            return this;
        }

        public QuizSettingBuilder shuffleOptions(boolean shuffleOptions) {
            this.shuffleOptions = shuffleOptions;
            return this;
        }

        public QuizSettingBuilder showResultsImmediately(boolean showResultsImmediately) {
            this.showResultsImmediately = showResultsImmediately;
            return this;
        }

        public QuizSettingBuilder maxAttempts(Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public QuizSettingBuilder allowBacktracking(boolean allowBacktracking) {
            this.allowBacktracking = allowBacktracking;
            return this;
        }

        public QuizSetting build() {
            return new QuizSetting(id, quiz, shuffleQuestions, shuffleOptions, showResultsImmediately, maxAttempts, allowBacktracking);
        }
    }
}
