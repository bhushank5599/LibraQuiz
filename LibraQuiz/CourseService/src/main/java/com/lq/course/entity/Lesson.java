package com.lq.course.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "module_lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String content;

    private String videoUrl;
    private Integer durationMinutes;
    private Integer sequenceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    private Long bookReferenceId;

    public Lesson() {
    }

    public Lesson(Long id, String title, String content, String videoUrl, Integer durationMinutes, Integer sequenceOrder, Module module, Long bookReferenceId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.durationMinutes = durationMinutes;
        this.sequenceOrder = sequenceOrder;
        this.module = module;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Long getBookReferenceId() {
        return bookReferenceId;
    }

    public void setBookReferenceId(Long bookReferenceId) {
        this.bookReferenceId = bookReferenceId;
    }

    public static LessonBuilder builder() {
        return new LessonBuilder();
    }

    public static class LessonBuilder {
        private Long id;
        private String title;
        private String content;
        private String videoUrl;
        private Integer durationMinutes;
        private Integer sequenceOrder;
        private Module module;
        private Long bookReferenceId;

        public LessonBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LessonBuilder title(String title) {
            this.title = title;
            return this;
        }

        public LessonBuilder content(String content) {
            this.content = content;
            return this;
        }

        public LessonBuilder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public LessonBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public LessonBuilder sequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public LessonBuilder module(Module module) {
            this.module = module;
            return this;
        }

        public LessonBuilder bookReferenceId(Long bookReferenceId) {
            this.bookReferenceId = bookReferenceId;
            return this;
        }

        public Lesson build() {
            return new Lesson(id, title, content, videoUrl, durationMinutes, sequenceOrder, module, bookReferenceId);
        }
    }
}
