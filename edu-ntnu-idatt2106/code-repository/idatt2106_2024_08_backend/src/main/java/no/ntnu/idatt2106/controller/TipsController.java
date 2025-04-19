package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.service.TipsService;
import no.ntnu.idatt2106.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * TipsController class is responsible for handling HTTP requests related to tips for users.
 * Takes in the email of a user and returns a tip based on the user's knowledge level.
 * User knowledge level is determined by the user's profile settings.
 * The knowledge level is used to determine which file to read tips from.
 * Interacts with the TipsService class.
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class TipsController {

    private static final Logger log = LoggerFactory.getLogger(TipsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TipsService tipsService;

    /**
     * Retrieves tips for a user based on their email.
     *
     * @param email The email address of the user.
     * @return A ResponseEntity containing the tips as a String. Returns HTTP status 200 (OK) if successful,
     *         otherwise returns HTTP status 404 (Not Found) if no user is found with the given email or
     *         HTTP status 500 (Internal Server Error) if there is an error retrieving the tips.
     */
    @Operation(summary = "Get financial tips", description = "Get financial tips at the knowledge level of the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The operation was successful.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "User not found.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "No user found"))),
        @ApiResponse(responseCode = "500", description = "An error occurred while retrieving tips.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Error retrieving tips for user: user")))
    })
    @GetMapping("/tips/{email}")
    public ResponseEntity<String> getTipsForUser(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            log.info("No user found with email: {}", email);
            return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);        }

        try {
            String tip = tipsService.getTipsForUser(user);
            return new ResponseEntity<>(tip, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error retrieving tips for user: {}", email, e);
            return new ResponseEntity<>("Error retrieving tips", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
