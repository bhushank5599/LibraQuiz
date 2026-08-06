package com.lq.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LessonRequest {

    @NotNull(message = "Module ID is required")
    private Long moduleId;

    @NotBlank(message = "Title is required")
    private String title;

    private String content;
    private String videoUrl;
    private Integer durationMinutes;
    private Integer sequenceOrder;
    private Long bookReferenceId;

    public LessonRequest() {
    }

    public LessonRequest(Long moduleId, String title, String content, String videoUrl, Integer durationMinutes, Integer sequenceOrder, Long bookReferenceId) {
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.durationMinutes = durationMinutes;
        this.sequenceOrder = sequenceOrder;
        this.bookReferenceId = bookReferenceId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public Long getBookReferenceId() {
        return bookReferenceId;
    }

    public void setBookReferenceId(Long bookReferenceId) {
        this.bookReferenceId = bookReferenceId;
    }

    public static LessonRequestBuilder builder() {
        return new LessonRequestBuilder();
    }

    public static class LessonRequestBuilder {
        private Long moduleId;
        private String title;
        private String content;
        private String videoUrl;
        private Integer durationMinutes;
        private Integer sequenceOrder;
        private Long bookReferenceId;

        public LessonRequestBuilder moduleId(Long moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public LessonRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public LessonRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public LessonRequestBuilder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public LessonRequestBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public LessonRequestBuilder sequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public LessonRequestBuilder bookReferenceId(Long bookReferenceId) {
            this.bookReferenceId = bookReferenceId;
            return this;
        }

        public LessonRequest build() {
            return new LessonRequest(moduleId, title, content, videoUrl, durationMinutes, sequenceOrder, bookReferenceId);
        }
    }
}
