package com.lq.course.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;
    private Integer sequenceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    public Module() {
    }

    public Module(Long id, String title, String description, Integer sequenceOrder, Course course, List<Lesson> lessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sequenceOrder = sequenceOrder;
        this.course = course;
        if (lessons != null) {
            this.lessons = lessons;
        }
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public static ModuleBuilder builder() {
        return new ModuleBuilder();
    }

    public static class ModuleBuilder {
        private Long id;
        private String title;
        private String description;
        private Integer sequenceOrder;
        private Course course;
        private List<Lesson> lessons = new ArrayList<>();

        public ModuleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ModuleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ModuleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ModuleBuilder sequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public ModuleBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public ModuleBuilder lessons(List<Lesson> lessons) {
            this.lessons = lessons;
            return this;
        }

        public Module build() {
            return new Module(id, title, description, sequenceOrder, course, lessons);
        }
    }
}
