package com.project.app;

import com.project.app.model.Movie;
import com.project.app.model.Rating;
import com.project.app.repository.MovieRepository;
import com.project.app.repository.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public DataLoader(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void run(String... args) {
        Movie inception = movieRepository.save(new Movie("Inception", "sci-fi"));
        Movie darkKnight = movieRepository.save(new Movie("The Dark Knight", "action"));
        movieRepository.save(new Movie("Interstellar", "sci-fi"));
        movieRepository.save(new Movie("Parasite", "thriller"));
        movieRepository.save(new Movie("The Shawshank Redemption", "drama"));

        ratingRepository.save(new Rating(inception, 5, "Alice"));
        ratingRepository.save(new Rating(darkKnight, 4, "Bob"));
    }
}