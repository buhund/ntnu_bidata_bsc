package edu.backend.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component

public class JWTUtil {
  private static final String SECRET = "VerySuperSecretMissionImpossibleGoingPinkPantherIntoTheWildAndBeyondWithoutUsingUnderpantsSince1989.com";

  public static String generateToken(String username) {
    Algorithm algorithm = Algorithm.HMAC256(SECRET);
    String token = JWT.create()
        .withSubject(username)
        .sign(algorithm);
    return token;
  }

  public static boolean validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      JWT.require(algorithm)
          .build()
          .verify(token);
      return true;
    } catch (JWTVerificationException exception){
      // Invalid signature/claims
      return false;
    }
  }
}