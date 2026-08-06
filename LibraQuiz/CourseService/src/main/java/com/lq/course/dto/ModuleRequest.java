package com.lq.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModuleRequest {

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private Integer sequenceOrder;

    public ModuleRequest() {
    }

    public ModuleRequest(Long courseId, String title, String description, Integer sequenceOrder) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.sequenceOrder = sequenceOrder;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public static ModuleRequestBuilder builder() {
        return new ModuleRequestBuilder();
    }

    public static class ModuleRequestBuilder {
        private Long courseId;
        private String title;
        private String description;
        private Integer sequenceOrder;

        public ModuleRequestBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public ModuleRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ModuleRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ModuleRequestBuilder sequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public ModuleRequest build() {
            return new ModuleRequest(courseId, title, description, sequenceOrder);
        }
    }
}
