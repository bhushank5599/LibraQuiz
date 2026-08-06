package com.lq.category.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeResponse {

    private Long id;
    private String name;
    private String description;
    private List<CategoryTreeResponse> subCategories = new ArrayList<>();

    public CategoryTreeResponse() {
    }

    public CategoryTreeResponse(Long id, String name, String description, List<CategoryTreeResponse> subCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        if (subCategories != null) {
            this.subCategories = subCategories;
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryTreeResponse> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryTreeResponse> subCategories) {
        this.subCategories = subCategories;
    }

    public static CategoryTreeResponseBuilder builder() {
        return new CategoryTreeResponseBuilder();
    }

    public static class CategoryTreeResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private List<CategoryTreeResponse> subCategories = new ArrayList<>();

        public CategoryTreeResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryTreeResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryTreeResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryTreeResponseBuilder subCategories(List<CategoryTreeResponse> subCategories) {
            this.subCategories = subCategories;
            return this;
        }

        public CategoryTreeResponse build() {
            return new CategoryTreeResponse(id, name, description, subCategories);
        }
    }
}
