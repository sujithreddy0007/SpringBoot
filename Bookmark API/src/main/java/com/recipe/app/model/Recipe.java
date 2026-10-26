package com.recipe.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is requrired")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 1,message = "Rating must be at least 1")
    @Max(value = 5,message = "Rating must be at most 5")
    private int rating;

    public Recipe() {}

    public Recipe(String title, String category, int rating) {
        this.title = title;
        this.category = category;
        this.rating = rating;
    }

    // --- getters and setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}