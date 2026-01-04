package com.example.bookservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Gestion centralisée des erreurs de l’API
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Gestion des erreurs de type requête invalide (400)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException exception) {
        return Map.of("error", exception.getMessage());
    }

    /**
     * Gestion des conflits métier (409)
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflict(IllegalStateException exception) {
        return Map.of("error", exception.getMessage());
    }
}
