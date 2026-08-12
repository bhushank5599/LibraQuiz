package com.lq.questionmgmt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_versions")
public class QuestionVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    private Integer versionNumber;

    @Column(length = 2000)
    private String questionSnapshotJson;

    private String changeLog;
    private LocalDateTime createdAt;

    public QuestionVersion() {
    }

    public QuestionVersion(Long id, Long questionId, Integer versionNumber, String questionSnapshotJson, String changeLog, LocalDateTime createdAt) {
        this.id = id;
        this.questionId = questionId;
        this.versionNumber = versionNumber;
        this.questionSnapshotJson = questionSnapshotJson;
        this.changeLog = changeLog;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getQuestionSnapshotJson() {
        return questionSnapshotJson;
    }

    public void setQuestionSnapshotJson(String questionSnapshotJson) {
        this.questionSnapshotJson = questionSnapshotJson;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static QuestionVersionBuilder builder() {
        return new QuestionVersionBuilder();
    }

    public static class QuestionVersionBuilder {
        private Long id;
        private Long questionId;
        private Integer versionNumber;
        private String questionSnapshotJson;
        private String changeLog;
        private LocalDateTime createdAt;

        public QuestionVersionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionVersionBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionVersionBuilder versionNumber(Integer versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public QuestionVersionBuilder questionSnapshotJson(String questionSnapshotJson) {
            this.questionSnapshotJson = questionSnapshotJson;
            return this;
        }

        public QuestionVersionBuilder changeLog(String changeLog) {
            this.changeLog = changeLog;
            return this;
        }

        public QuestionVersionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public QuestionVersion build() {
            return new QuestionVersion(id, questionId, versionNumber, questionSnapshotJson, changeLog, createdAt);
        }
    }
}
