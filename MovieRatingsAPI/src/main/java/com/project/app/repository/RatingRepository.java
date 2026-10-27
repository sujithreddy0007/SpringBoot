package com.project.app.repository;

import com.project.app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByMovieId(Long movieId);

    boolean existsByMovieIdAndReviewer(Long movieId, String reviewer);

     @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.movie.id = :movieId")
     Optional<Double> findAverageStarsByMovieId(@Param("movieId") Long movieId);
}
