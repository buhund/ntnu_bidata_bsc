package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.service.GoalService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-endpoint for fetching and saving goals
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class GoalController {
    @Autowired
    GoalService goalService;

    @Autowired
    UserService userService;

    /**
     * Retrieves a Goal with the given ID.
     *
     * @param id The ID of the Goal to retrieve.
     * @return ResponseEntity containing the retrieved Goal with HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @Operation(summary = "Retrieve specified goal", description = "Retrieve the information about a specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Goal was found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
        @ApiResponse(responseCode = "404", description = "Goal was not found.", content = @Content)
    })
    @GetMapping("/goals/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Goal goal = goalService.findById(id);

        if (goal != null) {
            return new ResponseEntity<>(goal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all the goals associated with a specific user.
     *
     * @param email The email of the user.
     * @return ResponseEntity containing a list of goals with HTTP status OK if found,
     *         or HTTP status NOT_FOUND if the user is not found.
     */
    @Operation(summary = "Retrieve all goals for user", description = "Retrieves a list of all goals connected to the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and goals were retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/goals/user/{email}")
    public ResponseEntity<List<Goal>> getAllGoalsByUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Goal> goalsByUser = goalService.findAllGoalsByUser(user);
        goalsByUser.forEach(goal -> goalService.updateProgress(goal));
        return new ResponseEntity<>(goalsByUser, HttpStatus.OK);
    }

    /**
     * Adds a new goal to the system.
     *
     * @param goal The goal to be added.
     * @return ResponseEntity containing the added goal with HTTP status CREATED.
     */
    @Operation(summary = "Create new goal", description = "Add a new goal to the application.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Goal was created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
        @ApiResponse(responseCode = "400", description = "Format of goal object was wrong.", content = @Content),
    })
    @PostMapping("/goals")
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        goalService.setStartDateForGoalToToday(goal);
        
        Goal addedGoal = goalService.updateGoal(goal);
        return new ResponseEntity<>(addedGoal, HttpStatus.CREATED);
    }

    /**
     * Adds a new goal for a user.
     *
     * @param email The email of the user.
     * @param goal The goal to be added.
     * @return ResponseEntity containing the added goal with HTTP status CREATED if successful,
     *         or HTTP status NOT_FOUND if the user is not found.
     */
    @Operation(summary = "Create new goal for user", description = "Add a new goal for the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User was found and goal was created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
        @ApiResponse(responseCode = "400", description = "Format of goal object was wrong.", content = @Content),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @PostMapping("/goals/user/{email}")
    public ResponseEntity<Goal> addGoalForUser(@PathVariable String email, @RequestBody Goal goal) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        goalService.setStartDateForGoalToToday(goal);
        goal.setUser(user);
        Goal addedGoal = goalService.updateGoal(goal);
        return new ResponseEntity<>(addedGoal, HttpStatus.CREATED);
    }

    /**
     * Update a specific goal with the given ID.
     *
     * @param id   The ID of the goal to be updated.
     * @param goal The updated goal object.
     * @return ResponseEntity containing the updated goal with HTTP status OK if successful,
     *         or HTTP status NOT_FOUND if the goal with the given ID was not found.
     */
    @Operation(summary = "Update specified goal", description = "Update a specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Goal was found and updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
        @ApiResponse(responseCode = "400", description = "Format of goal object was wrong.", content = @Content),
        @ApiResponse(responseCode = "404", description = "Goal was not found.", content = @Content)
    })
    @PutMapping("goals/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        Goal oldGoal = goalService.findById(id);

        if (oldGoal != null) {
            goal.setId(oldGoal.getId());

            goal = goalService.updateGoal(goal);
            return new ResponseEntity<>(goal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a Goal with the given ID.
     *
     * @param id The ID of the Goal to delete.
     * @return ResponseEntity with a message indicating the result of the deletion. Returns HTTP status OK if the Goal is deleted successfully, or HTTP status NOT_FOUND if the Goal
     *  is not found.
     */
    @Operation(summary = "Delete specified goal", description = "Delete a specified goal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Goal was found and deleted.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Goal with id: id deleted successfully."))),
        @ApiResponse(responseCode = "404", description = "Goal was not found.", content = @Content)
    })
    @DeleteMapping("goals/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        Goal goal = goalService.findById(id);

        if (goal != null) {
            goalService.deleteGoal(goal);

            return new ResponseEntity<>("Goal with id: " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
