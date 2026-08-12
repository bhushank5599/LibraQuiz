package com.lq.examination.dto;

import jakarta.validation.constraints.NotNull;

public class StartExamRequest {

    @NotNull(message = "Quiz ID is required")
    private Long quizId;

    @NotNull(message = "Student User ID is required")
    private Long studentUserId;

    public StartExamRequest() {
    }

    public StartExamRequest(Long quizId, Long studentUserId) {
        this.quizId = quizId;
        this.studentUserId = studentUserId;
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

    public static StartExamRequestBuilder builder() {
        return new StartExamRequestBuilder();
    }

    public static class StartExamRequestBuilder {
        private Long quizId;
        private Long studentUserId;

        public StartExamRequestBuilder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public StartExamRequestBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            return this;
        }

        public StartExamRequest build() {
            return new StartExamRequest(quizId, studentUserId);
        }
    }
}
