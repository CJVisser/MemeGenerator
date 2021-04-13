package com.memegenerator.backend.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.domain.service.CategoryService;
import com.memegenerator.backend.web.dto.CategoryDto;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    /**
     * @return ResponseEntity<List<CategoryDto>>
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        List<Category> categories = categoryService.getCategories();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        return new ResponseEntity<List<CategoryDto>>(categoryDtos, HttpStatus.OK);
    }
}