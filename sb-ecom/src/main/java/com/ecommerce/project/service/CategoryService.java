package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
//    void createCategory(Category category);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long categoryID);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
