package com.lq.examination.dto;

import com.lq.examination.entity.enums.AttemptStatus;

import java.time.LocalDateTime;
import java.util.List;

public class AttemptResponse {

    private Long id;
    private Long quizId;
    private Long studentUserId;
    private Integer attemptNumber;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private LocalDateTime expectedEndTime;
    private AttemptStatus status;
    private Double totalScoreObtained;
    private Double totalMarksObtained;
    private Boolean isPassed;
    private List<AnswerResponse> answers;

    public AttemptResponse() {
    }

    public AttemptResponse(Long id, Long quizId, Long studentUserId, Integer attemptNumber, LocalDateTime startTime, LocalDateTime submitTime, LocalDateTime expectedEndTime, AttemptStatus status, Double totalScoreObtained, Double totalMarksObtained, Boolean isPassed, List<AnswerResponse> answers) {
        this.id = id;
        this.quizId = quizId;
        this.studentUserId = studentUserId;
        this.attemptNumber = attemptNumber;
        this.startTime = startTime;
        this.submitTime = submitTime;
        this.expectedEndTime = expectedEndTime;
        this.status = status;
        this.totalScoreObtained = totalScoreObtained != null ? totalScoreObtained : totalMarksObtained;
        this.totalMarksObtained = totalMarksObtained != null ? totalMarksObtained : totalScoreObtained;
        this.isPassed = isPassed;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Long studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public LocalDateTime getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(LocalDateTime expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void setStatus(AttemptStatus status) {
        this.status = status;
    }

    public Double getTotalScoreObtained() {
        return totalScoreObtained != null ? totalScoreObtained : totalMarksObtained;
    }

    public void setTotalScoreObtained(Double totalScoreObtained) {
        this.totalScoreObtained = totalScoreObtained;
        this.totalMarksObtained = totalScoreObtained;
    }

    public Double getTotalMarksObtained() {
        return totalMarksObtained != null ? totalMarksObtained : totalScoreObtained;
    }

    public void setTotalMarksObtained(Double totalMarksObtained) {
        this.totalMarksObtained = totalMarksObtained;
        this.totalScoreObtained = totalMarksObtained;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResponse> answers) {
        this.answers = answers;
    }

    public static AttemptResponseBuilder builder() {
        return new AttemptResponseBuilder();
    }

    public static class AttemptResponseBuilder {
        private Long id;
        private Long quizId;
        private Long studentUserId;
        private Integer attemptNumber;
        private LocalDateTime startTime;
        private LocalDateTime submitTime;
        private LocalDateTime expectedEndTime;
        private AttemptStatus status;
        private Double totalScoreObtained;
        private Double totalMarksObtained;
        private Boolean isPassed;
        private List<AnswerResponse> answers;

        public AttemptResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AttemptResponseBuilder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public AttemptResponseBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            return this;
        }

        public AttemptResponseBuilder attemptNumber(Integer attemptNumber) {
            this.attemptNumber = attemptNumber;
            return this;
        }

        public AttemptResponseBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public AttemptResponseBuilder submitTime(LocalDateTime submitTime) {
            this.submitTime = submitTime;
            return this;
        }

        public AttemptResponseBuilder expectedEndTime(LocalDateTime expectedEndTime) {
            this.expectedEndTime = expectedEndTime;
            return this;
        }

        public AttemptResponseBuilder status(AttemptStatus status) {
            this.status = status;
            return this;
        }

        public AttemptResponseBuilder totalScoreObtained(Double totalScoreObtained) {
            this.totalScoreObtained = totalScoreObtained;
            this.totalMarksObtained = totalScoreObtained;
            return this;
        }

        public AttemptResponseBuilder totalMarksObtained(Double totalMarksObtained) {
            this.totalMarksObtained = totalMarksObtained;
            this.totalScoreObtained = totalMarksObtained;
            return this;
        }

        public AttemptResponseBuilder isPassed(Boolean isPassed) {
            this.isPassed = isPassed;
            return this;
        }

        public AttemptResponseBuilder answers(List<AnswerResponse> answers) {
            this.answers = answers;
            return this;
        }

        public AttemptResponse build() {
            return new AttemptResponse(id, quizId, studentUserId, attemptNumber, startTime, submitTime, expectedEndTime, status, totalScoreObtained, totalMarksObtained, isPassed, answers);
        }
    }

    public static class AnswerResponse {
        private Long id;
        private Long questionId;
        private Long selectedOptionId;
        private String textAnswer;
        private Boolean isCorrect;
        private Double scoreAwarded;
        private Double marksObtained;

        public AnswerResponse() {
        }

        public AnswerResponse(Long id, Long questionId, Long selectedOptionId, String textAnswer, Boolean isCorrect, Double scoreAwarded, Double marksObtained) {
            this.id = id;
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

        public static AnswerResponseBuilder builder() {
            return new AnswerResponseBuilder();
        }

        public static class AnswerResponseBuilder {
            private Long id;
            private Long questionId;
            private Long selectedOptionId;
            private String textAnswer;
            private Boolean isCorrect;
            private Double scoreAwarded;
            private Double marksObtained;

            public AnswerResponseBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public AnswerResponseBuilder questionId(Long questionId) {
                this.questionId = questionId;
                return this;
            }

            public AnswerResponseBuilder selectedOptionId(Long selectedOptionId) {
                this.selectedOptionId = selectedOptionId;
                return this;
            }

            public AnswerResponseBuilder textAnswer(String textAnswer) {
                this.textAnswer = textAnswer;
                return this;
            }

            public AnswerResponseBuilder isCorrect(Boolean isCorrect) {
                this.isCorrect = isCorrect;
                return this;
            }

            public AnswerResponseBuilder scoreAwarded(Double scoreAwarded) {
                this.scoreAwarded = scoreAwarded;
                this.marksObtained = scoreAwarded;
                return this;
            }

            public AnswerResponseBuilder marksObtained(Double marksObtained) {
                this.marksObtained = marksObtained;
                this.scoreAwarded = marksObtained;
                return this;
            }

            public AnswerResponse build() {
                return new AnswerResponse(id, questionId, selectedOptionId, textAnswer, isCorrect, scoreAwarded, marksObtained);
            }
        }
    }
}
