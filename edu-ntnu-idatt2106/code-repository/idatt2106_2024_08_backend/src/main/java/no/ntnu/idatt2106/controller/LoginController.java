package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.service.BadgeAwardService;
import no.ntnu.idatt2106.service.JavaMailSenderService;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-endpoint for login requests
 * Validates input and returns token if login is approved
 */
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSenderService javaMailSenderService;

    @Autowired
    BadgeAwardService badgeAwardService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Validates the user's login information and generates a token if login is approved.
     * Returns an HTTP response entity containing the token if login is approved,
     * or an error message with UNAUTHORIZED status if the username or password is wrong.
     *x
     * @param user The User object containing the email and password for login.
     * @return ResponseEntity<String> The HTTP response entity with the token or error message.
     */
    @Operation(summary = "Login and get JWT token", description = "Login with valid credentials returns a JWT token used to authenticate requests to the rest of the API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login was successful.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Encoded JWT token"))),
        @ApiResponse(responseCode = "401", description = "Credentials are wrong.",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Username or password is wrong.")))
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (loginService.validateUser(user.getEmail(), user.getPassword())) {
            badgeAwardService.awardLoginBadge(user.getEmail());

            return new ResponseEntity<>(loginService.generateToken(user.getEmail()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Username or password is wrong.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Checks the validity of a token.
     *
     * @return ResponseEntity<String> The HTTP response entity indicating the token's validity.
     */
    @Operation(summary = "Check validity of token.", description = "Endpoint for checking a token by triggering security filter.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "JWT token is still valid.", content = @Content),
        @ApiResponse(responseCode = "401", description = "JWT did not pass security filter and is invalid.", content = @Content),
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/check_token")
    public ResponseEntity<String> checkTokenValidity() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Resets the password for a user who has forgotten their password.
     *
     * @param email The email of the user who forgot their password.
     * @return ResponseEntity<String> An HTTP response entity indicating the result of the password reset operation.
     */
    @Operation(summary = "Forgot password",
        description = "Generates and sets new password for user specified by email and sends it to the registered email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The new password was successfully sent.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "New password sent to email email"))),
        @ApiResponse(responseCode = "404", description = "User was not found.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "No user found with email email"))),
    })
    @PostMapping(value = "/forgot_password", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        User u = userService.findByEmail(email);
        if (u != null) {
            String newPassword = loginService.generateNewPassword();
            String hashedPassword = passwordEncoder.encode(newPassword);
            u.setPassword(hashedPassword);
            userService.updateUser(u);
            javaMailSenderService.sendNewPasswordEmail(email, newPassword);
            return new ResponseEntity<>("New password sent to email " + email, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user found with email " + email, HttpStatus.NOT_FOUND);
        }
    }
}
