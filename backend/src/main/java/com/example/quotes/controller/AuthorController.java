package com.example.quotes.controller;

import com.example.quotes.dto.AuthorDto;
import com.example.quotes.repository.QuoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final QuoteRepository quotes;

    public AuthorController(QuoteRepository quotes) {
        this.quotes = quotes;
    }

    @GetMapping
    public List<AuthorDto> getAll(@RequestParam(required = false) Integer limit) {
        Pageable pageable = (limit == null) ? Pageable.unpaged() : PageRequest.of(0, Math.max(1, limit));
        return quotes.findAuthorsWithCount(pageable);
    }



}
