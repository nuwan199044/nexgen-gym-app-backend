package com.enzith.nexgen.config;

import com.enzith.nexgen.common.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.enzith.nexgen.common.AppConstants.*;

@Component
public class JwtProvider {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> claims = Map.of(
                EMAIL_CLAIM, authentication.getName(),
                AUTHORITIES_CLAIM, roles,
                ISSUED_AT, new Date(),
                EXPIRATION, new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS * 1000)
        );

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}
