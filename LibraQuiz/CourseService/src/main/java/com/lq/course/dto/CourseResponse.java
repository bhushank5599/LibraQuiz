package com.lq.course.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private Long teacherUserId;
    private String level;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private List<ModuleResponse> modules;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String title, String description, Long categoryId, Long teacherUserId, String level, String thumbnailUrl, LocalDateTime createdAt, List<ModuleResponse> modules) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.teacherUserId = teacherUserId;
        this.level = level;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.modules = modules;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ModuleResponse> getModules() {
        return modules;
    }

    public void setModules(List<ModuleResponse> modules) {
        this.modules = modules;
    }

    public static CourseResponseBuilder builder() {
        return new CourseResponseBuilder();
    }

    public static class CourseResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private Long categoryId;
        private Long teacherUserId;
        private String level;
        private String thumbnailUrl;
        private LocalDateTime createdAt;
        private List<ModuleResponse> modules;

        public CourseResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseResponseBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CourseResponseBuilder teacherUserId(Long teacherUserId) {
            this.teacherUserId = teacherUserId;
            return this;
        }

        public CourseResponseBuilder level(String level) {
            this.level = level;
            return this;
        }

        public CourseResponseBuilder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public CourseResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CourseResponseBuilder modules(List<ModuleResponse> modules) {
            this.modules = modules;
            return this;
        }

        public CourseResponse build() {
            return new CourseResponse(id, title, description, categoryId, teacherUserId, level, thumbnailUrl, createdAt, modules);
        }
    }

    public static class ModuleResponse {
        private Long id;
        private String title;
        private String description;
        private Integer sequenceOrder;
        private List<LessonResponse> lessons;

        public ModuleResponse() {
        }

        public ModuleResponse(Long id, String title, String description, Integer sequenceOrder, List<LessonResponse> lessons) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.sequenceOrder = sequenceOrder;
            this.lessons = lessons;
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

        public Integer getSequenceOrder() {
            return sequenceOrder;
        }

        public void setSequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
        }

        public List<LessonResponse> getLessons() {
            return lessons;
        }

        public void setLessons(List<LessonResponse> lessons) {
            this.lessons = lessons;
        }

        public static ModuleResponseBuilder builder() {
            return new ModuleResponseBuilder();
        }

        public static class ModuleResponseBuilder {
            private Long id;
            private String title;
            private String description;
            private Integer sequenceOrder;
            private List<LessonResponse> lessons;

            public ModuleResponseBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public ModuleResponseBuilder title(String title) {
                this.title = title;
                return this;
            }

            public ModuleResponseBuilder description(String description) {
                this.description = description;
                return this;
            }

            public ModuleResponseBuilder sequenceOrder(Integer sequenceOrder) {
                this.sequenceOrder = sequenceOrder;
                return this;
            }

            public ModuleResponseBuilder lessons(List<LessonResponse> lessons) {
                this.lessons = lessons;
                return this;
            }

            public ModuleResponse build() {
                return new ModuleResponse(id, title, description, sequenceOrder, lessons);
            }
        }
    }

    public static class LessonResponse {
        private Long id;
        private String title;
        private String content;
        private String videoUrl;
        private Integer durationMinutes;
        private Integer sequenceOrder;
        private Long bookReferenceId;

        public LessonResponse() {
        }

        public LessonResponse(Long id, String title, String content, String videoUrl, Integer durationMinutes, Integer sequenceOrder, Long bookReferenceId) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.videoUrl = videoUrl;
            this.durationMinutes = durationMinutes;
            this.sequenceOrder = sequenceOrder;
            this.bookReferenceId = bookReferenceId;
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

        public static LessonResponseBuilder builder() {
            return new LessonResponseBuilder();
        }

        public static class LessonResponseBuilder {
            private Long id;
            private String title;
            private String content;
            private String videoUrl;
            private Integer durationMinutes;
            private Integer sequenceOrder;
            private Long bookReferenceId;

            public LessonResponseBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public LessonResponseBuilder title(String title) {
                this.title = title;
                return this;
            }

            public LessonResponseBuilder content(String content) {
                this.content = content;
                return this;
            }

            public LessonResponseBuilder videoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
                return this;
            }

            public LessonResponseBuilder durationMinutes(Integer durationMinutes) {
                this.durationMinutes = durationMinutes;
                return this;
            }

            public LessonResponseBuilder sequenceOrder(Integer sequenceOrder) {
                this.sequenceOrder = sequenceOrder;
                return this;
            }

            public LessonResponseBuilder bookReferenceId(Long bookReferenceId) {
                this.bookReferenceId = bookReferenceId;
                return this;
            }

            public LessonResponse build() {
                return new LessonResponse(id, title, content, videoUrl, durationMinutes, sequenceOrder, bookReferenceId);
            }
        }
    }
}
