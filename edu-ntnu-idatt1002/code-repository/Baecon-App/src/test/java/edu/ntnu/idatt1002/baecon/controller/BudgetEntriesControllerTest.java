package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.readersAndWriters.BudgetReader;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BudgetEntriesControllerTest {

  private static BudgetEntriesController controller;
  private static BudgetReader budgetReader;
  private static BaeconFiles baeconFiles;
  private final static LocalDate testDate = LocalDate.of(2023, 1, 1);

  @BeforeAll
  public static void setUp() {
    baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src", "test",
      "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());
    controller = new BudgetEntriesController(baeconFiles);
    budgetReader = new BudgetReader();
  }

  @Test
  @Order(1)
  public void testLoadEntries() {
    assertNotNull(controller.getBudgetEntries());
  }

  @Test
  @Order(2)
  public void testAddNewBudgetEntry() {
    BudgetEntry testEntry = new BudgetEntry(true, 100, UUID.randomUUID());
    try {
      controller.addNewBudgetEntry(testEntry, testDate);
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
    assertTrue(controller.getBudgetEntries().contains(testEntry));
  }

  @Test
  @Order(3)
  public void testAddNewRepeatingBudgetEntry() {
    BudgetEntry testEntry = new BudgetEntry(true, 100, UUID.randomUUID());
    try {
      controller.addNewRepeatingBudgetEntry(testEntry, testDate, "Yearly");
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
    assertTrue(controller.getBudgetEntries().contains(testEntry));
  }

  @Test
  @Order(4)
  public void testEditBudgetEntry() {
    Double changeAmount = 300.0;
    BudgetEntry testEntry = controller.getBudgetEntries().get(0);
    testEntry.setAmount(changeAmount);
    try {
      controller.editBudgetEntry(testEntry, testDate);
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
    assertEquals(changeAmount, controller.getBudgetEntries().get(0).getAmount());
  }

  @Test
  @Order(5)
  public void testDeleteBudgetEntry() {
    BudgetEntry testEntry = controller.getBudgetEntries().get(0);
    try {
      controller.deleteBudgetEntry(testEntry, testDate);
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
    assertFalse(controller.getBudgetEntries().contains(testEntry));
  }

  @Test
  @Order(6)
  public void delete_budget_entries_by_category() throws IOException {
    Category category = new Category("Test");
    BudgetEntry testEntry = new BudgetEntry(true, 100, category.getId());
    BudgetEntry testEntry2 = new BudgetEntry(true, 300, category.getId());

    controller.addNewBudgetEntry(testEntry, testDate);
    controller.addNewBudgetEntry(testEntry2, testDate);

    assertTrue(controller.getBudgetEntries().contains(testEntry));
    assertTrue(controller.getBudgetEntries().contains(testEntry2));

    controller.deleteBudgetEntriesByCategory(category.getId());

    File budgetEntriesDirectory = baeconFiles.getBudgetEntriesDirectory();
    File[] files = budgetEntriesDirectory.listFiles();

    if (files == null) {
      fail("No files found in directory");
    }

    for (File file : files) {
      List<BudgetEntry> budgetEntries = budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
      for (BudgetEntry budgetEntry : budgetEntries) {
        assertNotEquals(category.getId(), budgetEntry.getCategoryId());
      }
    }
  }
}