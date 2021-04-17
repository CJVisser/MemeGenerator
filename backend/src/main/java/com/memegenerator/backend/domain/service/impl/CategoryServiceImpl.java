package com.memegenerator.backend.domain.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.repository.CategoryRepository;
import com.memegenerator.backend.domain.service.CategoryService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * @return List<Category>
     */
    public List<Category> getCategories() {

        return categoryRepository.findAll();
    }

    /**
     * @param categoryId
     * @return Category
     * @throws NoSuchElementException
     */
    public Category getCategoryById(long categoryId) throws NoSuchElementException {

        return categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Category not found"));
    }
}