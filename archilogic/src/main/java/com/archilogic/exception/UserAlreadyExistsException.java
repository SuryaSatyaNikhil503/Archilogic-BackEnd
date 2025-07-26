/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to register a user with a username or email
 * that is already taken. Results in a 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}