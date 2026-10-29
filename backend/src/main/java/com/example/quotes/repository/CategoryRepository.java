package com.example.quotes.repository;

import com.example.quotes.model.Category;
import com.example.quotes.dto.CategoryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
           SELECT new com.example.quotes.dto.CategoryDto(c.id, c.name, COUNT(q.id))
           FROM Category c
           LEFT JOIN Quote q ON q.category = c
           GROUP BY c.id, c.name
           ORDER BY COUNT(q.id) DESC, c.name ASC
           """)
           
    List<CategoryDto> findAllWithCount(Pageable pageable);
}
