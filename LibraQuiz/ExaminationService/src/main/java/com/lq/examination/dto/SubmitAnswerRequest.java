package com.lq.examination.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SubmitAnswerRequest {

    @NotNull(message = "Attempt ID is required")
    private Long attemptId;

    private List<AnswerDto> answers;

    public SubmitAnswerRequest() {
    }

    public SubmitAnswerRequest(Long attemptId, List<AnswerDto> answers) {
        this.attemptId = attemptId;
        this.answers = answers;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public static class AnswerDto {
        private Long questionId;
        private Long selectedOptionId;
        private String textAnswer;

        public AnswerDto() {
        }

        public AnswerDto(Long questionId, Long selectedOptionId, String textAnswer) {
            this.questionId = questionId;
            this.selectedOptionId = selectedOptionId;
            this.textAnswer = textAnswer;
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
    }
}
