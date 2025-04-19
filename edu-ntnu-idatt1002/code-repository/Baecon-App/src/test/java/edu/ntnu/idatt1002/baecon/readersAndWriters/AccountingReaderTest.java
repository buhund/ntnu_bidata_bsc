package edu.ntnu.idatt1002.baecon.readersAndWriters;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountingReaderTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");

  @Test
  @DisplayName("Test that the AccountingReader can read a budget entry from a file with "
      + "accounting entries")
  void testing_reading_all_accounting_entries_in_a_month_from_a_file() {
    AccountingReader accountingReader = new AccountingReader();
    List<AccountingEntry> accountingEntries = null;
    try {
      accountingEntries = accountingReader.
          readAllAccountingEntriesInAMonthFromFile(baeconFiles.getAccountingEntriesFile(LocalDate
            .of(2023, 4, 1)));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      fail();
    }
    accountingEntries.forEach(accountingEntry -> System.out.println(accountingEntry.getCategoryId()));
    assertTrue(accountingEntries.size() > 0);
    for (AccountingEntry accountingEntry : accountingEntries) {
      assertEquals(UUID.class, accountingEntry.getId().getClass());
    }
  }

  @Test
  @DisplayName("Test that the AccountReader throws an exception when trying to read "
      + "a file that does not exist")
  void testing_reading_all_accounting_entries_from_a_file_that_is_not_a_csv_file() {
    AccountingReader accountReader = new AccountingReader();
    File file = new File("src/test/resources/edu/ntnu/idatt1002/baecon/"
        + "dataFiles/budgetEntries.txt");
    try {
      accountReader.readAllAccountingEntriesInAMonthFromFile(file);
    } catch (Exception e) {
      assertEquals("Unsupported file format. Only .csv files are supported.",
          e.getMessage());
    }
  }

  @Test
  @DisplayName("Test that the AccountingReader throws an exception when trying to read "
      + "a file that does not exist")
  void testing_reading_all_accounting_entries_from_a_file_that_does_not_exist() {
    AccountingReader accountingReader = new AccountingReader();
    File file = new File("nonexistentFile.csv");

    assertThrows(IOException.class, () -> {
      accountingReader.readAllAccountingEntriesInAMonthFromFile(file);
    });
  }
}