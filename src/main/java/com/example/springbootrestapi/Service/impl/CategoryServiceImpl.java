package com.example.springbootrestapi.Service.impl;

import com.example.springbootrestapi.DTO.CategoryDTO;
import com.example.springbootrestapi.Entity.Category;
import com.example.springbootrestapi.Exception.ResourceNotFoundException;
import com.example.springbootrestapi.Repository.CategoryRepository;
import com.example.springbootrestapi.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category newCat=modelMapper.map(categoryDTO,Category.class);
        categoryRepository.save(newCat);
        return modelMapper.map(newCat,CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryByID(long categoryId) {
        Category newCat=categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(newCat,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryId);

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);
    }
}
