package com.yusei.neuralbank.shared.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(
                OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        ));
    }

    public record ApiError(OffsetDateTime timestamp, int status, String message) {
    }
}
