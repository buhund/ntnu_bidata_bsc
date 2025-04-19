package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class responsible for REST-endpoints for fetching and saving goals
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class ChallengeController {

    @Autowired
    ChallengeService challengeService;

    @Autowired
    GoalService goalService;

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankTransactionService bankTransactionService;

    /**
     * Retrieves a challenge by its ID.
     *
     * @param id The ID of the challenge.
     * @return ResponseEntity<Challenge> The response entity containing the challenge if found,
     * or HttpStatus.NOT_FOUND if the challenge does not exist.
     */
    @Operation(summary = "Get specified challenge", description = "Retrieve all information about a specified challenge.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Challenge was found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "404", description = "Challenge was not found.", content = @Content)
    })
    @GetMapping("/challenges/{id}")
    public ResponseEntity<Challenge> getChallengeById(@PathVariable Long id) {
        Challenge challenge = challengeService.findById(id);

        if (challenge != null) {
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all challenges associated with a specific goal.
     *
     * @param id The ID of the goal.
     * @return ResponseEntity<List < Challenge>> The response entity containing a list of challenges.
     */
    @Operation(summary = "Get all challenges for specified goal", description = "Retrieve a list of challenges connected to a specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Goal was found and challenges were retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "404", description = "Goal was not found.", content = @Content)
    })
    @GetMapping("/challenges/goal/{id}")
    public ResponseEntity<List<Challenge>> getAllChallengesByGoal(@PathVariable Long id) {
        Goal goal = goalService.findById(id);

        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Challenge> challengesByGoal = challengeService.findAllChallengesByGoal(goal);

        challengesByGoal.forEach(challenge -> challengeService.updateProgress(challenge));

        return new ResponseEntity<>(challengesByGoal, HttpStatus.OK);
    }

    /**
     * Retrieves all challenges associated with a specific user.
     *
     * @param email The email of the user.
     * @return ResponseEntity<List < Challenge>> The response entity containing a list of challenges,
     * or HttpStatus.NOT_FOUND if the user does not exist.
     */
    @Operation(summary = "Get all goals for specified user", description = "Retrieve a list of challenges connected to a user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and challenges were retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/challenges/user/{email}")
    public ResponseEntity<List<Challenge>> getAllChallengesByUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Challenge> challengesByUser = challengeService.findAllChallengesByUser(user);
        return new ResponseEntity<>(challengesByUser, HttpStatus.OK);
    }

    /**
     * Generates a challenge for a given goal ID.
     *
     * @param id The ID of the goal for which to generate a challenge.
     * @return ResponseEntity<Object> The response entity containing the generated challenge if successful,
     *         or HttpStatus.NOT_FOUND if the goal or bank account is not found.
     */
    @Operation(summary = "Generate a new challenge for specified goal", description = "Generates a challenge based on transaction history and the parameters of the specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The goal was found and a challenge was successfully generated.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "404", description = "The goal was not found.", content = @Content),
        @ApiResponse(responseCode = "400",
            description = "The application was unable to generate a challenge for the specified goal.",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "422", description = "The user connected to the specified goal has no internal representation of a bank account.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "No bank accounts connected to user with email: email"))),
    })
    @GetMapping("/challenges/generate/goal/{id}")
    public ResponseEntity<Object> generateChallengeForGoal(@PathVariable Long id) {
        Goal goal = goalService.findById(id);

        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BankAccount bankAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, goal.getUser());

        if (bankAccount == null) {
            return new ResponseEntity<>("No bank accounts connected to user with email: " + goal.getUser().getEmail(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<BankTransaction> transactions = bankTransactionService.getTransactionsByBankAccountInRange(LocalDate.now().minusMonths(1), LocalDate.now(), bankAccount);

        try {
            Challenge challenge = challengeService.autoGenerateChallengeForGoal(transactions, goal);
            challengeService.setStartDateForChallengeToToday(challenge);
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adds a new Challenge.
     *
     * @param challenge The Challenge object to be added.
     * @return ResponseEntity<Challenge> The response entity containing the added Challenge if successful,
     *         or HttpStatus.CREATED if the Challenge is added.
     */
    @Operation(summary = "Create new challenge", description = "Add a new challenge to the application.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Challenge was successfully created.",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "400", description = "Format of given challenge object was wrong.", content = @Content)
    })
    @PostMapping("challenges")
    public ResponseEntity<Challenge> addChallenge(@RequestBody Challenge challenge) {
        challengeService.setStartDateForChallengeToToday(challenge);
        Challenge addedChallenge = challengeService.updateChallenge(challenge);
        return new ResponseEntity<>(addedChallenge,HttpStatus.CREATED);
    }

    /**
     * Adds a new Challenge to a specific Goal.
     *
     * @param id        The ID of the Goal to which the Challenge will be added.
     * @param challenge The Challenge object to be added.
     * @return ResponseEntity<Challenge> The response entity containing the added Challenge if successful,
     * or HttpStatus.CREATED if the Challenge is added.
     */
    @Operation(summary = "Create new challenge for specified goal", description = "Add a new challenge for a specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Goal was found and challenge was successfully created.",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "400", description = "Format of given challenge object was wrong.", content = @Content),
        @ApiResponse(responseCode = "404", description = "Goal was not found.", content = @Content)
    })
    @PostMapping("challenges/goal/{id}")
    public ResponseEntity<Challenge> addChallengeByGoal(@PathVariable Long id, @RequestBody Challenge challenge) {
        Goal goal = goalService.findById(id);
        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        challenge.setGoal(goal);
        challenge.setUser(goal.getUser());
        Challenge addedChallenge = challengeService.updateChallenge(challenge);
        return new ResponseEntity<>(addedChallenge, HttpStatus.CREATED);
    }

    /**
     * Updates a challenge with the given ID.
     *
     * @param id       The ID of the challenge to update.
     * @param challenge The updated challenge object.
     * @return ResponseEntity<Challenge> The response entity containing the updated challenge if successful,
     *         or HttpStatus.NOT_FOUND if the challenge does not exist.
     */
    @Operation(summary = "Update a specified challenge", description = "Set a specified challenge to the values in the given challenge object.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Challenge was successfully updated.",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Challenge.class))),
        @ApiResponse(responseCode = "400", description = "Format of given challenge object was wrong.", content = @Content),
        @ApiResponse(responseCode = "404", description = "Challenge was not found.", content = @Content)
    })
    @PutMapping("challenges/{id}")
    public ResponseEntity<Challenge> updateChallenge(@PathVariable Long id, @RequestBody Challenge challenge) {
        Challenge oldChallenge = challengeService.findById(id);

        if (oldChallenge != null) {
            challenge.setId(oldChallenge.getId());

            challenge = challengeService.updateChallenge(challenge);
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a challenge with the given ID.
     *
     * @param id The ID of the challenge to delete.
     * @return ResponseEntity<String> The response entity with a message indicating the success of the delete operation,
     *         or HttpStatus.NOT_FOUND if the challenge does not exist.
     */
    @Operation(summary = "Delete a specified challenge", description = "Delete a specified challenge from the application.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Challenge was found and deleted successfully.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Goal with id: id deleted successfully."))),
        @ApiResponse(responseCode = "404", description = "Challenge was not found.", content = @Content)
    })
    @DeleteMapping("challenges/{id}")
    public ResponseEntity<String> deleteChallenge(@PathVariable Long id) {
        Challenge challenge = challengeService.findById(id);

        if (challenge != null) {
            challengeService.deleteChallenge(challenge);

            return new ResponseEntity<>("Goal with id: " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
