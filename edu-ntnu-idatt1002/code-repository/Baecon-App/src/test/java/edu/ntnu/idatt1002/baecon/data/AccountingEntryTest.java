package edu.ntnu.idatt1002.baecon.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;


/**
 * Test class for the AccountingEntry class, which inherits from the Entry class.
 */
class AccountingEntryTest {

  @Test
  void creates_accountingEntry_object_with_arguments() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    try {
      new AccountingEntry(testExpense, testDate, testAmount, testCategory.getId(), testDescription, testPdfId);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void creates_accountingEntry_object_with_valid_arguments_and_id() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    try {
      new AccountingEntry(testId, testExpense, testDate, testAmount, testCategory.getId(), testDescription, testPdfId);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void throws_IEA_with_null_date() {
    boolean testExpense = true;
    LocalDate wrongTestDate = null;
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    String expectedMessage = "The date can not be empty.";

    try {
      new AccountingEntry(testExpense, wrongTestDate,testAmount, testCategory.getId(), testDescription, testPdfId);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void throws_IEA_with_negative_amount() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double wrongTestAmount = -199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    String expectedMessage = "The amount have to be greater than zero.";

    try {
      new AccountingEntry(testExpense, testDate, wrongTestAmount, testCategory.getId(), testDescription, testPdfId);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void throws_IEA_with_zero_amount() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double wrongTestAmount = 0;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    String expectedMessage = "The amount have to be greater than zero.";

    try {
      new AccountingEntry(testExpense, testDate, wrongTestAmount, testCategory.getId(), testDescription, testPdfId);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void throws_IEA_with_null_description() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String wrongTestDescription = null;
    UUID testPdfId = UUID.randomUUID();

    String expectedMessage = "The description can not be empty.";

    try {
      new AccountingEntry(testExpense, testDate, testAmount, testCategory.getId(), wrongTestDescription, testPdfId);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void throws_IEA_with_null_pdfId() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID wrongTestPdfId = null;

    String expectedMessage = "The description can not be empty.";

    try {
      new AccountingEntry(testExpense, testDate, testAmount, testCategory.getId(), testDescription, wrongTestPdfId);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void set_new_description_with_valid_description() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    AccountingEntry accountingEntry = new AccountingEntry(testExpense, testDate, testAmount, testCategory.getId(), testDescription, testPdfId);
    String newTestDescription = "New test description for an account entry";
    accountingEntry.setDescription(newTestDescription);

    assertEquals("New test description for an account entry", accountingEntry.getDescription());
  }

  @Test
  void set_new_pdfId_with_valid_pdfId() {
    boolean testExpense = true;
    LocalDate testDate = LocalDate.now();
    double testAmount = 199.99;
    Category testCategory = new Category(UUID.randomUUID(), "Test description");
    String testDescription = "Test description for an account entry";
    UUID testPdfId = UUID.randomUUID();

    AccountingEntry accountingEntry = new AccountingEntry(testExpense, testDate, testAmount, testCategory.getId(), testDescription, testPdfId);
    UUID newTestPdfId = UUID.randomUUID();
    accountingEntry.setDocumentId(newTestPdfId);

    assertEquals(newTestPdfId, accountingEntry.getDocumentId());
  }
}