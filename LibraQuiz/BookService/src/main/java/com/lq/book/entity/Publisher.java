package com.lq.book.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String contactEmail;

    public Publisher() {
    }

    public Publisher(Long id, String name, String address, String contactEmail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public static PublisherBuilder builder() {
        return new PublisherBuilder();
    }

    public static class PublisherBuilder {
        private Long id;
        private String name;
        private String address;
        private String contactEmail;

        public PublisherBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PublisherBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PublisherBuilder address(String address) {
            this.address = address;
            return this;
        }

        public PublisherBuilder contactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Publisher build() {
            return new Publisher(id, name, address, contactEmail);
        }
    }
}
