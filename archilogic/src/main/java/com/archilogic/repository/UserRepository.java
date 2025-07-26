/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.repository;

import com.archilogic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username, eagerly fetching the associated roles in a single query.
     * This is the primary method for loading user details for security purposes to avoid
     * LazyInitializationException on the roles collection.
     *
     * @param username The username to search for.
     * @return An {@link Optional} containing the User with roles if found.
     */
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * Checks if a user with the given username already exists.
     * This is crucial for user registration to ensure usernames are unique.
     *
     * @param username The username to check.
     * @return true if a user with the username exists, false otherwise.
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email already exists.
     * This ensures emails are unique across all accounts.
     *
     * @param email The email to check.
     * @return true if a user with the email exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
