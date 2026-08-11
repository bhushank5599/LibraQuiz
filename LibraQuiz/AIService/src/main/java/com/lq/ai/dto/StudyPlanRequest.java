package com.lq.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class StudyPlanRequest {

    @NotBlank(message = "Goal subject is required")
    private String goalSubject;

    private Integer hoursPerWeek = 10;
    private Long userId;

    public StudyPlanRequest() {
    }

    public StudyPlanRequest(String goalSubject, Integer hoursPerWeek, Long userId) {
        this.goalSubject = goalSubject;
        if (hoursPerWeek != null) this.hoursPerWeek = hoursPerWeek;
        this.userId = userId;
    }

    public String getGoalSubject() {
        return goalSubject;
    }

    public void setGoalSubject(String goalSubject) {
        this.goalSubject = goalSubject;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static StudyPlanRequestBuilder builder() {
        return new StudyPlanRequestBuilder();
    }

    public static class StudyPlanRequestBuilder {
        private String goalSubject;
        private Integer hoursPerWeek = 10;
        private Long userId;

        public StudyPlanRequestBuilder goalSubject(String goalSubject) {
            this.goalSubject = goalSubject;
            return this;
        }

        public StudyPlanRequestBuilder hoursPerWeek(Integer hoursPerWeek) {
            this.hoursPerWeek = hoursPerWeek;
            return this;
        }

        public StudyPlanRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public StudyPlanRequest build() {
            return new StudyPlanRequest(goalSubject, hoursPerWeek, userId);
        }
    }
}
