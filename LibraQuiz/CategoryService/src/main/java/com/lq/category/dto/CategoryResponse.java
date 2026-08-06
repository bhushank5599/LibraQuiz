package com.lq.category.dto;

public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private String parentName;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, String description, Long parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.parentName = parentName;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public static CategoryResponseBuilder builder() {
        return new CategoryResponseBuilder();
    }

    public static class CategoryResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private Long parentId;
        private String parentName;

        public CategoryResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryResponseBuilder parentId(Long parentId) {
            this.parentId = parentId;
            return this;
        }

        public CategoryResponseBuilder parentName(String parentName) {
            this.parentName = parentName;
            return this;
        }

        public CategoryResponse build() {
            return new CategoryResponse(id, name, description, parentId, parentName);
        }
    }
}
