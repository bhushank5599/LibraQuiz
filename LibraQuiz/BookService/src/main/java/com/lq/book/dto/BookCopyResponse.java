package com.lq.book.dto;

import com.lq.book.entity.BookCopy;

public class BookCopyResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private String copyCode;
    private BookCopy.CopyStatus status;
    private String rackLocation;
    private String conditionNote;

    public BookCopyResponse() {
    }

    public BookCopyResponse(Long id, Long bookId, String bookTitle, String copyCode, BookCopy.CopyStatus status, String rackLocation, String conditionNote) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.copyCode = copyCode;
        this.status = status;
        this.rackLocation = rackLocation;
        this.conditionNote = conditionNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static BookCopyResponseBuilder builder() {
        return new BookCopyResponseBuilder();
    }

    public static class BookCopyResponseBuilder {
        private Long id;
        private Long bookId;
        private String bookTitle;
        private String copyCode;
        private BookCopy.CopyStatus status;
        private String rackLocation;
        private String conditionNote;

        public BookCopyResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookCopyResponseBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BookCopyResponseBuilder bookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
            return this;
        }

        public BookCopyResponseBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public BookCopyResponseBuilder status(BookCopy.CopyStatus status) {
            this.status = status;
            return this;
        }

        public BookCopyResponseBuilder rackLocation(String rackLocation) {
            this.rackLocation = rackLocation;
            return this;
        }

        public BookCopyResponseBuilder conditionNote(String conditionNote) {
            this.conditionNote = conditionNote;
            return this;
        }

        public BookCopyResponse build() {
            return new BookCopyResponse(id, bookId, bookTitle, copyCode, status, rackLocation, conditionNote);
        }
    }
}
