package com.project.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(404,exception.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Validation Failed");

        ErrorResponse errorResponse = new ErrorResponse(400,message);
        return ResponseEntity.status(400).body(errorResponse);

    }

    @ExceptionHandler(DuplicateRatingException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRating(DuplicateRatingException exception){
        ErrorResponse errorResponse = new ErrorResponse(409,exception.getMessage());
        return ResponseEntity.status(409).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(500,"An Unexpected error occured");
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(MovieNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(409,exception.getMessage());
        return ResponseEntity.status(409).body(errorResponse);
    }



}
