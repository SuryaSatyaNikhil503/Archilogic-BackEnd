/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.security.controller;

import com.archilogic.dto.JwtResponse;
import com.archilogic.dto.LoginRequest;
import com.archilogic.dto.MessageResponse;
import com.archilogic.dto.SignUpRequest;
import com.archilogic.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for user registration and login")
@RequiredArgsConstructor // Use constructor injection
public class AuthController {

    private final AuthService authService; // Make dependency final

    // ... (rest of the methods are unchanged)
    @Operation(summary = "Authenticate user and get token",
            description = "Provides a JWT token for a user with valid credentials.")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);

        if (jwtResponse.getRoles().contains("ROLE_ADMIN")) {
            jwtResponse.setLoginMessage("Login successful. Welcome, Admin!");
        } else if (jwtResponse.getRoles().contains("ROLE_USER")) {
            jwtResponse.setLoginMessage("Login successful. Welcome, User!");
        } else {
            jwtResponse.setLoginMessage("Login successful.");
        }

        return ResponseEntity.ok(jwtResponse);
    }

    @Operation(summary = "Register a new user",
            description = "Creates a new user account. Username and email must be unique.")
    @ApiResponse(responseCode = "200", description = "User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided or username/email already exists.")
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
