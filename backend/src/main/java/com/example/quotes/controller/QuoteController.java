package com.example.quotes.controller;

import com.example.quotes.dto.QuoteDto;
import com.example.quotes.model.Quote;
import com.example.quotes.repository.QuoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")

public class QuoteController {

    private final QuoteRepository quotes;
    public QuoteController(QuoteRepository quotes) {
        this.quotes = quotes;
    }

    @GetMapping
    public List<QuoteDto> getAll(@RequestParam(required = false) Integer categoryId) {
        List <Quote> result = (categoryId == null) ? quotes.findAll() : quotes.findByCategoryId(categoryId);

        return result.stream().map(QuoteDto::from).toList();
    }

    @GetMapping("/random")
    public ResponseEntity<QuoteDto> getRandom(@RequestParam(required = false) Integer categoryId) {
        Optional<Quote> quote = (categoryId == null) ? quotes.findRandom() : quotes.findRandomByCategoryId(categoryId);

        return quote.map(q -> ResponseEntity.ok(QuoteDto.from(q)))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getById(@PathVariable Integer id) {
        return quotes.findById(id)
                .map(q -> ResponseEntity.ok(QuoteDto.from(q)))
                .orElse(ResponseEntity.notFound().build());
    }
}
