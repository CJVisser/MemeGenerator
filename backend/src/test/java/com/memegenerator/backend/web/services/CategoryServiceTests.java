package com.memegenerator.backend.web.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.repository.CategoryRepository;
import com.memegenerator.backend.domain.service.CategoryService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void gets_categories() {

        int generations = new Random().nextInt(9) + 1;
        List<Category> categoryList = new ArrayList<Category>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            categoryList.add(new Category(mockTitle));
        }

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getCategories();

        assertEquals(result.size(), generations);
        verify(categoryRepository, times(1)).findAll();

    }
}