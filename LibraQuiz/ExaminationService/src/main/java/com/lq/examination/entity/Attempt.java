package com.lq.examination.entity;

import com.lq.examination.entity.enums.AttemptStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quizId;

    @Column(nullable = false)
    private Long studentUserId;

    private Integer attemptNumber = 1;

    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private LocalDateTime expectedEndTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttemptStatus status = AttemptStatus.IN_PROGRESS;

    private Double totalScoreObtained = 0.0;
    private Double totalMarksObtained = 0.0;
    private Boolean isPassed = false;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttemptAnswer> submittedAnswers = new ArrayList<>();

    public Attempt() {
    }

    public Attempt(Long id, Long quizId, Long studentUserId, Integer attemptNumber, LocalDateTime startTime, LocalDateTime submitTime, LocalDateTime expectedEndTime, AttemptStatus status, Double totalScoreObtained, Double totalMarksObtained, Boolean isPassed, List<AttemptAnswer> submittedAnswers) {
        this.id = id;
        this.quizId = quizId;
        this.studentUserId = studentUserId;
        if (attemptNumber != null) this.attemptNumber = attemptNumber;
        this.startTime = startTime;
        this.submitTime = submitTime;
        this.expectedEndTime = expectedEndTime;
        if (status != null) this.status = status;
        this.totalScoreObtained = totalScoreObtained != null ? totalScoreObtained : totalMarksObtained;
        this.totalMarksObtained = totalMarksObtained != null ? totalMarksObtained : totalScoreObtained;
        if (isPassed != null) this.isPassed = isPassed;
        if (submittedAnswers != null) {
            this.submittedAnswers = submittedAnswers;
        }
    }

    @PrePersist
    protected void onCreate() {
        this.startTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Long studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public LocalDateTime getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(LocalDateTime expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void setStatus(AttemptStatus status) {
        this.status = status;
    }

    public Double getTotalScoreObtained() {
        return totalScoreObtained != null ? totalScoreObtained : totalMarksObtained;
    }

    public void setTotalScoreObtained(Double totalScoreObtained) {
        this.totalScoreObtained = totalScoreObtained;
        this.totalMarksObtained = totalScoreObtained;
    }

    public Double getTotalMarksObtained() {
        return totalMarksObtained != null ? totalMarksObtained : totalScoreObtained;
    }

    public void setTotalMarksObtained(Double totalMarksObtained) {
        this.totalMarksObtained = totalMarksObtained;
        this.totalScoreObtained = totalMarksObtained;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public Boolean isPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public void setPassed(Boolean passed) {
        this.isPassed = passed;
    }

    public List<AttemptAnswer> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public List<AttemptAnswer> getAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(List<AttemptAnswer> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    public void setAnswers(List<AttemptAnswer> answers) {
        this.submittedAnswers = answers;
    }

    public static AttemptBuilder builder() {
        return new AttemptBuilder();
    }

    public static class AttemptBuilder {
        private Long id;
        private Long quizId;
        private Long studentUserId;
        private Integer attemptNumber = 1;
        private LocalDateTime startTime;
        private LocalDateTime submitTime;
        private LocalDateTime expectedEndTime;
        private AttemptStatus status = AttemptStatus.IN_PROGRESS;
        private Double totalScoreObtained = 0.0;
        private Double totalMarksObtained = 0.0;
        private Boolean isPassed = false;
        private List<AttemptAnswer> submittedAnswers = new ArrayList<>();

        public AttemptBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AttemptBuilder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public AttemptBuilder studentUserId(Long studentUserId) {
            this.studentUserId = studentUserId;
            return this;
        }

        public AttemptBuilder attemptNumber(Integer attemptNumber) {
            this.attemptNumber = attemptNumber;
            return this;
        }

        public AttemptBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public AttemptBuilder submitTime(LocalDateTime submitTime) {
            this.submitTime = submitTime;
            return this;
        }

        public AttemptBuilder expectedEndTime(LocalDateTime expectedEndTime) {
            this.expectedEndTime = expectedEndTime;
            return this;
        }

        public AttemptBuilder status(AttemptStatus status) {
            this.status = status;
            return this;
        }

        public AttemptBuilder totalScoreObtained(Double totalScoreObtained) {
            this.totalScoreObtained = totalScoreObtained;
            this.totalMarksObtained = totalScoreObtained;
            return this;
        }

        public AttemptBuilder totalMarksObtained(Double totalMarksObtained) {
            this.totalMarksObtained = totalMarksObtained;
            this.totalScoreObtained = totalMarksObtained;
            return this;
        }

        public AttemptBuilder isPassed(Boolean isPassed) {
            this.isPassed = isPassed;
            return this;
        }

        public AttemptBuilder submittedAnswers(List<AttemptAnswer> submittedAnswers) {
            this.submittedAnswers = submittedAnswers;
            return this;
        }

        public AttemptBuilder answers(List<AttemptAnswer> answers) {
            this.submittedAnswers = answers;
            return this;
        }

        public Attempt build() {
            return new Attempt(id, quizId, studentUserId, attemptNumber, startTime, submitTime, expectedEndTime, status, totalScoreObtained, totalMarksObtained, isPassed, submittedAnswers);
        }
    }
}
