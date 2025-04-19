package no.ntnu.idatt2106.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.service.BankAccountService;
import no.ntnu.idatt2106.service.BankTransactionService;
import no.ntnu.idatt2106.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * Controller class for Rest-endpoints responsible for fetching bank-related data
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class BankDataController {

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankTransactionService bankTransactionService;

    private static final Random random = new Random();

    /**
     * Retrieves an overview of the bank account based on the user's email.
     *
     * @param email The email of the user.
     * @return A ResponseEntity with an overview of the bank account.
     */
    @Operation(summary = "Get bank overview of a specified user", description = "Retrieves an overview of the stored bank information from the last month for the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and bank overview was successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Overview.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/overview/{email}")
    public ResponseEntity<Object> getBankOverviewByEmail(@PathVariable String email) {
        return getBankOverview(email, LocalDate.now().minusMonths(1), LocalDate.now());
    }

    /**
     * Retrieves an overview of the bank account based on the user's email and a date range.
     *
     * @param email The email of the user.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return A ResponseEntity with an overview of the bank account.
     */
    @Operation(summary = "Get bank overview of a specified user in specified date range", description = "Retrieves an overview of the stored bank information from the specified date range for the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and bank overview was successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Overview.class))),
        @ApiResponse(responseCode = "400", description = "The format of specified dates were wrong.", content = @Content),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("/overview/{email}/date_range")
    public ResponseEntity<Object> getBankOverviewByEmailInRange(@PathVariable String email,
                                                                @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
                                                                @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate endDate) {
        return getBankOverview(email, startDate, endDate);
    }

    /**
     * Retrieves an overview of the bank account based on the user's email.
     *
     * @param email The email of the user.
     * @return A ResponseEntity with an overview of the bank account.
     */
    private  ResponseEntity<Object> getBankOverview(String email, LocalDate startDate, LocalDate endDate) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BankAccount bankAccount;
        try {
            bankAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, user);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>("No bank account registered for user with email: " + email, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<BankTransaction> bankTransactions = bankTransactionService.getTransactionsByBankAccountInRange(startDate, endDate, bankAccount);
        return getObjectResponseEntity(bankTransactions);
    }


    private ResponseEntity<Object> getObjectResponseEntity(List<BankTransaction> bankTransactions) {
        double incomes = bankTransactionService.calculateIncomes(bankTransactions);
        double expenses = bankTransactionService.calculateExpenses(bankTransactions);
        double[] expensesByCategory = bankTransactionService.calculateExpensesByCategory(bankTransactions);

        Overview overview = new Overview(incomes, expenses, expensesByCategory);

        return new ResponseEntity<>(overview, HttpStatus.OK);
    }

    /**
     * Controller class for retrieves the savings of a user based on their email.
     *
     * @param email The email of the user.
     * @return A ResponseEntity with the user's savings.
     */
    @Operation(summary = "Get savings of specified user", description = "Retrieves a representation of savings of the user specified by an email address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was found and savings were successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Savings.class))),
        @ApiResponse(responseCode = "404", description = "User was not found.", content = @Content)
    })
    @GetMapping("savings/{email}")
    public ResponseEntity<Savings> getSavingsByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BankAccount savingsAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SAVINGS_ACCOUNT, user);

        List<BankTransaction> savingTransactions = bankTransactionService.getTransactionsByBankAccountInRange(LocalDate.now().minusMonths(1), LocalDate.now(), savingsAccount);
        double savings = bankTransactionService.calculateIncomes(savingTransactions);
        double[] savingsByCategory = bankTransactionService.calculateIncomesByCategory(savingTransactions);

        String savingsMessage;
        if (savings == 0.0) {
            savingsMessage = "Du har ikke spart noe med SpareSti enda. Begynn sparingen i dag!";
            return new ResponseEntity<>(new Savings(savings, savingsByCategory, savingsMessage), HttpStatus.OK);
        } else if (savings < 50) {
            savingsMessage = "Bra jobbet! Du er i gang med sparing med SpareSti!";
            return new ResponseEntity<>(new Savings(savings, savingsByCategory, savingsMessage), HttpStatus.OK);
        }

        ArrayList<String> currencies = new ArrayList<>();
        try (InputStream alternateCurrencies = new ClassPathResource("alternate-currencies").getInputStream()) {
            Scanner currencyScanner = new Scanner(alternateCurrencies);
            while (currencyScanner.hasNextLine()) {
                currencies.add(currencyScanner.nextLine());
            }
            currencyScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = currencies.get(random.nextInt(currencies.size()));
        String[] words = line.split(",");

        int amount = (int) savings / Integer.parseInt(words[2]);

        while (amount == 0) {
            currencies.remove(line);
            line = currencies.get(random.nextInt(currencies.size()));
            words = line.split(",");
            amount = (int) savings / Integer.parseInt(words[2]);
        }

        if (amount == 1) {
            savingsMessage = "Det tilsvarer omtrent 1 " + words[0];
        } else {
            savingsMessage = "Det tilsvarer omtrent " + amount + " " + words[1];
        }

        return new ResponseEntity<>(new Savings(savings, savingsByCategory, savingsMessage), HttpStatus.OK);
    }
}
