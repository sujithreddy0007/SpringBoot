package com.project.app.controller;

import com.project.app.model.Rating;
import com.project.app.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies/{movieId}/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> addRating(
            @PathVariable Long movieId,
            @Valid @RequestBody Rating rating) {
        return ResponseEntity.status(201).body(ratingService.addRating(movieId, rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(@PathVariable Long movieId) {
        return ResponseEntity.ok(ratingService.getRatingsForMovie(movieId));
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long movieId) {
        double average = ratingService.getAverageRating(movieId);
        Map<String, Object> response = Map.of(
                "movieId", movieId,
                "average", Math.round(average * 10.0) / 10.0
        );
        return ResponseEntity.ok(response);
    }
}