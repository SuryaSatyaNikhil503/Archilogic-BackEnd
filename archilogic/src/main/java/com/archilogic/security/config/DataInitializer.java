/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.security.config;

import com.archilogic.entity.ERole;
import com.archilogic.entity.Role;
import com.archilogic.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Component that runs on application startup to seed the database
 * with essential data, such as user roles.
 */
@Component
@RequiredArgsConstructor
@Slf4j // Using SLF4J for logging
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    /**
     * This method will be executed on application startup.
     * It checks if roles exist in the database and creates them if they don't.
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(String... args) {
        log.info("DataInitializer is running...");

        // Check if roles are already seeded
        if (roleRepository.count() == 0) {
            log.info("No roles found in the database. Seeding initial roles...");
            Arrays.stream(ERole.values()).forEach(eRole -> {
                roleRepository.save(new Role(eRole));
                log.info("Saved role to database: {}", eRole.name());
            });
            log.info("Role seeding complete.");
        } else {
            log.info("Roles already exist in the database. No seeding needed.");
        }
    }
}
