package com.project.app.service;

import com.project.app.exception.DuplicateRatingException;
import com.project.app.exception.MovieNotFoundException;
import com.project.app.model.Movie;
import com.project.app.model.Rating;
import com.project.app.repository.MovieRepository;
import com.project.app.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
    }

    public Rating addRating(Long movieId, Rating rating) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        if (ratingRepository.existsByMovieIdAndReviewer(movieId, rating.getReviewer())) {
            throw new DuplicateRatingException(rating.getReviewer(), movieId);
        }

        rating.setMovie(movie);
        return ratingRepository.save(rating);
    }

    public List<Rating> getRatingsForMovie(Long movieId) {
        return ratingRepository.findByMovieId(movieId);
    }

    public double getAverageRating(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }
        return ratingRepository.findAverageStarsByMovieId(movieId)
                .orElse(0.0);
    }
}