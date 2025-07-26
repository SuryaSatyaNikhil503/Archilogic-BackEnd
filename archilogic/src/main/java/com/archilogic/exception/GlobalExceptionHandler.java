/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.exception;

import com.archilogic.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * A global exception handler to catch and format exceptions across the entire application.
 * This ensures consistent and clean error responses for the API clients.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions for when a user tries to register with an existing username or email.
     *
     * @param ex The caught UserAlreadyExistsException.
     * @return A ResponseEntity with a 409 Conflict status.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Handles exceptions for when a required resource like a Role is not found.
     * This typically indicates a server-side configuration issue.
     *
     * @param ex The caught ResourceNotFoundException.
     * @return A ResponseEntity with a 500 Internal Server Error status.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Handles validation exceptions thrown by @Valid on DTOs.
     *
     * @param ex The caught MethodArgumentNotValidException.
     * @return A ResponseEntity with a 400 Bad Request status and a map of field errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "One or more validation errors occurred.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    /**
     * A fallback handler for any other unhandled exceptions.
     * This prevents leaking stack traces to the client.
     *
     * @param ex The caught exception.
     * @return A ResponseEntity with a 500 Internal Server Error status.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal server error occurred" );
    }
}
