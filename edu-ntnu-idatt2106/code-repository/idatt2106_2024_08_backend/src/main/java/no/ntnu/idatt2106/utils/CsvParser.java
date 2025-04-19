package no.ntnu.idatt2106.utils;

import no.ntnu.idatt2106.model.BankTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing CSV files.
 * Contains methods for parsing CSV files from DNB and Nordea banks.
 *
 */
public class CsvParser {

    private final DateTimeFormatter dnbDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter nordeaDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);


    /**
     * Parses the given amount string and converts it to a double, rounding it to two decimal places.
     *
     * @param amountString The amount string to parse.
     * @return The parsed amount as a double, rounded to two decimal places.
     */
    private double parseAmount(String amountString) {
        double amount = Double.parseDouble(amountString.replace(",", ".").trim());
        return Math.round(amount * 100.0) / 100.0;  // Rounding to two decimal places
    }

    /**
     * DNB format parsing method.
     * For parsing a CSV file containing DNB bank transactions and returns a list of
     * BankTransaction objects.
     * <p>
     * if-test to check if the amount is in the "Ut fra konto" or "Inn på konto" column.
     * - Check if amount is in "Ut" or "Inn" column.
     * - "Ut" is negative, "Inn" is positive.
     * - Writes the amount to the amount field.
     * - Assumes *only one* of the columns will contain an amount.
     *
     *
     * @param dnbCSV The input stream of the DNB CSV file.
     * @return A list of BankTransaction objects representing the parsed transactions.
     */
    public List<BankTransaction> parseDnbCsv(InputStream dnbCSV) {
        List<BankTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dnbCSV))) {
            reader.readLine(); // Skipping headers
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 5) {
                    BankTransaction transaction = new BankTransaction();
                    transaction.setTransactionDate(LocalDate.parse(data[0].replace("\"", ""), dnbDateFormatter));
                    transaction.setDescription(data[1].replace("\"", ""));
                    transaction.setAmount(parseAmount((data[3].replace("\"", "").isEmpty() ? data[4] : data[3]).replace("\"", "")));
                    transactions.add(transaction);
                } else {
                    logger.error("Skipping malformed line: {}", line);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading DNB CSV", e);
        }
        return transactions;
    }

    /**
     * Nordea format parsing method.
     * For parsing a CSV file from Nordea and creates a list of BankTransaction objects.
     *<p>
     * @param nordeaCSV The input stream of the CSV file from Nordea.
     * @return A list of BankTransaction objects parsed from the CSV file.
     */
    public List<BankTransaction> parseNordeaCsv(InputStream nordeaCSV) {
        List<BankTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(nordeaCSV))) {
            reader.readLine(); // Skipping headers
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 6) { // Ensure there are enough columns
                    BankTransaction transaction = new BankTransaction();
                    transaction.setTransactionDate(LocalDate.parse(data[0], nordeaDateFormatter));
                    transaction.setDescription(data[5]);
                    transaction.setAmount(parseAmount(data[1]));
                    transactions.add(transaction);
                } else {
                    logger.error("Skipping malformed line due to insufficient data columns: {}", line);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading Nordea CSV", e);
        } catch (Exception e) {
            logger.error("Unexpected error during parsing Nordea CSV", e);
        }
        return transactions;
    }

}
