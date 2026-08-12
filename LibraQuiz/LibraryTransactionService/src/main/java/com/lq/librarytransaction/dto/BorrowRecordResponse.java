package com.lq.librarytransaction.dto;

import com.lq.librarytransaction.entity.BorrowRecord;

import java.time.LocalDate;

public class BorrowRecordResponse {

    private Long id;
    private Long userId;
    private String copyCode;
    private Long bookId;
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowRecord.BorrowStatus status;
    private Integer renewalCount;
    private Boolean isOverdue;

    public BorrowRecordResponse() {
    }

    public BorrowRecordResponse(Long id, Long userId, String copyCode, Long bookId, String bookTitle, LocalDate issueDate, LocalDate dueDate, LocalDate returnDate, BorrowRecord.BorrowStatus status, Integer renewalCount, Boolean isOverdue) {
        this.id = id;
        this.userId = userId;
        this.copyCode = copyCode;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.renewalCount = renewalCount;
        this.isOverdue = isOverdue;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public BorrowRecord.BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowRecord.BorrowStatus status) {
        this.status = status;
    }

    public Integer getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(Integer renewalCount) {
        this.renewalCount = renewalCount;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public static BorrowRecordResponseBuilder builder() {
        return new BorrowRecordResponseBuilder();
    }

    public static class BorrowRecordResponseBuilder {
        private Long id;
        private Long userId;
        private String copyCode;
        private Long bookId;
        private String bookTitle;
        private LocalDate issueDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private BorrowRecord.BorrowStatus status;
        private Integer renewalCount;
        private Boolean isOverdue;

        public BorrowRecordResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BorrowRecordResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BorrowRecordResponseBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public BorrowRecordResponseBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BorrowRecordResponseBuilder bookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
            return this;
        }

        public BorrowRecordResponseBuilder issueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public BorrowRecordResponseBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public BorrowRecordResponseBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BorrowRecordResponseBuilder status(BorrowRecord.BorrowStatus status) {
            this.status = status;
            return this;
        }

        public BorrowRecordResponseBuilder renewalCount(Integer renewalCount) {
            this.renewalCount = renewalCount;
            return this;
        }

        public BorrowRecordResponseBuilder isOverdue(Boolean isOverdue) {
            this.isOverdue = isOverdue;
            return this;
        }

        public BorrowRecordResponse build() {
            return new BorrowRecordResponse(id, userId, copyCode, bookId, bookTitle, issueDate, dueDate, returnDate, status, renewalCount, isOverdue);
        }
    }
}
