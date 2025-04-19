package no.ntnu.idatt2106.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static no.ntnu.idatt2106.service.LoginService.KEY_STR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

  private static final String TEST_EMAIL = "tester@test.mail";
  private static final String TEST_PASSWORD = "password";

  @MockBean
  UserRepository userRepository;

  @Autowired
  LoginService loginService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  void testReturnsTrueOnValidUserDetails() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
    user.setFirstName("Hans");
    user.setLastName("Hansen");

    Mockito.when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(user);

    assertTrue(loginService.validateUser(TEST_EMAIL, TEST_PASSWORD));
    Mockito.verify(userRepository, Mockito.times(2)).findByEmail(TEST_EMAIL);
  }

  @Test
  void testReturnsFalseOnInvalidUserDetails() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
    user.setFirstName("Hans");
    user.setLastName("Hansen");

    Mockito.when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(user);

    assertFalse(loginService.validateUser(TEST_EMAIL, "bar"));
    Mockito.verify(userRepository, Mockito.times(1)).findByEmail(TEST_EMAIL);
  }

  @Test
  void testGenerateTokenGeneratesValidJWTToken() {
    final Algorithm hmac512 = Algorithm.HMAC512(KEY_STR);
    JWTVerifier verifier = JWT.require(hmac512).build();

    String token = loginService.generateToken(TEST_EMAIL);

    assertEquals("idatt2106_team8_sparesti", verifier.verify(token).getIssuer());
    assertEquals(TEST_EMAIL, verifier.verify(token).getSubject());
  }

  @Test
  void testGenerateNewPasswordGeneratesAPasswordWithTheExpectedStructure() {
    String password = loginService.generateNewPassword();
    assertEquals(12, password.length());
    AtomicInteger lowerCaseCharacter = new AtomicInteger();
    AtomicInteger upperCaseCharacter = new AtomicInteger();
    AtomicInteger specialCharacter = new AtomicInteger();
    List<Integer> specialChars = "!@#$%^&*()_+".chars().boxed().toList();
    AtomicInteger digit = new AtomicInteger();
    password.chars().forEach(character -> {
      if (specialChars.contains(character)) {
        specialCharacter.getAndIncrement();
      } else if (Character.isDigit(character)) {
        digit.getAndIncrement();
      } else if (Character.isUpperCase(character)) {
        upperCaseCharacter.getAndIncrement();
      } else if (Character.isLowerCase(character)) {
        lowerCaseCharacter.getAndIncrement();
      }
    });
    assertThat(lowerCaseCharacter.get(), greaterThanOrEqualTo(2));
    assertThat(upperCaseCharacter.get(), greaterThanOrEqualTo(2));
    assertThat(digit.get(), greaterThanOrEqualTo(2));
    assertThat(specialCharacter.get(), greaterThanOrEqualTo(2));
  }
}
