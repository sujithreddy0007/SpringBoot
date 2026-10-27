package com.project.app.exception;

public class DuplicateRatingException extends RuntimeException {
    public DuplicateRatingException(String reviewer, Long movieId) {
        super("Reviewer '" + reviewer + "' has already rated movie with id: " + movieId);
    }
}
