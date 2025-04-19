package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.listeners.BudgetEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.readersAndWriters.BudgetReader;
import edu.ntnu.idatt1002.baecon.readersAndWriters.BudgetWriter;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible for handling budgetEntries.
 */
public class BudgetEntriesController {
  private final BaeconFiles baeconFiles;
  private final List<BudgetEntriesChangeListener> listeners;
  private List<BudgetEntry> budgetEntries;
  private final BudgetReader budgetReader;
  private final BudgetWriter budgetWriter;

  /**
   * Constructor for instantiating a new BudgetEntriesController.
   */
  public BudgetEntriesController() {
    baeconFiles  = new BaeconFiles();
    listeners = new ArrayList<>();
    budgetReader = new BudgetReader();
    budgetWriter = new BudgetWriter();
    LocalDate todaysDate = LocalDate.now();

    loadEntries(todaysDate);
  }

  /**
   * Constructor for instantiating a new BudgetEntriesController with a custom file path.
   *
   * @param baeconFiles as BaeconFiles
   */
  public BudgetEntriesController(BaeconFiles baeconFiles) {
    this.baeconFiles = baeconFiles;
    listeners = new ArrayList<>();
    budgetReader = new BudgetReader();
    budgetWriter = new BudgetWriter();
    LocalDate todaysDate = LocalDate.now();

    loadEntries(todaysDate);
  }

  /**
   * Method to add a listener to the list of listeners.
   *
   * @return {@code List} of BudgetEntriesController
   */
  public List<BudgetEntry> getBudgetEntries() {
    return budgetEntries;
  }

  /**
   * This method retrieves all entries from a file using {@link BudgetReader}.
   *
   * @param date date
   */
  public void loadEntries(LocalDate date) {
    File file = baeconFiles.getBudgetEntriesFile(date);

    try {
      budgetEntries = budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
    } catch (IOException e) {
      if (!e.getMessage().endsWith("(No such file or directory)")) {
        System.out.println(e.getMessage());
      }
      budgetEntries = new ArrayList<>();
    }
    listeners.forEach(listener -> listener.updateBudgetEntries(budgetEntries, date));
  }

  /**
   * Method to retrieve entries from a file using {@link BudgetReader}.
   *
   * @param date (LocalDate)
   * @return {@code List} of {@link BudgetEntry}
   */
  public List<BudgetEntry> retrieveEntries(LocalDate date) {
    File file = baeconFiles.getBudgetEntriesFile(date);
    List<BudgetEntry> budgetEntries;
    try {
      budgetEntries = budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
    } catch (IOException e) {
      if (!e.getMessage().endsWith("(No such file or directory)")) {
        System.out.println(e.getMessage());
      }
      budgetEntries = new ArrayList<>();
    }
    return budgetEntries;
  }

  /**
   * This method adds a new budget entry to the file and updates the list of budget entries.
   *
   * @param newBudgetEntry as BudgetEntry
   * @throws IOException io exception
   */
  public void addNewBudgetEntry(BudgetEntry newBudgetEntry, LocalDate date) throws IOException {
    File file = baeconFiles.getBudgetEntriesFile(date);
    budgetWriter.writeBudgetEntryToFile(newBudgetEntry, file);
    budgetEntries.add(newBudgetEntry);
    loadEntries(date);
  }

  /**
   * This method adds a new budget entry following the given repeating option.
   *
   * @param newBudgetEntry the new budget entry to save
   * @param date the date to start from
   * @param repeatingOption the repeating option: "Monthly", "Quarterly" or "Yearly"
   * @throws IOException io exception
   */
  public void addNewRepeatingBudgetEntry(BudgetEntry newBudgetEntry, LocalDate date,
      String repeatingOption) throws IOException {

    switch (repeatingOption) {
      case "Monthly" -> {
        for (int i = 0; i < 12; i++) {
          budgetWriter.writeBudgetEntryToFile(newBudgetEntry,
              baeconFiles.getBudgetEntriesFile(date.plusMonths(i)));
        }
      }
      case "Quarterly" -> {
        for (int i = 0; i < 12; i++) {
          budgetWriter.writeBudgetEntryToFile(newBudgetEntry,
              baeconFiles.getBudgetEntriesFile(date.plusMonths(i * 3)));
        }
      }
      case "Yearly" -> {
        for (int i = 0; i < 2; i++) {
          budgetWriter.writeBudgetEntryToFile(newBudgetEntry,
              baeconFiles.getBudgetEntriesFile(date.plusYears(i)));
        }
      }
      default -> throw new IllegalArgumentException("Invalid repeating option");
    }

    loadEntries(date);
  }

  /**
   * Method to edit a budget entry.
   *
   * @param budgetEntry the budget entry to edit
   * @param date the date of the budget entry
   * @throws IOException io exception
   */
  public void editBudgetEntry(BudgetEntry budgetEntry, LocalDate date) throws IOException {
    File file = baeconFiles.getBudgetEntriesFile(date);
    budgetEntries.stream().filter(entry -> entry.getId().equals(budgetEntry.getId()))
        .forEach(entry -> {
          entry.setAmount(budgetEntry.getAmount());
          entry.setCategoryId(budgetEntry.getCategoryId());
        });
    budgetWriter.writeBudgetEntriesToFile(budgetEntries, file);

    notifyListeners(date);
  }

  /**
   * This method removes a budget entry.
   *
   * @param budgetEntry as BudgetEntry
   */
  public void deleteBudgetEntry(BudgetEntry budgetEntry, LocalDate date) throws IOException {
    File file = baeconFiles.getBudgetEntriesFile(date);
    budgetEntries.remove(budgetEntry);
    budgetWriter.writeBudgetEntriesToFile(budgetEntries, file);

    notifyListeners(date);
  }

  /**
   * Method to remove all budget entries for a given category.
   *
   * @param categoryId as UUID
   */
  public void deleteBudgetEntriesByCategory(UUID categoryId) throws IOException {
    File budgetEntriesDirectory = baeconFiles.getBudgetEntriesDirectory();
    File[] files = budgetEntriesDirectory.listFiles((file, name) -> name.endsWith(".csv"));

    if (files == null) {
      return;
    }
    for (File file : files) {
      System.out.println(file.getName());
      List<BudgetEntry> budgetEntries = budgetReader.readAllBudgetEntriesInAMonthFromFile(file);
      budgetEntries.removeIf(entry -> entry.getCategoryId().equals(categoryId));
      budgetWriter.writeBudgetEntriesToFile(budgetEntries, file);
    }

    notifyListeners(LocalDate.now());
  }

  /**
   * This method adds a listener to the list of listeners.
   *
   * @param listener as BudgetEntriesChangeListener
   */
  public void addSubscriber(BudgetEntriesChangeListener listener) {
    listeners.add(listener);
  }

  /**
   * Method to notify all listeners.
   *
   * @param date date
   */
  private void notifyListeners(LocalDate date) {
    listeners.forEach(listener -> listener.updateBudgetEntries(budgetEntries, date));
  }
}
