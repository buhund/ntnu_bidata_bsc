package no.ntnu.idatt2106.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/**
 * The login service layer is responsible for handling the logic for the User class log in requests.
 */
@Service
public class LoginService {

    public static final String KEY_STR = "rbhjnioøsgfbjmikoøaegfvjimkloøagtvjmikloædgtrfvmikloæ";
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(45);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BadgeAwardService badgeAwardService;

    /**
     * Validates the user's login information.
     *
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return true if the user is valid, false otherwise.
     */
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());

        if (passwordMatches) {
            updateLoginStreak(user);
            badgeAwardService.awardStreakBadge(user);
        }

        return passwordMatches;
    }

    private void updateLoginStreak(User user) {
        LocalDate today = LocalDate.now();
        if (user.getLastLoginDate() != null && user.getLastLoginDate().equals(today.minusDays(1))) {
            user.setLoginStreak(user.getLoginStreak() + 1);
        } else {
            user.setLoginStreak(1);
        }
        user.setLastLoginDate(today);
        userRepository.save(user);
    }

    /**
     * Generates a JSON Web Token (JWT) for authentication.
     *
     * @param email The email address of the user.
     * @return The generated JWT.
     */
    public String generateToken(final String email) {
        final Instant now = Instant.now();
        final Algorithm hmac512 = Algorithm.HMAC512(KEY_STR);
        return JWT.create()
            .withSubject(email)
            .withIssuer("idatt2106_team8_sparesti")
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
            .sign(hmac512);
    }

    /**
     * Method to generate a new random password.
     * Used when user has forgotten their original password.
     * Uses Passay password policy enforcement library.
     * Method is based on code from Baeldung: https://www.baeldung.com/java-generate-secure-password
     */
    public String generateNewPassword() {
        PasswordGenerator gen = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "ERROR_CODE";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule specialCharacterRule = new CharacterRule(specialChars);
        specialCharacterRule.setNumberOfCharacters(2);

        return gen.generatePassword(12, lowerCaseRule, upperCaseRule, digitRule, specialCharacterRule);
    }
}