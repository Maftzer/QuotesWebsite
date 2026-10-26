package com.example.quotes.dto;

import com.example.quotes.model.Quote;

public record QuoteDto(Integer id, String text, String author, String category) {
    public static QuoteDto from(Quote q) {
        return new QuoteDto(q.getId(), q.getText(), q.getAuthor(), q.getCategory().getName());
    }
}
