package com.example.productapi.config;
/*
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;*/

//@Component
public class JwtUtil {

    /*private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private String secretKey = "secret";

    public String extractUsername(String token) {
        logger.debug("Extracting username from token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        logger.debug("Extracting expiration date from token: {}", token);
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        logger.debug("Extracting all claims from token: {}", token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        logger.debug("Checking if token is expired: {}", token);
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        logger.debug("Validating token for username: {}", username);
        return (username != null && !isTokenExpired(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        logger.debug("Validating token for user: {}", username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(UserDetails userDetails) {
        logger.debug("Generating token for user: {}", userDetails.getUsername());
        return createToken(userDetails.getUsername());
    }

    private String createToken(String subject) {
        logger.debug("Creating token for subject: {}", subject);
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String extractToken(String header) {
        logger.debug("Extracting token from header: {}", header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }*/
}