package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class responsible for writing {@link BudgetEntry} files.
 */
public class BudgetWriter {
  private static final String delimiter = ",";
  private static final String newLine = "\r\n";

  /**
   * This method writes a {@code BudgetEntry} file.
   *
   * @param budgetEntry as {@code BudgetEntry}
   * @throws IOException "Unsupported file format. Only .csv files are supported."
   * @throws IOException "File does not exist. Unable to write to file that does not exist."
   * @throws IOException "Unable to write budget entries to file " + file.getName() + "."
   */
  public void writeBudgetEntryToFile(BudgetEntry budgetEntry, File file) throws IOException {
    if (budgetEntry == null) {
      throw new IllegalArgumentException("Budget entry is null.");
    }
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
      fileWriter.write(budgetEntry.getId() + delimiter + budgetEntry.isExpense() + delimiter
          + budgetEntry.getAmount()
          + delimiter + budgetEntry.getCategoryId() + newLine);
    }
  }

  /**
   * Method to write budget entries to file.
   *
   * @param budgetEntries the budget entries
   * @param file the file
   * @throws IOException io exception
   */
  public void writeBudgetEntriesToFile(List<BudgetEntry> budgetEntries, File file)
      throws IOException {
    if (budgetEntries == null) {
      throw new IllegalArgumentException("Budget entries list is null.");
    }
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, false)) {
      for (BudgetEntry budgetEntry : budgetEntries) {
        fileWriter.write(budgetEntry.getId() + delimiter + budgetEntry.isExpense() + delimiter
            + budgetEntry.getAmount() + delimiter + budgetEntry.getCategoryId() + newLine);
      }
    }
  }

  /**
   * Helper method to check if file is valid.
   *
   * @param file the file
   * @throws IOException io exception
   */
  private void checkIfFileIsValid(File file) throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
  }
}
