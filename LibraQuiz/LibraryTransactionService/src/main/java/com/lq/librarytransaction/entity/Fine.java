package com.lq.librarytransaction.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "borrow_record_id")
    private BorrowRecord borrowRecord;

    @Column(nullable = false)
    private BigDecimal amount;

    private String reason;

    @Enumerated(EnumType.STRING)
    private FineStatus status = FineStatus.UNPAID;

    private LocalDateTime paidAt;

    public Fine() {
    }

    public Fine(Long id, Long userId, BorrowRecord borrowRecord, BigDecimal amount, String reason, FineStatus status, LocalDateTime paidAt) {
        this.id = id;
        this.userId = userId;
        this.borrowRecord = borrowRecord;
        this.amount = amount;
        this.reason = reason;
        if (status != null) this.status = status;
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

    public BorrowRecord getBorrowRecord() {
        return borrowRecord;
    }

    public void setBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecord = borrowRecord;
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

    public FineStatus getStatus() {
        return status;
    }

    public void setStatus(FineStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public enum FineStatus {
        UNPAID, PAID, WAIVED
    }

    public static FineBuilder builder() {
        return new FineBuilder();
    }

    public static class FineBuilder {
        private Long id;
        private Long userId;
        private BorrowRecord borrowRecord;
        private BigDecimal amount;
        private String reason;
        private FineStatus status = FineStatus.UNPAID;
        private LocalDateTime paidAt;

        public FineBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FineBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public FineBuilder borrowRecord(BorrowRecord borrowRecord) {
            this.borrowRecord = borrowRecord;
            return this;
        }

        public FineBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public FineBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public FineBuilder status(FineStatus status) {
            this.status = status;
            return this;
        }

        public FineBuilder paidAt(LocalDateTime paidAt) {
            this.paidAt = paidAt;
            return this;
        }

        public Fine build() {
            return new Fine(id, userId, borrowRecord, amount, reason, status, paidAt);
        }
    }
}
