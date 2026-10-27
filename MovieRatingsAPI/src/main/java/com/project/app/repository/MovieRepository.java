package com.project.app.repository;

import com.project.app.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitleIgnoreCase(String title);
}
