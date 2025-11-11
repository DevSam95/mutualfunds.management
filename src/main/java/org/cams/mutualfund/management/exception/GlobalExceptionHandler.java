package org.cams.mutualfund.management.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(ValidationException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Validation failed");
        errors.put("cause", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
