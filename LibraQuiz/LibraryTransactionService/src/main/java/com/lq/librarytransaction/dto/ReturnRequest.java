package com.lq.librarytransaction.dto;

import jakarta.validation.constraints.NotBlank;

public class ReturnRequest {

    @NotBlank(message = "Copy code is required")
    private String copyCode;

    private boolean isDamaged;
    private boolean isLost;
    private String note;

    public ReturnRequest() {
    }

    public ReturnRequest(String copyCode, boolean isDamaged, boolean isLost, String note) {
        this.copyCode = copyCode;
        this.isDamaged = isDamaged;
        this.isLost = isLost;
        this.note = note;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static ReturnRequestBuilder builder() {
        return new ReturnRequestBuilder();
    }

    public static class ReturnRequestBuilder {
        private String copyCode;
        private boolean isDamaged;
        private boolean isLost;
        private String note;

        public ReturnRequestBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public ReturnRequestBuilder isDamaged(boolean isDamaged) {
            this.isDamaged = isDamaged;
            return this;
        }

        public ReturnRequestBuilder isLost(boolean isLost) {
            this.isLost = isLost;
            return this;
        }

        public ReturnRequestBuilder note(String note) {
            this.note = note;
            return this;
        }

        public ReturnRequest build() {
            return new ReturnRequest(copyCode, isDamaged, isLost, note);
        }
    }
}
