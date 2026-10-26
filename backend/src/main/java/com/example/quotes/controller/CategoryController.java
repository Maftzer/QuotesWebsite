package com.example.quotes.controller;

import com.example.quotes.dto.CategoryDto;
import com.example.quotes.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController 
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categories;

    public CategoryController(CategoryRepository categories) {
        this.categories = categories;
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categories.findAll(Sort.by("name")).stream()
                .map(CategoryDto::from)
                .toList();
    }
}

