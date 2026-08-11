package com.lq.librarytransaction.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String copyCode;

    private Long bookId;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status = BorrowStatus.ISSUED;

    private Integer renewalCount = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BorrowRecord() {
    }

    public BorrowRecord(Long id, Long userId, String copyCode, Long bookId, LocalDate issueDate, LocalDate dueDate, LocalDate returnDate, BorrowStatus status, Integer renewalCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.copyCode = copyCode;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        if (status != null) this.status = status;
        if (renewalCount != null) this.renewalCount = renewalCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public Integer getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(Integer renewalCount) {
        this.renewalCount = renewalCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public enum BorrowStatus {
        ISSUED, RETURNED, OVERDUE, RENEWED, LOST, DAMAGED
    }

    public static BorrowRecordBuilder builder() {
        return new BorrowRecordBuilder();
    }

    public static class BorrowRecordBuilder {
        private Long id;
        private Long userId;
        private String copyCode;
        private Long bookId;
        private LocalDate issueDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private BorrowStatus status = BorrowStatus.ISSUED;
        private Integer renewalCount = 0;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BorrowRecordBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BorrowRecordBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BorrowRecordBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public BorrowRecordBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BorrowRecordBuilder issueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public BorrowRecordBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public BorrowRecordBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BorrowRecordBuilder status(BorrowStatus status) {
            this.status = status;
            return this;
        }

        public BorrowRecordBuilder renewalCount(Integer renewalCount) {
            this.renewalCount = renewalCount;
            return this;
        }

        public BorrowRecordBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BorrowRecordBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BorrowRecord build() {
            return new BorrowRecord(id, userId, copyCode, bookId, issueDate, dueDate, returnDate, status, renewalCount, createdAt, updatedAt);
        }
    }
}
