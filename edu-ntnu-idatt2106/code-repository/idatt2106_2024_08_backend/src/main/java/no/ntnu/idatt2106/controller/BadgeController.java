package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.model.Badge;
import no.ntnu.idatt2106.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-endpoints for Badge-related operations.
 * Includes methods to assign a badge to a user, get all badges rewarded to a user and get all badges.
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    /**
     * Get a badge by name.
     *
     * @param badgeName the name of the badge
     * @return the badge
     */
    @Operation(summary = "Get specified badge", description = "Retrieve information about the specified badge.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Badge was found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Badge.class))),
        @ApiResponse(responseCode = "404", description = "Badge was not found.", content = @Content)
    })
    @GetMapping("/badges/{badgeName}")
        public ResponseEntity<Badge> getBadgeByName(@PathVariable String badgeName) {
                Badge badge = badgeService.findByName(badgeName);
                if (badge == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(badge, HttpStatus.OK);
        }

    /**
     * Get all badges rewarded to a user by email.
     *
     * @param email the email of the user
     * @return a list of rewarded badges
     */
    @Operation(summary = "Get all badges for specified user",
        description = "Retrieves a list of all badges the user specified by an email address has been awarded.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and badges successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Badge.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/user/{email}/badges")
    public ResponseEntity<List<Badge>> findAllBadgesByUserEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(List.copyOf(badgeService.findAllBadgesByUserEmail(email)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all badges.
     *
     * @return a list of all badges
     */
    @Operation(summary = "Get all badges", description = "Retrieve a list of all badges.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All badges were successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Badge.class))),
    })
    @GetMapping("/badges")
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeService.findAllBadges();
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    /**
     * Get all badges a user has not yet been rewarded.
     *
     * @param email the email of the user
     * @return a list of missing badges
     */
    @Operation(summary = "Get all badges not rewarded to specified user",
        description = "Retrieves a list of all badges the user specified by an email address has not been awarded.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and badges successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Badge.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/user/{email}/missing_badges")
    public ResponseEntity<List<Badge>> findAllMissingBadgesByUserEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(List.copyOf(badgeService.findAllMissingBadgesByUserEmail(email)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}