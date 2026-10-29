package com.example.quotes.controller;

import com.example.quotes.dto.CategoryDto;
import com.example.quotes.repository.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<CategoryDto> getAll(@RequestParam(required = false) Integer limit) {
        Pageable pageable = (limit == null) ? Pageable.unpaged() : PageRequest.of(0, Math.max(1, limit));
        return categories.findAllWithCount(pageable);
    }
}

