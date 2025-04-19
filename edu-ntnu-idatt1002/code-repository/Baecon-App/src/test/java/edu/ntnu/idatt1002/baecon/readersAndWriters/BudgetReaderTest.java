package edu.ntnu.idatt1002.baecon.readersAndWriters;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BudgetReaderTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
      + "idatt1002/baecon/dataFiles");
  @Test
  @DisplayName("Test that the BudgetReader can read a budget entry from a file with budget entries")
  void testing_reading_all_budget_entries_in_a_month_from_a_file() {
    BudgetReader budgetReader = new BudgetReader();
    LocalDate date = LocalDate.now();
    File file = baeconFiles.getBudgetEntriesFile(date);
    try {
      List<BudgetEntry> budgetEntries = budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
      assertTrue(budgetEntries.size() > 0);
      for (BudgetEntry budgetEntry : budgetEntries) {
        assertEquals(UUID.class, budgetEntry.getId().getClass());
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @DisplayName("Test that the BudgetReader throws an exception when trying to read "
      + "a file that does not exist")
  void testing_reading_all_budget_entries_from_a_file_that_is_not_a_csv_file() {
    BudgetReader budgetReader = new BudgetReader();
    LocalDate date = LocalDate.now();
    File file = baeconFiles.getBudgetEntriesFile(date);
    try {
      budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
    } catch (Exception e) {
      assertEquals("Unsupported file format. Only .csv files are supported.",
          e.getMessage());
    }
  }

  @Test
  @DisplayName("Test that the BudgetReader throws an exception when trying to read "
      + "a file that does not exist")
  void testing_reading_all_budget_entries_from_a_file_that_does_not_exist() {
    BudgetReader budgetReader = new BudgetReader();
    File file = new File("nonexistentFile.csv");

    assertThrows(IOException.class, () -> budgetReader.readAllBudgetEntriesInAMonthFromFile(file));
  }
}