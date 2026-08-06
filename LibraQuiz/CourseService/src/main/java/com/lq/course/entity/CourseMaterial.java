package com.lq.course.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_materials")
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String fileUrl;
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public CourseMaterial() {
    }

    public CourseMaterial(Long id, String title, String fileUrl, String fileType, Lesson lesson) {
        this.id = id;
        this.title = title;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.lesson = lesson;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public static CourseMaterialBuilder builder() {
        return new CourseMaterialBuilder();
    }

    public static class CourseMaterialBuilder {
        private Long id;
        private String title;
        private String fileUrl;
        private String fileType;
        private Lesson lesson;

        public CourseMaterialBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseMaterialBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseMaterialBuilder fileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public CourseMaterialBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public CourseMaterialBuilder lesson(Lesson lesson) {
            this.lesson = lesson;
            return this;
        }

        public CourseMaterial build() {
            return new CourseMaterial(id, title, fileUrl, fileType, lesson);
        }
    }
}
