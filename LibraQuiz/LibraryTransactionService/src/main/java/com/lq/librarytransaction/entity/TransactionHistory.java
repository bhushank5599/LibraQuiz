package com.lq.librarytransaction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String copyCode;
    private String action;
    private String details;
    private LocalDateTime timestamp;

    public TransactionHistory() {
    }

    public TransactionHistory(Long id, Long userId, String copyCode, String action, String details, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.copyCode = copyCode;
        this.action = action;
        this.details = details;
        this.timestamp = timestamp;
    }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
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

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static TransactionHistoryBuilder builder() {
        return new TransactionHistoryBuilder();
    }

    public static class TransactionHistoryBuilder {
        private Long id;
        private Long userId;
        private String copyCode;
        private String action;
        private String details;
        private LocalDateTime timestamp;

        public TransactionHistoryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TransactionHistoryBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public TransactionHistoryBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public TransactionHistoryBuilder action(String action) {
            this.action = action;
            return this;
        }

        public TransactionHistoryBuilder details(String details) {
            this.details = details;
            return this;
        }

        public TransactionHistoryBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionHistory build() {
            return new TransactionHistory(id, userId, copyCode, action, details, timestamp);
        }
    }
}
