package com.lq.quiz.dto;

import com.lq.quiz.entity.Quiz;

import java.time.LocalDateTime;
import java.util.List;

public class QuizResponse {

    private Long id;
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
    private boolean published;
    private Quiz.QuizStatus status;
    private LocalDateTime createdAt;
    private List<Long> questionBankIds;
    private List<Long> questionIds;

    public QuizResponse() {
    }

    public QuizResponse(Long id, String title, String description, Long courseId, Long categoryId, Long teacherUserId, Long createdByTeacherId, Integer durationMinutes, Double totalMarks, Double passingMarks, Double negativeMarkingRate, Integer maxAttempts, boolean published, Quiz.QuizStatus status, LocalDateTime createdAt, List<Long> questionBankIds, List<Long> questionIds) {
        this.id = id;
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
        this.status = status != null ? status : (published ? Quiz.QuizStatus.PUBLISHED : Quiz.QuizStatus.DRAFT);
        this.createdAt = createdAt;
        this.questionBankIds = questionBankIds != null ? questionBankIds : questionIds;
        this.questionIds = questionIds != null ? questionIds : questionBankIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Quiz.QuizStatus getStatus() {
        return status;
    }

    public void setStatus(Quiz.QuizStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public static QuizResponseBuilder builder() {
        return new QuizResponseBuilder();
    }

    public static class QuizResponseBuilder {
        private Long id;
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
        private boolean published;
        private Quiz.QuizStatus status;
        private LocalDateTime createdAt;
        private List<Long> questionBankIds;
        private List<Long> questionIds;

        public QuizResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuizResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuizResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public QuizResponseBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public QuizResponseBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuizResponseBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            this.createdByTeacherId = teacherUserId;
            return this;
        }

        public QuizResponseBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            this.teacherUserId = createdByTeacherId;
            return this;
        }

        public QuizResponseBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public QuizResponseBuilder totalMarks(Double totalMarks) {
            this.totalMarks = totalMarks;
            return this;
        }

        public QuizResponseBuilder passingMarks(Double passingMarks) {
            this.passingMarks = passingMarks;
            return this;
        }

        public QuizResponseBuilder negativeMarkingRate(Double negativeMarkingRate) {
            this.negativeMarkingRate = negativeMarkingRate;
            return this;
        }

        public QuizResponseBuilder maxAttempts(Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public QuizResponseBuilder published(boolean published) {
            this.published = published;
            return this;
        }

        public QuizResponseBuilder status(Quiz.QuizStatus status) {
            this.status = status;
            return this;
        }

        public QuizResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuizResponseBuilder questionBankIds(List<Long> questionBankIds) {
            this.questionBankIds = questionBankIds;
            this.questionIds = questionBankIds;
            return this;
        }

        public QuizResponseBuilder questionIds(List<Long> questionIds) {
            this.questionIds = questionIds;
            this.questionBankIds = questionIds;
            return this;
        }

        public QuizResponse build() {
            return new QuizResponse(id, title, description, courseId, categoryId, teacherUserId, createdByTeacherId, durationMinutes, totalMarks, passingMarks, negativeMarkingRate, maxAttempts, published, status, createdAt, questionBankIds, questionIds);
        }
    }
}
