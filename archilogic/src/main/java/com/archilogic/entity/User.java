/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a user in the application.
 * <p>
 * This entity is mapped to the "users" table in the database and implements
 * Spring Security's UserDetails for seamless authentication and authorization.
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a user account in the system.")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier for the user.", example = "1")
    private Long id;

    @NotBlank(message = "Username cannot be blank.")
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "The unique username for the user.", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "First name cannot be blank.")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    @Schema(description = "The user's first name.", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String first_name;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    @Schema(description = "The user's last name.", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String last_name;

    @NotBlank(message = "Email cannot be blank.")
    @Size(max = 100)
    @Email(message = "Email should be a valid format.")
    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "The unique email address for the user.", example = "johndoe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Phone number cannot be blank.")
    @Size(max = 15)
    @Column(length = 15)
    @Schema(description = "The user's phone number.", example = "+15551234567", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone_number;

    @NotBlank(message = "Password cannot be blank.")
    @Size(max = 255) // Length should accommodate a hashed password
    @Column(nullable = false)
    @Schema(description = "The user's hashed password.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    /**
     * The roles assigned to the user.
     * Fetched lazily to avoid the N+1 select problem and improve performance.
     * The relationship is managed through the "user_roles" join table.
     */
    @ManyToMany(fetch = FetchType.LAZY) // CRITICAL: Changed from EAGER to LAZY
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    @Schema(description = "Set of roles assigned to the user.")
    private Set<Role> roles = new HashSet<>();

    // --- UserDetails Implementation ---

    /**
     * Returns the authorities granted to the user. Converts the user's roles
     * into a collection of SimpleGrantedAuthority.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    // Lombok's @Getter provides the getPassword() and getUsername() methods required by UserDetails.

    @Override
    public boolean isAccountNonExpired() {
        return true; // Or add a field to manage this state
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or add a field to manage this state
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or add a field to manage this state
    }

    @Override
    public boolean isEnabled() {
        return true; // Or add a field to manage this state
    }

    // --- equals, hashCode, and toString for Robustness ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // Use a unique, non-null business key for equality.
        // The ID is not reliable before the entity is persisted.
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        // Use the same business key for the hash code to ensure consistency with equals().
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}