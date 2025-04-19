package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.readersAndWriters.AccountingReader;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountingEntriesControllerTest {
  private AccountingEntriesController controller;
  private AccountingReader accountingReader;
  private BaeconFiles baeconFiles;

  @BeforeEach
  void setUp() {
    baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src", "test",
      "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());
    controller = new AccountingEntriesController(baeconFiles);
    accountingReader = new AccountingReader();
  }

  @Test
  @Order(1)
  void testLoadEntries() {
    LocalDate localDate = LocalDate.now();

    assertDoesNotThrow(() -> controller.loadEntries(localDate));
    assertNotNull(controller.getAccountingEntries());
  }

  @Test
  @Order(2)
  void testAddNewAccountingEntry() throws IOException {
    LocalDate localDate = LocalDate.now();
    AccountingEntry entry = new AccountingEntry(true, localDate, 100.0, UUID.randomUUID(), "Test entry", UUID.randomUUID());
    controller.addNewAccountingEntry(entry);

    List<AccountingEntry> entries = controller.getAccountingEntries();
    assertTrue(entries.contains(entry));
  }

  @Test
  @Order(3)
  void testAddNewRepeatingAccountingEntry() throws IOException {
    LocalDate localDate = LocalDate.now();
    AccountingEntry entry = new AccountingEntry(true, localDate, 100.0, UUID.randomUUID(), "Test entry", UUID.randomUUID());
    controller.addNewRepeatingAccountingEntry(entry, "Yearly");

    controller.loadEntries(localDate.plusYears(1));
    List<AccountingEntry> entries = controller.getAccountingEntries();
    assertTrue(entries.stream().anyMatch(e -> e.getDescription().equals("Test entry")));
  }

  @Test
  @Order(4)
  void testEditAccountingEntry() throws IOException {
    AccountingEntry entry = new AccountingEntry(true, LocalDate.now(), 100.0, UUID.randomUUID(), "Test entry", UUID.randomUUID());
    controller.addNewAccountingEntry(entry);

    entry.setDescription("Updated description");
    controller.editAccountingEntry(entry);

    List<AccountingEntry> entries = controller.getAccountingEntries();
    assertTrue(entries.stream().anyMatch(e -> e.getDescription().equals("Updated description")));
  }

  @Test
  @Order(5)
  void testDeleteAccountingEntry() throws IOException {
    AccountingEntry entry = new AccountingEntry(true, LocalDate.now(), 100.0, UUID.randomUUID(), "Test entry", UUID.randomUUID());
    controller.addNewAccountingEntry(entry);

    controller.deleteAccountingEntry(entry);
    List<AccountingEntry> entries = controller.getAccountingEntries();
    assertFalse(entries.contains(entry));
  }

  @Test
  @Order(6)
  public void delete_accounting_entries_by_category() throws IOException, ParseException {
    Category category = new Category("Test description");
    AccountingEntry entry = new AccountingEntry(true, LocalDate.now(), 100.0,
      category.getId(), "Test entry", UUID.randomUUID());
    AccountingEntry entry2 = new AccountingEntry(true, LocalDate.now(), 100.0,
      category.getId(), "Test entry2", UUID.randomUUID());

    controller.addNewAccountingEntry(entry);
    controller.addNewAccountingEntry(entry2);

    assertTrue(controller.getAccountingEntries().contains(entry));
    assertTrue(controller.getAccountingEntries().contains(entry2));

    controller.deleteAccountingEntriesByCategory(category.getId());

    File accountingEntriesDirectory = baeconFiles.getAccountingEntriesDirectory();
    File[] files = accountingEntriesDirectory.listFiles();

    if (files == null) {
      fail("No files found");
    }

    for (File file : files) {
      List<AccountingEntry> accountingEntries = accountingReader
        .readAllAccountingEntriesInAMonthFromFile(file);
      for (AccountingEntry accountingEntry : accountingEntries) {
        assertNotEquals(category.getId(), accountingEntry.getCategoryId());
      }
    }
  }
}