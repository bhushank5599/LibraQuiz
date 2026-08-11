package com.lq.librarytransaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IssueRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Copy code is required")
    private String copyCode;

    private Integer durationDays;

    public IssueRequest() {
    }

    public IssueRequest(Long userId, String copyCode, Integer durationDays) {
        this.userId = userId;
        this.copyCode = copyCode;
        this.durationDays = durationDays;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public static IssueRequestBuilder builder() {
        return new IssueRequestBuilder();
    }

    public static class IssueRequestBuilder {
        private Long userId;
        private String copyCode;
        private Integer durationDays;

        public IssueRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public IssueRequestBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public IssueRequestBuilder durationDays(Integer durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public IssueRequest build() {
            return new IssueRequest(userId, copyCode, durationDays);
        }
    }
}
