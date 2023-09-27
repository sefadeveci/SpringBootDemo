package com.example.springbootrestapi.Service;

import com.example.springbootrestapi.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategoryByID(long id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);

}
