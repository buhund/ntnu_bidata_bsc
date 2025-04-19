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
import no.ntnu.idatt2106.model.BankTransaction;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.service.BankAccountService;
import no.ntnu.idatt2106.service.BankStatementParserService;
import no.ntnu.idatt2106.service.BankTransactionService;
import no.ntnu.idatt2106.service.UserService;
import no.ntnu.idatt2106.utils.TransactionCategorisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller class to handle CSV file uploading and processing.
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class CsvController {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankTransactionService bankTransactionService;

    @Autowired
    UserService userService;

    @Autowired
    BankStatementParserService bankStatementParserService;

    @Autowired
    TransactionCategorisation transactionCategorisation;

    /**
     * Saves the transactions from a CSV file to the user's spending account.
     *
     * @param email The email of the user.
     * @param file The CSV file containing the transactions.
     * @return A ResponseEntity with a success or error message.
     */
    @Operation(summary = "Upload bank transactions csv", description = "Upload csv file containing bank transactions to be used in the application for simulation of bank data.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions were successfully saved.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Transactions successfully parsed and saved."))),
        @ApiResponse(responseCode = "404", description = "User not found.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "User with email email does not exist."))),
        @ApiResponse(responseCode = "422", description = "User has no internal representation of bank account.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "User with email email has no associated bank accounts."))),
        @ApiResponse(responseCode = "400", description = "Formatting of supplied CSV file is wrong.",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/users/{email}/transactions_csv_to_first_account", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveTransactionsToUsersSpendingAccount(@PathVariable String email, @RequestParam MultipartFile file) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("User with email " + email + " does not exist.", HttpStatus.NOT_FOUND);
        }

        BankAccount usersBankAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, user);

        if (usersBankAccount == null) {
            /*
            Should not be reached during normal operations, as all new users get internal representations of bank accounts
            created on registration.
            */
            return new ResponseEntity<>("User with email " + email + " has no associated bank accounts.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<BankTransaction> transactions;
        try {
            transactions = bankStatementParserService.parseCSV(file.getInputStream());
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (transactions.isEmpty()) {
            return new ResponseEntity<>("No transactions provided.", HttpStatus.BAD_REQUEST);
        }

        transactions.forEach(bankTransaction -> {
            bankTransaction.setBankAccount(usersBankAccount);
            transactionCategorisation.categorizeAndSetTransactionCategory(bankTransaction);
            bankTransactionService.saveTransaction(bankTransaction);
        });

        return new ResponseEntity<>("Transactions successfully parsed and saved.", HttpStatus.CREATED);
    }
}
