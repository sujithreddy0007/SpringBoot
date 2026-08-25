package com.ecommerce.project.controller;
import java.util.*;
import com.ecommerce.project.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController {
    private List<Category> categories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories(){
        return categories;
    }

}
