package com.auth.service.utils;

import com.auth.service.entity.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.security.core.GrantedAuthority;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class JwtGenerator {

    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    private static final String ISSUER = "auth-service-api";

    private static final Date issuedDate = Date.from(Instant.now());

    private static final Date expiredDate = Date.from(Instant.now(Clock.fixed(Instant.parse("2025-08-25T13:00:00Z"), ZoneId.of("UTC"))));

    public static String generateNotExpiredJWT(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(issuedDate)
                    .withSubject(user.getUsername())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Error generating token: ", exception);
        }
    }

    public static String generateExpiredJWT(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(issuedDate)
                    .withExpiresAt(expiredDate)
                    .withSubject(user.getUsername())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Error generating token: ", exception);
        }
    }

}
