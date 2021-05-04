package com.memegenerator.backend.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.domain.service.impl.CategoryServiceImpl;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class CategoryControllerTests {

    @Autowired
    private CategoryController controller;

    @MockBean
    private CategoryServiceImpl categoryService;

    @Autowired
	private MockMvc mockMvc;	

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "unittest", roles = { "User" })
    public void returns_categories() throws Exception {

        int iterations = new Random().nextInt(9) + 1;
        List<Category> categoryList = new ArrayList<Category>();
        String mockTitle = "title";

        for (int i = 0; i < iterations; i++) {
            categoryList.add(new Category(mockTitle));
        }

        when(categoryService.getCategories()).thenReturn(categoryList);

        var mvcResult = this.mockMvc.perform(get("/category/")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");
      
        assertThat(title).isEqualTo(mockTitle);
        verify(categoryService, times(1)).getCategories();
    }
}