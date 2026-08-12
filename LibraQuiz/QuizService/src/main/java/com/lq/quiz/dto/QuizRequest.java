package com.lq.quiz.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class QuizRequest {

    @NotBlank(message = "Quiz title is required")
    private String title;

    private String description;
    private Long courseId;
    private Long categoryId;
    private Long teacherUserId;
    private Long createdByTeacherId;

    private Integer durationMinutes;
    private Double totalMarks;
    private Double passingMarks;
    private Double negativeMarkingRate;
    private Integer maxAttempts;
    private Boolean published;

    private List<Long> questionBankIds;
    private List<Long> questionIds;

    public QuizRequest() {
    }

    public QuizRequest(String title, String description, Long courseId, Long categoryId, Long teacherUserId, Long createdByTeacherId, Integer durationMinutes, Double totalMarks, Double passingMarks, Double negativeMarkingRate, Integer maxAttempts, Boolean published, List<Long> questionBankIds, List<Long> questionIds) {
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.categoryId = categoryId;
        this.teacherUserId = teacherUserId != null ? teacherUserId : createdByTeacherId;
        this.createdByTeacherId = createdByTeacherId != null ? createdByTeacherId : teacherUserId;
        this.durationMinutes = durationMinutes;
        this.totalMarks = totalMarks;
        this.passingMarks = passingMarks;
        this.negativeMarkingRate = negativeMarkingRate;
        this.maxAttempts = maxAttempts;
        this.published = published;
        this.questionBankIds = questionBankIds != null ? questionBankIds : questionIds;
        this.questionIds = questionIds != null ? questionIds : questionBankIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTeacherUserId() {
        return teacherUserId != null ? teacherUserId : createdByTeacherId;
    }

    public void setTeacherUserId(Long teacherUserId) {
        this.teacherUserId = teacherUserId;
        this.createdByTeacherId = teacherUserId;
    }

    public Long getCreatedByTeacherId() {
        return createdByTeacherId != null ? createdByTeacherId : teacherUserId;
    }

    public void setCreatedByTeacherId(Long createdByTeacherId) {
        this.createdByTeacherId = createdByTeacherId;
        this.teacherUserId = createdByTeacherId;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Double passingMarks) {
        this.passingMarks = passingMarks;
    }

    public Double getNegativeMarkingRate() {
        return negativeMarkingRate;
    }

    public void setNegativeMarkingRate(Double negativeMarkingRate) {
        this.negativeMarkingRate = negativeMarkingRate;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public List<Long> getQuestionBankIds() {
        return questionBankIds != null ? questionBankIds : questionIds;
    }

    public void setQuestionBankIds(List<Long> questionBankIds) {
        this.questionBankIds = questionBankIds;
        this.questionIds = questionBankIds;
    }

    public List<Long> getQuestionIds() {
        return questionIds != null ? questionIds : questionBankIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
        this.questionBankIds = questionIds;
    }

    public static QuizRequestBuilder builder() {
        return new QuizRequestBuilder();
    }

    public static class QuizRequestBuilder {
        private String title;
        private String description;
        private Long courseId;
        private Long categoryId;
        private Long teacherUserId;
        private Long createdByTeacherId;
        private Integer durationMinutes;
        private Double totalMarks;
        private Double passingMarks;
        private Double negativeMarkingRate;
        private Integer maxAttempts;
        private Boolean published;
        private List<Long> questionBankIds;
        private List<Long> questionIds;

        public QuizRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuizRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public QuizRequestBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public QuizRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuizRequestBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            this.createdByTeacherId = teacherUserId;
            return this;
        }

        public QuizRequestBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            this.teacherUserId = createdByTeacherId;
            return this;
        }

        public QuizRequestBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public QuizRequestBuilder totalMarks(Double totalMarks) {
            this.totalMarks = totalMarks;
            return this;
        }

        public QuizRequestBuilder passingMarks(Double passingMarks) {
            this.passingMarks = passingMarks;
            return this;
        }

        public QuizRequestBuilder negativeMarkingRate(Double negativeMarkingRate) {
            this.negativeMarkingRate = negativeMarkingRate;
            return this;
        }

        public QuizRequestBuilder maxAttempts(Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public QuizRequestBuilder published(Boolean published) {
            this.published = published;
            return this;
        }

        public QuizRequestBuilder questionBankIds(List<Long> questionBankIds) {
            this.questionBankIds = questionBankIds;
            this.questionIds = questionBankIds;
            return this;
        }

        public QuizRequestBuilder questionIds(List<Long> questionIds) {
            this.questionIds = questionIds;
            this.questionBankIds = questionIds;
            return this;
        }

        public QuizRequest build() {
            return new QuizRequest(title, description, courseId, categoryId, teacherUserId, createdByTeacherId, durationMinutes, totalMarks, passingMarks, negativeMarkingRate, maxAttempts, published, questionBankIds, questionIds);
        }
    }
}
