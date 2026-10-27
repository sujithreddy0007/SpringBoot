package com.project.app.service;

import com.project.app.exception.MovieAlreadyExistsException;
import com.project.app.exception.MovieNotFoundException;
import com.project.app.model.Movie;
import com.project.app.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        if(movieRepository.existsByTitleIgnoreCase(movie.getTitle())) {
            throw new MovieAlreadyExistsException(movie.getTitle());
        }
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}