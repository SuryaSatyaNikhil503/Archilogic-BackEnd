/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Schema(description = "Data Transfer Object for user registration.")
public class SignUpRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Schema(description = "The desired username for the new account.", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "First name is required.")
    @Size(max = 50)
    @Schema(description = "The user's first name.", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String first_name;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50)
    @Schema(description = "The user's last name.", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String last_name;

    @NotBlank(message = "Email is required.")
    @Size(max = 100)
    @Email(message = "Email should be in a valid format.")
    @Schema(description = "The user's email address. Must be unique.", example = "johndoe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 15)
    @Schema(description = "The user's phone number.", example = "+15551234567", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone_number;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters.")
    @Schema(description = "The desired password for the new account. Must be at least 8 characters.", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "A set of roles to assign to the user (e.g., 'admin', 'user'). If not provided, defaults to 'user'.", example = "[\"user\"]")
    private Set<String> role;
}
