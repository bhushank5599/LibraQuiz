package com.lq.result.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long attemptId;

    @Column(nullable = false)
    private Long studentUserId;

    @Column(nullable = false)
    private Long quizId;

    private Double totalScoreObtained = 0.0;
    private Double marksObtained = 0.0;
    private Double maxMarks = 0.0;
    private Double totalPossibleMarks = 0.0;
    private Double percentage = 0.0;
    private boolean isPassed = false;
    private String grade;

    private Integer totalQuestions = 0;
    private Integer correctAnswersCount = 0;
    private Integer incorrectAnswersCount = 0;
    private Integer unansweredCount = 0;

    private LocalDateTime generatedAt;
    private LocalDateTime calculatedAt;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionResult> questionBreakdown = new ArrayList<>();

    public Result() {
    }

    public Result(Long id, Long attemptId, Long studentUserId, Long quizId, Double totalScoreObtained, Double marksObtained, Double maxMarks, Double totalPossibleMarks, Double percentage, boolean isPassed, String grade, Integer totalQuestions, Integer correctAnswersCount, Integer incorrectAnswersCount, Integer unansweredCount, LocalDateTime generatedAt, LocalDateTime calculatedAt, List<QuestionResult> questionBreakdown) {
        this.id = id;
        this.attemptId = attemptId;
        this.studentUserId = studentUserId;
        this.quizId = quizId;
        this.totalScoreObtained = totalScoreObtained != null ? totalScoreObtained : marksObtained;
        this.marksObtained = marksObtained != null ? marksObtained : totalScoreObtained;
        this.maxMarks = maxMarks != null ? maxMarks : totalPossibleMarks;
        this.totalPossibleMarks = totalPossibleMarks != null ? totalPossibleMarks : maxMarks;
        if (percentage != null) this.percentage = percentage;
        this.isPassed = isPassed;
        this.grade = grade;
        if (totalQuestions != null) this.totalQuestions = totalQuestions;
        if (correctAnswersCount != null) this.correctAnswersCount = correctAnswersCount;
        if (incorrectAnswersCount != null) this.incorrectAnswersCount = incorrectAnswersCount;
        if (unansweredCount != null) this.unansweredCount = unansweredCount;
        this.generatedAt = generatedAt != null ? generatedAt : calculatedAt;
        this.calculatedAt = calculatedAt != null ? calculatedAt : generatedAt;
        if (questionBreakdown != null) {
            this.questionBreakdown = questionBreakdown;
        }
    }

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
        this.calculatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Long studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Double getTotalScoreObtained() {
        return totalScoreObtained != null ? totalScoreObtained : marksObtained;
    }

    public void setTotalScoreObtained(Double totalScoreObtained) {
        this.totalScoreObtained = totalScoreObtained;
        this.marksObtained = totalScoreObtained;
    }

    public Double getMarksObtained() {
        return marksObtained != null ? marksObtained : totalScoreObtained;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
        this.totalScoreObtained = marksObtained;
    }

    public Double getMaxMarks() {
        return maxMarks != null ? maxMarks : totalPossibleMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
        this.totalPossibleMarks = maxMarks;
    }

    public Double getTotalPossibleMarks() {
        return totalPossibleMarks != null ? totalPossibleMarks : maxMarks;
    }

    public void setTotalPossibleMarks(Double totalPossibleMarks) {
        this.totalPossibleMarks = totalPossibleMarks;
        this.maxMarks = totalPossibleMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public boolean getIsPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(Integer correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public Integer getIncorrectAnswersCount() {
        return incorrectAnswersCount;
    }

    public void setIncorrectAnswersCount(Integer incorrectAnswersCount) {
        this.incorrectAnswersCount = incorrectAnswersCount;
    }

    public Integer getUnansweredCount() {
        return unansweredCount;
    }

    public void setUnansweredCount(Integer unansweredCount) {
        this.unansweredCount = unansweredCount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt != null ? generatedAt : calculatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
        this.calculatedAt = generatedAt;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt != null ? calculatedAt : generatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
        this.generatedAt = calculatedAt;
    }

    public List<QuestionResult> getQuestionBreakdown() {
        return questionBreakdown;
    }

    public List<QuestionResult> getQuestionResults() {
        return questionBreakdown;
    }

    public void setQuestionBreakdown(List<QuestionResult> questionBreakdown) {
        this.questionBreakdown = questionBreakdown;
    }

    public void setQuestionResults(List<QuestionResult> questionResults) {
        this.questionBreakdown = questionResults;
    }

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }

    public static class ResultBuilder {
        private Long id;
        private Long attemptId;
        private Long studentUserId;
        private Long quizId;
        private Double totalScoreObtained = 0.0;
        private Double marksObtained = 0.0;
        private Double maxMarks = 0.0;
        private Double totalPossibleMarks = 0.0;
        private Double percentage = 0.0;
        private boolean isPassed = false;
        private String grade;
        private Integer totalQuestions = 0;
        private Integer correctAnswersCount = 0;
        private Integer incorrectAnswersCount = 0;
        private Integer unansweredCount = 0;
        private LocalDateTime generatedAt;
        private LocalDateTime calculatedAt;
        private List<QuestionResult> questionBreakdown = new ArrayList<>();

        public ResultBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResultBuilder attemptId(Long attemptId) {
            this.attemptId = attemptId;
            return this;
        }

        public ResultBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            return this;
        }

        public ResultBuilder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public ResultBuilder totalScoreObtained(Double totalScoreObtained) {
            this.totalScoreObtained = totalScoreObtained;
            this.marksObtained = totalScoreObtained;
            return this;
        }

        public ResultBuilder marksObtained(Double marksObtained) {
            this.marksObtained = marksObtained;
            this.totalScoreObtained = marksObtained;
            return this;
        }

        public ResultBuilder maxMarks(Double maxMarks) {
            this.maxMarks = maxMarks;
            this.totalPossibleMarks = maxMarks;
            return this;
        }

        public ResultBuilder totalPossibleMarks(Double totalPossibleMarks) {
            this.totalPossibleMarks = totalPossibleMarks;
            this.maxMarks = totalPossibleMarks;
            return this;
        }

        public ResultBuilder percentage(Double percentage) {
            this.percentage = percentage;
            return this;
        }

        public ResultBuilder isPassed(boolean isPassed) {
            this.isPassed = isPassed;
            return this;
        }

        public ResultBuilder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public ResultBuilder totalQuestions(Integer totalQuestions) {
            this.totalQuestions = totalQuestions;
            return this;
        }

        public ResultBuilder correctAnswersCount(Integer correctAnswersCount) {
            this.correctAnswersCount = correctAnswersCount;
            return this;
        }

        public ResultBuilder incorrectAnswersCount(Integer incorrectAnswersCount) {
            this.incorrectAnswersCount = incorrectAnswersCount;
            return this;
        }

        public ResultBuilder unansweredCount(Integer unansweredCount) {
            this.unansweredCount = unansweredCount;
            return this;
        }

        public ResultBuilder generatedAt(LocalDateTime generatedAt) {
            this.generatedAt = generatedAt;
            this.calculatedAt = generatedAt;
            return this;
        }

        public ResultBuilder calculatedAt(LocalDateTime calculatedAt) {
            this.calculatedAt = calculatedAt;
            this.generatedAt = calculatedAt;
            return this;
        }

        public ResultBuilder questionBreakdown(List<QuestionResult> questionBreakdown) {
            this.questionBreakdown = questionBreakdown;
            return this;
        }

        public ResultBuilder questionResults(List<QuestionResult> questionResults) {
            this.questionBreakdown = questionResults;
            return this;
        }

        public Result build() {
            return new Result(id, attemptId, studentUserId, quizId, totalScoreObtained, marksObtained, maxMarks, totalPossibleMarks, percentage, isPassed, grade, totalQuestions, correctAnswersCount, incorrectAnswersCount, unansweredCount, generatedAt, calculatedAt, questionBreakdown);
        }
    }
}
