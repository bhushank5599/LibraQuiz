package com.lq.book.dto;

import com.lq.book.entity.BookCopy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookCopyRequest {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotBlank(message = "Copy code is required")
    private String copyCode;

    private BookCopy.CopyStatus status;
    private String rackLocation;
    private String conditionNote;

    public BookCopyRequest() {
    }

    public BookCopyRequest(Long bookId, String copyCode, BookCopy.CopyStatus status, String rackLocation, String conditionNote) {
        this.bookId = bookId;
        this.copyCode = copyCode;
        this.status = status;
        this.rackLocation = rackLocation;
        this.conditionNote = conditionNote;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public BookCopy.CopyStatus getStatus() {
        return status;
    }

    public void setStatus(BookCopy.CopyStatus status) {
        this.status = status;
    }

    public String getRackLocation() {
        return rackLocation;
    }

    public void setRackLocation(String rackLocation) {
        this.rackLocation = rackLocation;
    }

    public String getConditionNote() {
        return conditionNote;
    }

    public void setConditionNote(String conditionNote) {
        this.conditionNote = conditionNote;
    }

    public static BookCopyRequestBuilder builder() {
        return new BookCopyRequestBuilder();
    }

    public static class BookCopyRequestBuilder {
        private Long bookId;
        private String copyCode;
        private BookCopy.CopyStatus status;
        private String rackLocation;
        private String conditionNote;

        public BookCopyRequestBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BookCopyRequestBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public BookCopyRequestBuilder status(BookCopy.CopyStatus status) {
            this.status = status;
            return this;
        }

        public BookCopyRequestBuilder rackLocation(String rackLocation) {
            this.rackLocation = rackLocation;
            return this;
        }

        public BookCopyRequestBuilder conditionNote(String conditionNote) {
            this.conditionNote = conditionNote;
            return this;
        }

        public BookCopyRequest build() {
            return new BookCopyRequest(bookId, copyCode, status, rackLocation, conditionNote);
        }
    }
}
