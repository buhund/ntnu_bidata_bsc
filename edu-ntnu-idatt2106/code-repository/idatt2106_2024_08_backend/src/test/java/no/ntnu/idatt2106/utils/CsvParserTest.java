package no.ntnu.idatt2106.utils;

import no.ntnu.idatt2106.model.BankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    private CsvParser csvParser;

    /**
     * Helper method for printing the transactions for manual inspection.
     * Invoke with call to
     *      printTransactions(transactions);
     *
     * @param transactions List of transactions to print
     */
    void printTransactions(List<BankTransaction> transactions) {
        transactions.forEach(transaction -> {
            System.out.println(transaction.getTransactionDate());
            System.out.println(transaction.getDescription());
            System.out.println(transaction.getAmount());
        });
    }


    @BeforeEach
    void setup() {
        csvParser = new CsvParser();
    }

    /**
     * Test parsing of DNB CSV format with header containing "Ut fra konto" and "Inn på konto" columns.
     * This test check if the parsing of CSV content works, where date and amount is converted
     * from strings to LocalDate and double, respectively.
     * <p>
     * This test also specifically check if the parser correctly process the two different amount columns
     * in DNB header format.
     * <p>
     * This test checks "Ut fra konto" column.
     *
     * @throws IOException generic exception
     */
    @Test
    void testParseDnbCsvFormatHeaderWithOutgoingAmount() throws IOException {
        InputStream dnbCSV = new ClassPathResource("dummybankstatement-dnb-ut_fra_konto.csv").getInputStream();
        List<BankTransaction> transactions = csvParser.parseDnbCsv(dnbCSV);

        // Check if transactions list is not empty
        assertFalse(transactions.isEmpty());

        for (BankTransaction transaction : transactions) {
            // Check if fields are not null
            assertNotNull(transaction.getTransactionDate());
            assertNotNull(transaction.getDescription());
            assertNotNull(transaction.getAmount());

            // Check if fields have expected values
            assertEquals("2024-04-14", transaction.getTransactionDate().toString());
            assertEquals("CLASOHLSON TRONDHEIM TORG", transaction.getDescription());
            assertEquals(-643.70, transaction.getAmount());
        }
    }

    /**
     * Test parsing of DNB CSV format with header containing "Ut fra konto" and "Inn på konto" columns.
     * This test check if the parsing of CSV content works, where date and amount is converted
     * from strings to LocalDate and double, respectively.
     * <p>
     * This test also specifically check if the parser correctly process the two different amount columns
     * in DNB header format.
     * <p>
     * This test checks "Inn på konto" column.
     *
     * @throws IOException generic exception
     */
    @Test
    void testParseDnbCsvFormatHeaderWithIncomingAmount() throws IOException {
        InputStream dnbCSV = new ClassPathResource("dummybankstatement-dnb-inn_pa_konto.csv").getInputStream();
        List<BankTransaction> transactions = csvParser.parseDnbCsv(dnbCSV);

        // Check if transactions list is not empty
        assertFalse(transactions.isEmpty());

        for (BankTransaction transaction : transactions) {
            // Check if fields are not null
            assertNotNull(transaction.getTransactionDate());
            assertNotNull(transaction.getDescription());
            assertNotNull(transaction.getAmount());

            // Check if fields have expected values
            assertEquals("2024-04-12", transaction.getTransactionDate().toString());
            assertEquals("STATENS LANEKASSE", transaction.getDescription());
            assertEquals(9020.30, transaction.getAmount());
        }
    }

    /**
     * Test parsing of Nordea CSV format.
     *
     * @throws IOException generic exception
     */
    @Test
    void testParseNordeaCsv() throws IOException {
        InputStream nordeaCSV = new ClassPathResource("dummybankstatement-nordea.csv").getInputStream();
        List<BankTransaction> transactions = csvParser.parseNordeaCsv(nordeaCSV);

        // Check if transactions list is not empty
        assertFalse(transactions.isEmpty());

        for (BankTransaction transaction : transactions) {
            // Check if fields are not null
            assertNotNull(transaction.getTransactionDate());
            assertNotNull(transaction.getDescription());
            assertNotNull(transaction.getAmount());

            // Check if fields have expected values
            assertEquals("2024-04-14", transaction.getTransactionDate().toString());
            assertEquals("CLASOHLSON TRONDHEIM TORG", transaction.getDescription());
            assertEquals(-643.70, transaction.getAmount());
        }
    }
}