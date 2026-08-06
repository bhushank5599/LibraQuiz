package com.lq.category.service;

import com.lq.category.dto.CategoryRequest;
import com.lq.category.dto.CategoryResponse;
import com.lq.category.dto.CategoryTreeResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    List<CategoryTreeResponse> getCategoryTree();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
