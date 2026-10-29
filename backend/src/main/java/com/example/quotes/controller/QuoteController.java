package com.example.quotes.controller;

import com.example.quotes.dto.QuoteDto;
import com.example.quotes.model.Quote;
import com.example.quotes.repository.QuoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/quotes")

public class QuoteController {

    private final QuoteRepository quotes;

    public QuoteController(QuoteRepository quotes) {
        this.quotes = quotes;
    }

    
    @GetMapping
    public List<QuoteDto> getAll(@RequestParam(required = false) Integer categoryId,
                                 @RequestParam(required = false) String author,
                                 @RequestParam(required = false) String search) {
        List <Quote> result;
        if (search != null && !search.isBlank()){
            result = quotes.search(search.trim());
        } else if (author != null && !author.isBlank()) {
            result = quotes.findByAuthorIgnoreCase(author.trim());
        } else if (categoryId != null) {
            result = quotes.findByCategoryId(categoryId);
        } else {
            result = quotes.findAll();
        }

        return result.stream().map(QuoteDto::from).toList();
    }

    @GetMapping("/today")
    public ResponseEntity<QuoteDto> getToday() {

        long count = quotes.count();

        if(count == 0) {
            return ResponseEntity.notFound().build();
        }
        
        int index = (int) (LocalDate.now(). toEpochDay() % count);

        Quote quote = quotes.findAll(PageRequest.of(index, 1, Sort.by("id"))).getContent().get(0);

        return ResponseEntity.ok(QuoteDto.from(quote));

    }









    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getById(@PathVariable Integer id) {
        return quotes.findById(id)
                .map(q -> ResponseEntity.ok(QuoteDto.from(q)))
                .orElse(ResponseEntity.notFound().build());
    }
}
