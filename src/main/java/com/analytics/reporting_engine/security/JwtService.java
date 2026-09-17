package com.analytics.reporting_engine.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.analytics.reporting_engine.entity.User;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET_KEY =
            "my-super-secret-key-for-analytics-project-2026";

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith( getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        String email = extractEmail(token);

        return email.equals(userDetails.getUsername());
    }
}