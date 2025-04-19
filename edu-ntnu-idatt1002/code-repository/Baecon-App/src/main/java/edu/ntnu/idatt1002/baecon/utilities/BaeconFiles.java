package edu.ntnu.idatt1002.baecon.utilities;

import java.io.File;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper class to format files with our own format.
 */
public class BaeconFiles {
  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM");
  private final String filePath;

  /**
   * Constructor to create a BaeconFiles object with a default filePath.
   */
  public BaeconFiles() {
    this.filePath = FileSystems.getDefault().getPath("src",
      "main", "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString();
  }

  /**
   * Constructor to create a BaeconFiles object with a custom filePath.
   *
   * @param filePath the filePath
   */
  public BaeconFiles(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Method to get a file.
   *
   * @param fileName the file name
   * @return the file
   */
  public File getFile(String fileName) {
    return new File(filePath + File.separator + fileName);
  }

  /**
   * Method to get a accountingEntriesFile.
   *
   * @param date the date
   * @return the accountingEntriesFile
   */
  public File getAccountingEntriesFile(LocalDate date) {
    return new File(filePath + File.separator + File.separator + "accountingEntries"
      + File.separator + dateFormatter.format(date) + ".csv");
  }

  /**
   * Method to get a accountingEntriesDirectory.
   *
   * @return the accountingEntriesDirectory
   */
  public File getAccountingEntriesDirectory() {
    return new File(filePath + File.separator + "accountingEntries");
  }

  /**
   * Method to get a budgetEntriesFile.
   *
   * @param date the date
   * @return the budgetEntriesFile
   */
  public File getBudgetEntriesFile(LocalDate date) {
    return new File(filePath + File.separator + File.separator + "budgetEntries"
      + File.separator + dateFormatter.format(date) + ".csv");
  }

  /**
   * Method to get a budgetEntriesDirectory.
   *
   * @return the budgetEntriesDirectory
   */
  public File getBudgetEntriesDirectory() {
    return new File(filePath + File.separator + "budgetEntries");
  }
}
