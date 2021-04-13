package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Category;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    List<Category> getCategories();

    Category getCategoryById(long id) throws NoSuchElementException;
}