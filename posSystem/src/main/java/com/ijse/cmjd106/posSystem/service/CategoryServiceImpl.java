package com.ijse.cmjd106.posSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.CategoryRequest;
import com.ijse.cmjd106.posSystem.dto.CategoryResponse;
import com.ijse.cmjd106.posSystem.model.Category;
import com.ijse.cmjd106.posSystem.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();
        Category createdCategory = categoryRepository.save(category);
        return maptoCategoryResponse(createdCategory);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        ArrayList<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse categoryResponse = maptoCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return maptoCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest) {
        Category existinCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existinCategory.setCategoryName(categoryRequest.getCategoryName());
        Category updatedCategory = categoryRepository.save(existinCategory);
        return maptoCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
       categoryRepository.deleteById(id);
    }

    private CategoryResponse maptoCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }


}
