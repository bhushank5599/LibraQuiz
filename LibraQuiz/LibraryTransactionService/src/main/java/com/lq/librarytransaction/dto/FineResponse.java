package com.lq.librarytransaction.dto;

import com.lq.librarytransaction.entity.Fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FineResponse {

    private Long id;
    private Long userId;
    private Long borrowRecordId;
    private BigDecimal amount;
    private String reason;
    private Fine.FineStatus status;
    private LocalDateTime paidAt;

    public FineResponse() {
    }

    public FineResponse(Long id, Long userId, Long borrowRecordId, BigDecimal amount, String reason, Fine.FineStatus status, LocalDateTime paidAt) {
        this.id = id;
        this.userId = userId;
        this.borrowRecordId = borrowRecordId;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.paidAt = paidAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBorrowRecordId() {
        return borrowRecordId;
    }

    public void setBorrowRecordId(Long borrowRecordId) {
        this.borrowRecordId = borrowRecordId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Fine.FineStatus getStatus() {
        return status;
    }

    public void setStatus(Fine.FineStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public static FineResponseBuilder builder() {
        return new FineResponseBuilder();
    }

    public static class FineResponseBuilder {
        private Long id;
        private Long userId;
        private Long borrowRecordId;
        private BigDecimal amount;
        private String reason;
        private Fine.FineStatus status;
        private LocalDateTime paidAt;

        public FineResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FineResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public FineResponseBuilder borrowRecordId(Long borrowRecordId) {
            this.borrowRecordId = borrowRecordId;
            return this;
        }

        public FineResponseBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public FineResponseBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public FineResponseBuilder status(Fine.FineStatus status) {
            this.status = status;
            return this;
        }

        public FineResponseBuilder paidAt(LocalDateTime paidAt) {
            this.paidAt = paidAt;
            return this;
        }

        public FineResponse build() {
            return new FineResponse(id, userId, borrowRecordId, amount, reason, status, paidAt);
        }
    }
}
