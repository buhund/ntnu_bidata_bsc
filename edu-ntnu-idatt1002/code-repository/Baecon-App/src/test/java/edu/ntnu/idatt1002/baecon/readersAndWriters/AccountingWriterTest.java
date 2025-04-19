package edu.ntnu.idatt1002.baecon.readersAndWriters;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountingWriterTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
      + "idatt1002/baecon/dataFiles");

  @Test
  @DisplayName("Test that the AccountingEntryWriter can write an accounting entry to a file")
  void testing_writing_an_accounting_entry_with_valid_arguments_to_a_file() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();
    AccountingEntry accountingEntry = new AccountingEntry(testId,testExpense,testDate,testAmount,
        testCategory.getId(),testDescription,testPdfId);
    AccountingWriter accountingWriter = new AccountingWriter();
    File file = baeconFiles.getAccountingEntriesFile(accountingEntry.getDate());
    long numberOfLinesBefore = file.length();
    try {
      accountingWriter.writeAccountingEntryToFile(accountingEntry,file);
      long numberOfLinesAfter = file.length();
      assertTrue(numberOfLinesAfter > numberOfLinesBefore);
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @DisplayName("Test that the AccountingWriter throws an exception when trying to write to a"
      + " file that is not a csv file")
  void test_write_an_account_entry_to_a_file_that_is_not_a_Csv_File() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();
    AccountingEntry accountingEntry = new AccountingEntry(testId,testExpense,testDate,testAmount,
        testCategory.getId(),testDescription,testPdfId);
    AccountingWriter accountingWriter = new AccountingWriter();
    File file = baeconFiles.getAccountingEntriesFile(accountingEntry.getDate());
    try {
      accountingWriter.writeAccountingEntryToFile(accountingEntry,file);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Unsupported file format. Only .csv files are supported.");
    }
  }
}