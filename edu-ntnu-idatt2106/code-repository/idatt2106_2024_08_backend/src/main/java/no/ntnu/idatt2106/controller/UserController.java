package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.service.BankAccountService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-endpoints for User-related operations
 * Includes methods to get all users, get user by email, add, update and delete users
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Retrieves all users from the system.
     *
     * @return ResponseEntity<List < User>> Returns a response entity with the list of all users in the system and a status of OK.
     */
    @Operation(summary = "Get all users", description = "Retrieves all users from the system and returns them as a list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of data.",
            content = @Content(mediaType = "application/json" ,schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
            content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Adds a new user to the system.
     * Creates spending and saving accounts connected to the user.
     * 
     * @param user The user object to be added.
     * @return ResponseEntity<Object> Returns a response entity with the added user if the email is not already in use, or a response entity with a status of CONFLICT if the email
     *  is already in use.
     */
    @Operation(summary = "Create new user", description = "Creates a new user with the given object.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful creation of user.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Supplied user object had a bad format.",
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "409", description = "Email specified in user object is already in use.",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Email already in use."))),
    })
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already in use.", HttpStatus.CONFLICT);
        }

        User addedUser = userService.updateUser(user);

        BankAccount spendingAccount = new BankAccount();
        BankAccount savingsAccount = new BankAccount();
        spendingAccount.setAccountType(AccountType.SPENDING_ACCOUNT);
        savingsAccount.setAccountType(AccountType.SAVINGS_ACCOUNT);
        spendingAccount.setUser(addedUser);
        savingsAccount.setUser(addedUser);
        bankAccountService.updateBankAccount(spendingAccount);
        bankAccountService.updateBankAccount(savingsAccount);

        userService.setPasswordToNull(addedUser);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return ResponseEntity<User> Returns a response entity with the retrieved user if found, or a response entity with a status of NOT_FOUND if the user is not found.
     */
    @Operation(summary = "Get specified user", description = "Retrieves the information about a user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content()),
        @ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
            content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user != null) {
            userService.setPasswordToNull(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a user with the given email.
     *
     * @param email The email of the user to update.
     * @param user The updated user object.
     * @return The response entity with the updated user object if the user was found and updated successfully,
     *         or a response entity with an appropriate error message if the user was not found or the email
     *         is already in use.
     */
    @Operation(summary = "Update specified user", description = "Changes a user specified by an email address to the values specified in the supplied user object.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully updated.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content()),
        @ApiResponse(responseCode = "409", description = "New email specified in user object already in use.",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Email already in use."))),
        @ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
            content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "jwt_auth")
    @PutMapping("/users/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable String email, @RequestBody User user) {
        User oldUser = userService.findByEmail(email);

        if (oldUser != null) {
            if (!user.getEmail().equals(oldUser.getEmail()) && userService.findByEmail(user.getEmail()) != null) {
                    return new ResponseEntity<>("Email already in use.", HttpStatus.CONFLICT);
                }


            user.setId(oldUser.getId());

            user = userService.updateUser(user);
            userService.setPasswordToNull(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a user with the given email.
     *
     * @param email The email of the user to delete.
     * @return The response entity with a message indicating whether the user was deleted successfully or not.
     */
    @Operation(summary = "Delete specified user", description = "Deletes a user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully deleted.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "User with email: email deleted successfully."))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content),
        @ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
            content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "jwt_auth")
    @DeleteMapping("/users/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user != null) {
            userService.deleteUser(user);

            return new ResponseEntity<>("User with email: " + email + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
