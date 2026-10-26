package com.example.quotes.repository;

import com.example.quotes.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    
    List<Quote> findByCategoryId(Integer categoryId);

    @Query(value = "SELECT * FROM quotes ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> findRandom();

    @Query(value = "SELECT * FROM quotes WHERE category_id = :categoryId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> findRandomByCategoryId(@Param("categoryId") Integer categoryId);
}
