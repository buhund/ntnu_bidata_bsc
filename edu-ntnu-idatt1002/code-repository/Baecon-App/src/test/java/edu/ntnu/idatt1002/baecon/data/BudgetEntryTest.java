package edu.ntnu.idatt1002.baecon.data;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BudgetEntry class, which inherits from the Entry class.
 */
class BudgetEntryTest {

  @Test
  void creates_budgetEntry_object_with_valid_arguments() {
    boolean testExpense = true;
    double testAmount = 199.99;
    UUID testId = UUID.randomUUID();
    String testDescription = "Test description";
    Category testCategory = new Category(testId, testDescription);

    try {
      new BudgetEntry(testExpense, testAmount, testCategory.getId());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void creates_budgetEntry_object_with_valid_arguments_and_id() {
    UUID testId = UUID.randomUUID();
    boolean testExpense = true;
    double testAmount = 199.99;
    String testDescription = "Test description";
    Category testCategory = new Category(UUID.randomUUID(), testDescription);

    try {
      new BudgetEntry(testId, testExpense, testAmount,
          testCategory.getId());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  void throws_IEA_with_negative_amount() {
    boolean testExpense = true;
    double wrongTestAmount = -199.99;
    UUID testId = UUID.randomUUID();
    String testDescription = "Test description";
    Category testCategory = new Category(testId, testDescription);

    String expectedMessage = "The amount have to be greater than zero.";

    try {
      new BudgetEntry(testExpense, wrongTestAmount, testCategory.getId());
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void throws_IEA_with_zero_amount() {
    boolean testExpense = true;
    double wrongTestAmount = 0;
    UUID testId = UUID.randomUUID();
    String testDescription = "Test description";
    Category testCategory = new Category(testId, testDescription);

    String expectedMessage = "The amount have to be greater than zero.";

    try {
      new BudgetEntry(testExpense, wrongTestAmount, testCategory.getId());

    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void set_new_amount_with_valid_amount() {
    boolean testExpense = true;
    double testAmount = 199.99;
    UUID testId = UUID.randomUUID();
    String testDescription = "Test description";
    Category testCategory = new Category(testId, testDescription);

    BudgetEntry budgetEntry = new BudgetEntry(testExpense, testAmount, testCategory.getId());
    double newTestAmount = 299.99;
    budgetEntry.setAmount(newTestAmount);

    assertEquals(299.99, budgetEntry.getAmount());
  }

  @Test
  void set_new_category_with_valid_category() {
    boolean testExpense = true;
    double testAmount = 199.99;
    UUID testId = UUID.randomUUID();
    String testDescription = "Test description";
    Category testCategory = new Category(testId, testDescription);

    BudgetEntry budgetEntry = new BudgetEntry(testExpense, testAmount, testCategory.getId());
    Category newtestCategory = new Category(UUID.randomUUID(), "New test description");
    budgetEntry.setCategoryId(newtestCategory.getId());

    assertEquals(newtestCategory.getId(), budgetEntry.getCategoryId());
  }
}