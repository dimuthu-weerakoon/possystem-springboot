package com.ijse.cmjd106.posSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.CategoryRequest;
import com.ijse.cmjd106.posSystem.dto.CategoryResponse;


@Service
public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest);

    void deleteCategory(Integer id);
}
