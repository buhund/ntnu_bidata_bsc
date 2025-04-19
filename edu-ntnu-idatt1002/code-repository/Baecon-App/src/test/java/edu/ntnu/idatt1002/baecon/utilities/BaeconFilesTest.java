package edu.ntnu.idatt1002.baecon.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class BaeconFilesTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles(FileSystems.getDefault()
    .getPath("src", "test", "resources", "edu", "ntnu", "idatt1002", "baecon",
      "dataFiles").toString());

  @Test
  public void get_file() {
    File expectedFile = new File("src/test/resources/edu/ntnu/idatt1002/baecon/"
      + "dataFiles/2021.09.csv");
    String fileName = "2021.09.csv";


    File actualFile = baeconFiles.getFile(fileName);

    assertEquals(expectedFile, actualFile);
  }

  @Test
  public void get_accounting_entries_file() {
    File expectedFile = new File("src/test/resources/edu/ntnu/idatt1002/baecon/"
      + "dataFiles/accountingEntries/2021.09.csv");
    LocalDate date = LocalDate.of(2021, 9, 1);

    File actualFile = baeconFiles.getAccountingEntriesFile(date);

    assertEquals(expectedFile, actualFile);
  }

  @Test
  public void get_budget_entries_file() {
    File expectedFile = new File("src/test/resources/edu/ntnu/idatt1002/baecon/"
      + "dataFiles/budgetEntries/2021.09.csv");
    LocalDate date = LocalDate.of(2021, 9, 1);

    File actualFile = baeconFiles.getBudgetEntriesFile(date);

    assertEquals(expectedFile, actualFile);
  }
}

