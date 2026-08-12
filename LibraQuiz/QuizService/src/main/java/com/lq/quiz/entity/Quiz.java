package com.lq.quiz.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private Long courseId;
    private Long categoryId;
    private Long teacherUserId;
    private Long createdByTeacherId;

    private Integer durationMinutes = 30;
    private Double totalMarks = 100.0;
    private Double passingMarks = 40.0;
    private Double negativeMarkingRate = 0.0;
    private Integer maxAttempts = 3;
    private boolean published = false;

    @Enumerated(EnumType.STRING)
    private QuizStatus status = QuizStatus.DRAFT;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> questions = new ArrayList<>();

    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private QuizSetting settings;

    public Quiz() {
    }

    public Quiz(Long id, String title, String description, Long courseId, Long categoryId, Long teacherUserId, Long createdByTeacherId, Integer durationMinutes, Double totalMarks, Double passingMarks, Double negativeMarkingRate, Integer maxAttempts, boolean published, QuizStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, List<QuizQuestion> questions, QuizSetting settings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.categoryId = categoryId;
        this.teacherUserId = teacherUserId != null ? teacherUserId : createdByTeacherId;
        this.createdByTeacherId = createdByTeacherId != null ? createdByTeacherId : teacherUserId;
        if (durationMinutes != null) this.durationMinutes = durationMinutes;
        if (totalMarks != null) this.totalMarks = totalMarks;
        if (passingMarks != null) this.passingMarks = passingMarks;
        if (negativeMarkingRate != null) this.negativeMarkingRate = negativeMarkingRate;
        if (maxAttempts != null) this.maxAttempts = maxAttempts;
        this.published = published;
        this.status = status != null ? status : (published ? QuizStatus.PUBLISHED : QuizStatus.DRAFT);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        if (questions != null) {
            this.questions = questions;
        }
        this.settings = settings;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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
        return published || status == QuizStatus.PUBLISHED;
    }

    public void setPublished(boolean published) {
        this.published = published;
        if (published) this.status = QuizStatus.PUBLISHED;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
        this.published = (status == QuizStatus.PUBLISHED);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public QuizSetting getSettings() {
        return settings;
    }

    public void setSettings(QuizSetting settings) {
        this.settings = settings;
    }

    public enum QuizStatus {
        DRAFT, PUBLISHED, ARCHIVED
    }

    public static QuizBuilder builder() {
        return new QuizBuilder();
    }

    public static class QuizBuilder {
        private Long id;
        private String title;
        private String description;
        private Long courseId;
        private Long categoryId;
        private Long teacherUserId;
        private Long createdByTeacherId;
        private Integer durationMinutes = 30;
        private Double totalMarks = 100.0;
        private Double passingMarks = 40.0;
        private Double negativeMarkingRate = 0.0;
        private Integer maxAttempts = 3;
        private boolean published = false;
        private QuizStatus status = QuizStatus.DRAFT;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<QuizQuestion> questions = new ArrayList<>();
        private QuizSetting settings;

        public QuizBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuizBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuizBuilder description(String description) {
            this.description = description;
            return this;
        }

        public QuizBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public QuizBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public QuizBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            this.createdByTeacherId = teacherUserId;
            return this;
        }

        public QuizBuilder createdByTeacherId(Long createdByTeacherId) {
            this.createdByTeacherId = createdByTeacherId;
            this.teacherUserId = createdByTeacherId;
            return this;
        }

        public QuizBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public QuizBuilder totalMarks(Double totalMarks) {
            this.totalMarks = totalMarks;
            return this;
        }

        public QuizBuilder passingMarks(Double passingMarks) {
            this.passingMarks = passingMarks;
            return this;
        }

        public QuizBuilder negativeMarkingRate(Double negativeMarkingRate) {
            this.negativeMarkingRate = negativeMarkingRate;
            return this;
        }

        public QuizBuilder maxAttempts(Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public QuizBuilder published(boolean published) {
            this.published = published;
            if (published) this.status = QuizStatus.PUBLISHED;
            return this;
        }

        public QuizBuilder status(QuizStatus status) {
            this.status = status;
            if (status == QuizStatus.PUBLISHED) this.published = true;
            return this;
        }

        public QuizBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuizBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public QuizBuilder questions(List<QuizQuestion> questions) {
            this.questions = questions;
            return this;
        }

        public QuizBuilder settings(QuizSetting settings) {
            this.settings = settings;
            return this;
        }

        public Quiz build() {
            return new Quiz(id, title, description, courseId, categoryId, teacherUserId, createdByTeacherId, durationMinutes, totalMarks, passingMarks, negativeMarkingRate, maxAttempts, published, status, createdAt, updatedAt, questions, settings);
        }
    }
}
