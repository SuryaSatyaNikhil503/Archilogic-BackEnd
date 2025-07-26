/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for the response after a successful authentication.")
public class JwtResponse {

    @Schema(description = "The JSON Web Token for authentication.", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwicm9sZXMi...")
    private String token;

    @Schema(description = "The type of the token.", example = "Bearer")
    @Builder.Default // IMPORTANT: Ensures the builder uses this default value
    private String type = "Bearer";

    @Schema(description = "The unique ID of the authenticated user.", example = "1")
    private Long id;

    @Schema(description = "The username of the authenticated user.", example = "johndoe")
    private String username;

    @Schema(description = "The email of the authenticated user.", example = "johndoe@example.com")
    private String email;

    @Schema(description = "A list of roles assigned to the user.", example = "[\"ROLE_USER\"]")
    private List<String> roles;

    @Schema(description = "A friendly message for the client, useful for testing.", example = "Login successful. Welcome, Admin!")
    private String loginMessage;
}