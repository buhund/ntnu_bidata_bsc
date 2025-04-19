package com.example.demo.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;

@Service
@CrossOrigin
public class TokenService {

    private static final long TOKEN_EXPIRY_TIME = 900_000;
    Algorithm encoder;
    JWTVerifier verifier;

    String secret = "secret";


    public TokenService() {
        encoder = Algorithm.HMAC512(secret);
        verifier = JWT.require(encoder).build();
    }
    public DecodedJWT decodeToken(String token) {
        try {
            return verifier.verify(token);
        } catch (final JWTVerificationException verificationEx) {
            return null;
        }
    }

    public String createToken(String username) {
        final Instant now = Instant.now();

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(TOKEN_EXPIRY_TIME))
                .sign(encoder);
    }

    public boolean isTokenExpired(DecodedJWT token) {
        final Instant now = Instant.now();

        return now.isAfter(token.getExpiresAtAsInstant());
    }
}
