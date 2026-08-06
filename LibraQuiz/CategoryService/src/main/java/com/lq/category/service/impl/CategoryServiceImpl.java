package com.lq.category.service.impl;

import com.lq.category.dto.CategoryRequest;
import com.lq.category.dto.CategoryResponse;
import com.lq.category.dto.CategoryTreeResponse;
import com.lq.category.entity.Category;
import com.lq.category.repository.CategoryRepository;
import com.lq.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + request.getParentId()));
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategory(parent)
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        return rootCategories.stream()
                .map(this::toTreeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + request.getParentId()));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }

        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .parentName(category.getParentCategory() != null ? category.getParentCategory().getName() : null)
                .build();
    }

    private CategoryTreeResponse toTreeResponse(Category category) {
        List<CategoryTreeResponse> subTree = category.getSubCategories().stream()
                .map(this::toTreeResponse)
                .collect(Collectors.toList());

        return CategoryTreeResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .subCategories(subTree)
                .build();
    }
}
