package com.example.quotes.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    protected Category() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
}
