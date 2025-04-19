package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@link AccountingEntry} files.
 */
public class AccountingReader {
  private static final String delimiter = ",|(\r\n|\n)";

  /**
   * Method to read all {@link AccountingEntry} objects from a file.
   *
   * @param file The file to read from.
   * @return A list of {@link AccountingEntry} objects.
   * @throws IOException If the file is not found or the file format is not supported.
   * @throws ParseException If the date format is not supported.
   */
  public List<AccountingEntry> readAllAccountingEntriesInAMonthFromFile(File file)
      throws IOException, ParseException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    List<AccountingEntry> accountingEntries = new ArrayList<>();
    try (Scanner accountingEntryFileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      accountingEntryFileReader.useDelimiter(delimiter);
      while (accountingEntryFileReader.hasNext()) {

        String idAsString = accountingEntryFileReader.next();
        UUID id = UUID.fromString(idAsString);

        boolean expense = Boolean.parseBoolean(accountingEntryFileReader.next());

        LocalDate formatDate = BaeconDateFormatter.parse(accountingEntryFileReader.next());

        double amount = Double.parseDouble(accountingEntryFileReader.next());

        String categoryIdAsString = accountingEntryFileReader.next();
        UUID categoryId = UUID.fromString(categoryIdAsString);

        String description = accountingEntryFileReader.next();

        String pdfIdAsString = accountingEntryFileReader.next();
        UUID pdfId;
        if (pdfIdAsString.equals("null")) {
          pdfId = null;
        } else {
          pdfId = UUID.fromString(pdfIdAsString);
        }

        AccountingEntry accountingEntry = new AccountingEntry(id, expense,
            formatDate, amount, categoryId, description, pdfId);
        accountingEntries.add(accountingEntry);
      }
    }
    return accountingEntries;
  }
}
