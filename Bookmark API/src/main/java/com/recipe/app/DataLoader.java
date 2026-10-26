package com.recipe.app;

import com.recipe.app.model.Recipe;
import com.recipe.app.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    public DataLoader(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) {
        recipeRepository.save(new Recipe("Spaghetti Carbonara", "pasta", 5));
        recipeRepository.save(new Recipe("Caesar Salad", "salad", 4));
        recipeRepository.save(new Recipe("Chicken Tikka Masala", "curry", 5));
        recipeRepository.save(new Recipe("Greek Salad", "salad", 3));
        recipeRepository.save(new Recipe("Penne Arrabiata", "pasta", 4));
    }
}