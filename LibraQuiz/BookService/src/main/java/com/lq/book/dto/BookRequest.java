package com.lq.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private String authorName;
    private String publisherName;
    private Integer publicationYear;
    private String edition;
    private String coverImageUrl;
    private Integer initialCopiesCount = 1;

    public BookRequest() {
    }

    public BookRequest(String title, String isbn, String description, Long categoryId, String authorName, String publisherName, Integer publicationYear, String edition, String coverImageUrl, Integer initialCopiesCount) {
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.categoryId = categoryId;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.publicationYear = publicationYear;
        this.edition = edition;
        this.coverImageUrl = coverImageUrl;
        if (initialCopiesCount != null) this.initialCopiesCount = initialCopiesCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Integer getInitialCopiesCount() {
        return initialCopiesCount;
    }

    public void setInitialCopiesCount(Integer initialCopiesCount) {
        this.initialCopiesCount = initialCopiesCount;
    }

    public static BookRequestBuilder builder() {
        return new BookRequestBuilder();
    }

    public static class BookRequestBuilder {
        private String title;
        private String isbn;
        private String description;
        private Long categoryId;
        private String authorName;
        private String publisherName;
        private Integer publicationYear;
        private String edition;
        private String coverImageUrl;
        private Integer initialCopiesCount = 1;

        public BookRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookRequestBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public BookRequestBuilder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public BookRequestBuilder publisherName(String publisherName) {
            this.publisherName = publisherName;
            return this;
        }

        public BookRequestBuilder publicationYear(Integer publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public BookRequestBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookRequestBuilder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public BookRequestBuilder initialCopiesCount(Integer initialCopiesCount) {
            this.initialCopiesCount = initialCopiesCount;
            return this;
        }

        public BookRequest build() {
            return new BookRequest(title, isbn, description, categoryId, authorName, publisherName, publicationYear, edition, coverImageUrl, initialCopiesCount);
        }
    }
}
