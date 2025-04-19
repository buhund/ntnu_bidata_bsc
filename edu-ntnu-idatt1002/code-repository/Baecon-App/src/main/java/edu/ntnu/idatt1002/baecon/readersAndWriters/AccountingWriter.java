package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class responsible for writing {@link AccountingEntry} files.
 */
public class AccountingWriter {
  private final String delimiter = ",";
  private final String newLine = "\r\n";

  /**
   * This method writes a {@code AccountingEntry} file.
   *
   * @param accountingEntry as {@code AccountingEntry}
   * @throws IOException "Unsupported file format. Only .csv files are supported."
   * @throws IOException "File does not exist. Unable to write to file that does not exist."
   * @throws IOException "Unable to write accounting entries to file " + file.getName() + "."
   */
  public void writeAccountingEntryToFile(AccountingEntry accountingEntry, File file)
      throws IOException {
    checkIfFileIsValid(file);
    if (accountingEntry == null) {
      throw new NullPointerException("AccountingEntry cannot be null");
    }

    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
      fileWriter.write(accountingEntry.getId() + delimiter + accountingEntry.isExpense()
          + delimiter + BaeconDateFormatter.format(accountingEntry.getDate()) + delimiter
          + accountingEntry.getAmount() + delimiter + accountingEntry.getCategoryId()
          + delimiter + accountingEntry.getDescription() + delimiter
          + accountingEntry.getDocumentId() + newLine);
    }
  }

  /**
   * Method to write accounting entries to file.
   *
   * @param accountingEntries the accounting entries
   * @param file the file
   * @throws IOException io exception
   */
  public void writeAccountingEntriesToFile(List<AccountingEntry> accountingEntries, File file)
      throws IOException {
    checkIfFileIsValid(file);
    if (accountingEntries == null) {
      throw new IllegalArgumentException("AccountingEntries can not be null!");
    }

    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, false)) {
      for (AccountingEntry accountingEntry : accountingEntries) {
        fileWriter.write(accountingEntry.getId() + delimiter);
        fileWriter.write(accountingEntry.isExpense() + delimiter);
        fileWriter.write(BaeconDateFormatter.format(accountingEntry.getDate()) + delimiter);
        fileWriter.write(accountingEntry.getAmount() + delimiter);
        fileWriter.write(accountingEntry.getCategoryId() + delimiter);
        fileWriter.write(accountingEntry.getDescription() + delimiter);
        fileWriter.write(accountingEntry.getDocumentId() + newLine);
      }
    }
  }

  /**
   * Method to check if the file is valid.
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
