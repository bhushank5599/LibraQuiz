package com.lq.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class NaturalLanguageSearchRequest {

    @NotBlank(message = "Prompt is required")
    private String prompt;

    private Long userId;

    public NaturalLanguageSearchRequest() {
    }

    public NaturalLanguageSearchRequest(String prompt, Long userId) {
        this.prompt = prompt;
        this.userId = userId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static NaturalLanguageSearchRequestBuilder builder() {
        return new NaturalLanguageSearchRequestBuilder();
    }

    public static class NaturalLanguageSearchRequestBuilder {
        private String prompt;
        private Long userId;

        public NaturalLanguageSearchRequestBuilder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public NaturalLanguageSearchRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public NaturalLanguageSearchRequest build() {
            return new NaturalLanguageSearchRequest(prompt, userId);
        }
    }
}
