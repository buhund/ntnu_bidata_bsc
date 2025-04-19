package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.listeners.AccountingEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.readersAndWriters.AccountingReader;
import edu.ntnu.idatt1002.baecon.readersAndWriters.AccountingWriter;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible for handling accountingEntries.
 */
public class AccountingEntriesController {
  private final BaeconFiles baeconFiles;
  private final List<AccountingEntriesChangeListener> listeners;
  private List<AccountingEntry> accountingEntries;
  private final AccountingReader accountingReader;
  private final AccountingWriter accountingWriter;

  /**
   * Constructor to initiate a new AccountingEntriesController.
   */
  public AccountingEntriesController() {
    baeconFiles = new BaeconFiles();
    listeners = new ArrayList<>();
    accountingReader = new AccountingReader();
    accountingWriter = new AccountingWriter();
    LocalDate todaysDate = LocalDate.now();

    loadEntries(todaysDate);
  }

  /**
   * Constructor to instantiate a new AccountingEntriesController with a custom file path.
   *
   * @param baeconFiles custom {@link BaeconFiles}
   */
  public AccountingEntriesController(BaeconFiles baeconFiles) {
    this.baeconFiles = baeconFiles;
    listeners = new ArrayList<>();
    accountingReader = new AccountingReader();
    accountingWriter = new AccountingWriter();
    LocalDate todaysDate = LocalDate.now();

    loadEntries(todaysDate);
  }

  /**
   * Method to get the accountingEntries.
   *
   * @return a list of {@code AccountingEntry}
   */
  public List<AccountingEntry> getAccountingEntries() {
    return accountingEntries;
  }

  /**
   * Method to load entries from file using the accountingEntryReader.
   *
   * @param date (LocalDate)
   */
  public void loadEntries(LocalDate date) {
    File file = baeconFiles.getAccountingEntriesFile(date);

    try {
      accountingEntries = accountingReader.readAllAccountingEntriesInAMonthFromFile(file);
    } catch (IOException e) {
      if (!e.getMessage().endsWith("(No such file or directory)")) {
        System.out.println(e.getMessage());
      }
      accountingEntries = new ArrayList<>();
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    listeners.forEach(listener -> listener.updateAccountingEntries(accountingEntries));
  }

  /**
   * Method to retrieve entries from file using the accountingEntryReader.
   *
   * @param date (LocalDate)
   * @return a list of {@link AccountingEntry}
   */
  public List<AccountingEntry> retrieveEntries(LocalDate date) {
    File file = baeconFiles.getAccountingEntriesFile(date);
    List<AccountingEntry> accountingEntries = new ArrayList<>();
    try {
      accountingEntries = accountingReader.readAllAccountingEntriesInAMonthFromFile(file);
    } catch (IOException e) {
      if (!e.getMessage().endsWith("(No such file or directory)")) {
        System.out.println(e.getMessage());
      }
      accountingEntries = new ArrayList<>();
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    return accountingEntries;
  }

  /**
   * Method to add a newAccountingEntry to file.
   *
   * @param newAccountingEntry the new accounting entry
   * @throws IOException io exception
   */
  public void addNewAccountingEntry(AccountingEntry newAccountingEntry) throws IOException {
    accountingWriter.writeAccountingEntryToFile(newAccountingEntry,
        baeconFiles.getAccountingEntriesFile(newAccountingEntry.getDate()));

    loadEntries(newAccountingEntry.getDate());
  }

  /**
   * Method to add a repeating accountingEntry to file.
   *
   * @param newAccountingEntry the new accounting entry
   * @param repeatingOption the repeating option
   * @throws IOException io exception
   */
  public void addNewRepeatingAccountingEntry(AccountingEntry newAccountingEntry,
      String repeatingOption) throws IOException {
    LocalDate startDate = newAccountingEntry.getDate();

    switch (repeatingOption) {
      case "Weekly" -> {
        try {
          for (int i = 0; i < 52; i++) {
            newAccountingEntry.setDate(startDate.plusWeeks(i));
            accountingWriter.writeAccountingEntryToFile(newAccountingEntry,
                baeconFiles.getAccountingEntriesFile(newAccountingEntry.getDate()));
          }
        } catch (IllegalArgumentException | IOException numberFormatException) {
          numberFormatException.printStackTrace();
          System.out.println(numberFormatException.getMessage());
        }
      }
      case "Monthly" -> {
        try {
          for (int i = 0; i < 12; i++) {
            newAccountingEntry.setDate(startDate.plusMonths(i));
            accountingWriter.writeAccountingEntryToFile(newAccountingEntry,
                baeconFiles.getAccountingEntriesFile(newAccountingEntry.getDate()));
          }
        } catch (IllegalArgumentException | IOException numberFormatException) {
          numberFormatException.printStackTrace();
          System.out.println(numberFormatException.getMessage());
        }
      }
      case "Quarterly" -> {
        for (int i = 0; i < 12; i++) {
          try {
            newAccountingEntry.setDate(startDate.plusMonths(i * 3));
            accountingWriter.writeAccountingEntryToFile(newAccountingEntry,
                baeconFiles.getAccountingEntriesFile(newAccountingEntry.getDate()));
          } catch (IllegalArgumentException | IOException numberFormatException) {
            numberFormatException.printStackTrace();
            System.out.println(numberFormatException.getMessage());
          }
        }
      }
      case "Yearly" -> {
        for (int i = 0; i < 2; i++) {
          try {
            newAccountingEntry.setDate(startDate.plusYears(i));
            accountingWriter.writeAccountingEntryToFile(newAccountingEntry,
                baeconFiles.getAccountingEntriesFile(newAccountingEntry.getDate()));
          } catch (IllegalArgumentException | IOException numberFormatException) {
            numberFormatException.printStackTrace();
            System.out.println(numberFormatException.getMessage());
          }
        }
      }
      default -> throw new IllegalArgumentException("Invalid repeating option");
    }

    loadEntries(startDate);
  }

  /**
   * Method to edit an accounting entry.
   *
   * @param editedAccountingEntry the accounting entry to edit
   */
  public void editAccountingEntry(AccountingEntry editedAccountingEntry)
      throws IOException {
    File file = baeconFiles.getAccountingEntriesFile(editedAccountingEntry.getDate());
    accountingEntries.stream().filter(accountingEntry -> accountingEntry.getId()
        .equals(editedAccountingEntry.getId())).forEach(accountingEntry -> {
          accountingEntry.setAmount(editedAccountingEntry.getAmount());
          accountingEntry.setDate(editedAccountingEntry.getDate());
          accountingEntry.setDescription(editedAccountingEntry.getDescription());
          accountingEntry.setCategoryId(editedAccountingEntry.getCategoryId());
        });
    accountingWriter.writeAccountingEntriesToFile(accountingEntries, file);

    notifyListeners();
  }

  /**
   * Method to remove an {@code AccountingEntry}.
   *
   * @param accountingEntry the AccountingEntry to remove
   */
  public void deleteAccountingEntry(AccountingEntry accountingEntry) throws IOException {
    File file = baeconFiles.getAccountingEntriesFile(accountingEntry.getDate());
    accountingEntries.remove(accountingEntry);
    accountingWriter.writeAccountingEntriesToFile(accountingEntries, file);

    notifyListeners();
  }

  /**
   * Method to remove all {@code AccountingEntry} with a specific category.
   *
   * @param categoryId the category id
   */
  public void deleteAccountingEntriesByCategory(UUID categoryId) {
    File accountingEntriesDirectory = baeconFiles.getAccountingEntriesDirectory();
    File[] files = accountingEntriesDirectory.listFiles((file, name) -> name.endsWith(".csv"));

    if (files == null) {
      return;
    }

    for (File file : files) {
      try {
        List<AccountingEntry> accountingEntries = accountingReader
            .readAllAccountingEntriesInAMonthFromFile(file);
        accountingEntries.removeIf(accountingEntry -> accountingEntry.getCategoryId()
            .equals(categoryId));
        accountingWriter.writeAccountingEntriesToFile(accountingEntries, file);
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    }

    notifyListeners();
  }

  /**
   * Method to add a new subscriber to the list of listeners.
   *
   * @param listener the new subscriber
   */
  public void addSubscriber(AccountingEntriesChangeListener listener) {
    listeners.add(listener);
  }

  /**
   * Method to notify all subscribers of a change.
   */
  private void notifyListeners() {
    listeners.forEach(listener -> listener.updateAccountingEntries(accountingEntries));
  }
}
