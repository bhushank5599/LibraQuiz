package com.lq.category.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    public Category() {
    }

    public Category(Long id, String name, String description, Category parentCategory, List<Category> subCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public static class CategoryBuilder {
        private Long id;
        private String name;
        private String description;
        private Category parentCategory;
        private List<Category> subCategories = new ArrayList<>();

        public CategoryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryBuilder parentCategory(Category parentCategory) {
            this.parentCategory = parentCategory;
            return this;
        }

        public CategoryBuilder subCategories(List<Category> subCategories) {
            this.subCategories = subCategories;
            return this;
        }

        public Category build() {
            return new Category(id, name, description, parentCategory, subCategories);
        }
    }
}
