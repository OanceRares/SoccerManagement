package com.footballteams.footballteamorganizer.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;  // Secure key from application.properties

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;  // JWT expiration time in milliseconds

    // Extract JWT token from the Authorization header
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " prefix
        }
        return null;
    }

    public String generateTokenFromEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Signing with secure key
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            JwtParserBuilder parserBuilder = Jwts.parser();
            JwtParser parser = parserBuilder
                    .setSigningKey(getSigningKey())
                    .build();

            Claims claims = parser.parseClaimsJws(token).getPayload();
            return claims.getSubject();
        } catch (io.jsonwebtoken.JwtException e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            return null;
        }
    }

    // Validate the JWT token
    public boolean validateJwtToken(String authToken) {
        try {
            JwtParserBuilder parserBuilder = Jwts.parser();
            JwtParser parser = parserBuilder
                    .setSigningKey(getSigningKey())  // Set the signing key for validation
                    .build();

            parser.parseClaimsJws(authToken);  // Parse and validate the JWT
            return true;  // If no exception is thrown, the token is valid
        } catch (io.jsonwebtoken.JwtException e) {  // Catch any JWT validation exceptions
            logger.error("JWT validation failed: {}", e.getMessage());
        }
        return false;  // If any exception occurs, the token is invalid
    }

    // Get the signing key from the secret
    private Key getSigningKey() {
        // Ensure your jwtSecret is sufficiently long for HS512 (minimum 512 bits / 64 bytes)
        // It's recommended to store your secret in Base64 encoded format
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());  // Decoding the secret and getting the key
    }
}
