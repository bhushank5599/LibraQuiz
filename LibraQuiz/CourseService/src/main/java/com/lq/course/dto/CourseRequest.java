package com.lq.course.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private Long categoryId;
    private Long teacherUserId;
    private String level;
    private String thumbnailUrl;

    public CourseRequest() {
    }

    public CourseRequest(String title, String description, Long categoryId, Long teacherUserId, String level, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.teacherUserId = teacherUserId;
        this.level = level;
        this.thumbnailUrl = thumbnailUrl;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(Long teacherUserId) {
        this.teacherUserId = teacherUserId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public static CourseRequestBuilder builder() {
        return new CourseRequestBuilder();
    }

    public static class CourseRequestBuilder {
        private String title;
        private String description;
        private Long categoryId;
        private Long teacherUserId;
        private String level;
        private String thumbnailUrl;

        public CourseRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CourseRequestBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            return this;
        }

        public CourseRequestBuilder level(String level) {
            this.level = level;
            return this;
        }

        public CourseRequestBuilder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public CourseRequest build() {
            return new CourseRequest(title, description, categoryId, teacherUserId, level, thumbnailUrl);
        }
    }
}
