package com.marco.shop.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.access-token.expiration}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
    ) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getUsername(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public LocalDateTime getAccessExpirationTime() {
        return LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION / 1000);
    }

    public LocalDateTime getRefreshExpirationTime() {
        return LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION / 1000);
    }
}
