package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@link BudgetEntry} files.
 */
public class BudgetReader {
  private static final String delimiter = ",|(\r\n|\n)";

  /**
   * Method to read all {@link BudgetEntry} objects from a file.
   *
   * @param file The file to read from.
   * @return A list of {@link BudgetEntry} objects.
   * @throws IOException If the file is not found or the file format is not supported.
   */
  public List<BudgetEntry> readAllBudgetEntriesInAMonthFromFile(File file)
      throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    List<BudgetEntry> budgetEntries = new ArrayList<>();
    try (Scanner budgetEntryFileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      budgetEntryFileReader.useDelimiter(delimiter);
      while (budgetEntryFileReader.hasNext()) {

        String idAsString = budgetEntryFileReader.next();
        UUID id = UUID.fromString(idAsString);

        boolean expense = Boolean.parseBoolean(budgetEntryFileReader.next());

        double amount = Double.parseDouble(budgetEntryFileReader.next());

        String categoryIdAsString = budgetEntryFileReader.next();
        UUID categoryId = UUID.fromString(categoryIdAsString);

        BudgetEntry budgetEntry = new BudgetEntry(id, expense, amount, categoryId);
        budgetEntries.add(budgetEntry);
      }
    }
    return budgetEntries;
  }
}
