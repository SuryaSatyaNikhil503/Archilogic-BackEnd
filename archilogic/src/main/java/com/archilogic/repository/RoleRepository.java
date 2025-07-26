/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.repository;

import com.archilogic.entity.ERole;
import com.archilogic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Finds a role by its name.
     * Spring Data JPA automatically implements this method based on its name.
     *
     * @param name The ERole enum value (e.g., ERole.ROLE_USER).
     * @return An Optional containing the Role if found, or an empty Optional otherwise.
     */
    Optional<Role> findByName(ERole name);
}

