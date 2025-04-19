package edu.ntnu.idatt1002.baecon.readersAndWriters;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BudgetWriterTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");

  @Test
  @DisplayName("Test that the BudgetEntryWriter can write a budget entry to a file")
  void testing_writing_a_budget_entry_to_a_file() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    double testAmount = 199.99;
    String testDescription = "Test description";
    Category testCategory = new Category(UUID.randomUUID(), testDescription);
    BudgetEntry budgetEntry = new BudgetEntry(testId, testExpense, testAmount,
        testCategory.getId());
    BudgetWriter budgetWriter = new BudgetWriter();

    LocalDate date = LocalDate.now();
    File file = baeconFiles.getBudgetEntriesFile(date);

    long numberOfLinesBefore = file.length();
    try {
      budgetWriter.writeBudgetEntryToFile(budgetEntry,file);
      long numberOfLinesAfter = file.length();
      assertTrue(numberOfLinesAfter > numberOfLinesBefore);
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @DisplayName("Test that the BudgetWriter throws an exception when trying to write to a"
      + " file that is not a csv file")
  void test_write_an_budget_entry_to_a_file_that_is_not_a_csv_File() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    double testAmount = 199.99;
    String testDescription = "Test description";
    Category testCategory = new Category(UUID.randomUUID(), testDescription);
    BudgetEntry budgetEntry = new BudgetEntry(testId, testExpense, testAmount,
        testCategory.getId());
    BudgetWriter budgetWriter = new BudgetWriter();
    LocalDate date = LocalDate.now();
    File file = baeconFiles.getBudgetEntriesFile(date);
    try {
      budgetWriter.writeBudgetEntryToFile(budgetEntry, file);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Unsupported file format. Only .csv files are supported.");
    }
  }
}