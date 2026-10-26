package com.example.quotes.dto;

import com.example.quotes.model.Category;

public record CategoryDto(Integer id, String name) {
    public static CategoryDto from(Category c) {
        return new CategoryDto(c.getId(), c.getName());
    }
}