/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for user login.")
public class LoginRequest {

    @NotBlank(message = "Username is required.")
    @Schema(description = "The user's username.", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password is required.")
    @Schema(description = "The user's password.", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}