package edu.ntnu.idatt1002.baecon.factory;

import edu.ntnu.idatt1002.baecon.controller.AccountingEntriesController;

/**
 * Class responsible for creating a single instance of the {@link AccountingEntriesController}.
 */
public class AccountingEntriesControllerFactory {
  private static AccountingEntriesController accountingEntriesController;

  /**
   * Returns a single instance of the {@link AccountingEntriesController}. If no instance exists,
   * a new instance is created.
   * Synchronized to ensure that only one thread at a time can access the controller instance.
   *
   * @return The single instance of the {@link AccountingEntriesController}.
   */
  public static synchronized AccountingEntriesController getAccountingEntriesController() {
    if (accountingEntriesController == null) {
      accountingEntriesController = new AccountingEntriesController();
    }

    return accountingEntriesController;
  }
}
