/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.security.jwt;

import com.archilogic.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * Provides methods for generating, parsing, and validating JWTs.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${archilogic.app.jwtSecret}")
    private String jwtSecret;

    @Value("${archilogic.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private Key key;

    /**
     * Initializes the signing key after the component is constructed.
     * This is more efficient than creating the key on every JWT operation.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT for a given authenticated user.
     * The user's roles are embedded as a custom claim within the token.
     *
     * @param authentication The Spring Security Authentication object.
     * @return A signed JWT string.
     */
    public String generateJwtToken(Authentication authentication) {
        // We use our custom User entity to get more details if needed
        User userPrincipal = (User) authentication.getPrincipal();

        // Extract roles from authorities
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles) // Add roles as a custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key) // Use the modern, non-deprecated signWith method
                .compact();
    }

    /**
     * Extracts the username from a given JWT.
     *
     * @param token The JWT string.
     * @return The username contained in the token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validates the integrity and expiration of a JWT.
     *
     * @param authToken The JWT string to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) key)
                    .build()
                    .parse(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
