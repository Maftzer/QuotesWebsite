package com.example.quotes.repository;

import com.example.quotes.dto.AuthorDto;
import com.example.quotes.model.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    
    List<Quote> findByCategoryId(Integer categoryId);

    List<Quote> findByAuthorIgnoreCase(String author);

    @Query("""
           SELECT q FROM Quote q
           WHERE LOWER(q.text) LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(q.author) LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(q.category.name) LIKE LOWER(CONCAT('%', :term, '%'))
           """)
    
    List<Quote> search(@Param("term") String term);
    
    @Query("""
           SELECT new com.example.quotes.dto.AuthorDto(q.author, COUNT(q.id))
           FROM Quote q
           WHERE q.author IS NOT NULL
           GROUP BY q.author
           ORDER BY COUNT(q.id) DESC, q.author ASC
           """)
    List<AuthorDto> findAuthorsWithCount(Pageable pageable);





}
