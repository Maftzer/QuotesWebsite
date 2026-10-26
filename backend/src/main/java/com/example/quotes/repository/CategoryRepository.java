package com.example.quotes.repository;

import com.example.quotes.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
