package com.lq.book.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String copyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopyStatus status = CopyStatus.AVAILABLE;

    private String rackLocation;
    private String conditionNote;

    public BookCopy() {
    }

    public BookCopy(Long id, String copyCode, Book book, CopyStatus status, String rackLocation, String conditionNote) {
        this.id = id;
        this.copyCode = copyCode;
        this.book = book;
        if (status != null) this.status = status;
        this.rackLocation = rackLocation;
        this.conditionNote = conditionNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public void setStatus(CopyStatus status) {
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

    public enum CopyStatus {
        AVAILABLE, BORROWED, RESERVED, LOST, DAMAGED, MAINTENANCE
    }

    public static BookCopyBuilder builder() {
        return new BookCopyBuilder();
    }

    public static class BookCopyBuilder {
        private Long id;
        private String copyCode;
        private Book book;
        private CopyStatus status = CopyStatus.AVAILABLE;
        private String rackLocation;
        private String conditionNote;

        public BookCopyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookCopyBuilder copyCode(String copyCode) {
            this.copyCode = copyCode;
            return this;
        }

        public BookCopyBuilder book(Book book) {
            this.book = book;
            return this;
        }

        public BookCopyBuilder status(CopyStatus status) {
            this.status = status;
            return this;
        }

        public BookCopyBuilder rackLocation(String rackLocation) {
            this.rackLocation = rackLocation;
            return this;
        }

        public BookCopyBuilder conditionNote(String conditionNote) {
            this.conditionNote = conditionNote;
            return this;
        }

        public BookCopy build() {
            return new BookCopy(id, copyCode, book, status, rackLocation, conditionNote);
        }
    }
}
