package com.utility.utilityAPI.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtVerifier {
    public static boolean verifyToken(String token, String userName) {
        System.out.println("About to verify the token.. all the best !");
        try {
            final String SECRET_KEY = "utility_api"; // Replace with your own secret key
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            String payLoadUserName= jwt.getSubject();

            System.out.println("Token verified. Subject: " + jwt.getSubject());
            System.out.println("Issued At: " + jwt.getIssuedAt());
            System.out.println("Expires At: " + jwt.getExpiresAt());

            return Objects.equals(payLoadUserName, userName);

            // Token verification succeeded
        } catch (JWTVerificationException e) {
            // Token verification failed
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }
}
