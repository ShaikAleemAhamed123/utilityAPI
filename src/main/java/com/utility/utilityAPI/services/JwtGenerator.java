package com.utility.utilityAPI.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtGenerator {
    private static final String SECRET_KEY = "utility_api"; // Replace with your own secret key

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 6L *30*24*3600000); // Token validity: 6 Months

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }
}
