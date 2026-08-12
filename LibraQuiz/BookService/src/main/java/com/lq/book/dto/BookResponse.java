package com.lq.book.dto;

import java.util.List;

public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private String description;
    private Long categoryId;
    private String categoryName;
    private String authorName;
    private String publisherName;
    private Integer publicationYear;
    private String edition;
    private String coverImageUrl;
    private Integer totalCopies;
    private Integer availableCopies;
    private List<BookCopyResponse> copies;

    public BookResponse() {
    }

    public BookResponse(Long id, String title, String isbn, String description, Long categoryId, String categoryName, String authorName, String publisherName, Integer publicationYear, String edition, String coverImageUrl, Integer totalCopies, Integer availableCopies, List<BookCopyResponse> copies) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.publicationYear = publicationYear;
        this.edition = edition;
        this.coverImageUrl = coverImageUrl;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.copies = copies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<BookCopyResponse> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopyResponse> copies) {
        this.copies = copies;
    }

    public static BookResponseBuilder builder() {
        return new BookResponseBuilder();
    }

    public static class BookResponseBuilder {
        private Long id;
        private String title;
        private String isbn;
        private String description;
        private Long categoryId;
        private String categoryName;
        private String authorName;
        private String publisherName;
        private Integer publicationYear;
        private String edition;
        private String coverImageUrl;
        private Integer totalCopies;
        private Integer availableCopies;
        private List<BookCopyResponse> copies;

        public BookResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookResponseBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookResponseBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public BookResponseBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public BookResponseBuilder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public BookResponseBuilder publisherName(String publisherName) {
            this.publisherName = publisherName;
            return this;
        }

        public BookResponseBuilder publicationYear(Integer publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public BookResponseBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookResponseBuilder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public BookResponseBuilder totalCopies(Integer totalCopies) {
            this.totalCopies = totalCopies;
            return this;
        }

        public BookResponseBuilder availableCopies(Integer availableCopies) {
            this.availableCopies = availableCopies;
            return this;
        }

        public BookResponseBuilder copies(List<BookCopyResponse> copies) {
            this.copies = copies;
            return this;
        }

        public BookResponse build() {
            return new BookResponse(id, title, isbn, description, categoryId, categoryName, authorName, publisherName, publicationYear, edition, coverImageUrl, totalCopies, availableCopies, copies);
        }
    }
}
