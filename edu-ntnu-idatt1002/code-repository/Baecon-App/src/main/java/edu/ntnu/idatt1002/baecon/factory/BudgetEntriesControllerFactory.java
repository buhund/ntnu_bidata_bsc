package edu.ntnu.idatt1002.baecon.factory;

import edu.ntnu.idatt1002.baecon.controller.BudgetEntriesController;

/**
 * Class responsible for creating a single instance of the {@link BudgetEntriesController}.
 */
public class BudgetEntriesControllerFactory {
  private static BudgetEntriesController budgetEntriesController;

  /**
   * Returns a single instance of the {@link BudgetEntriesController}. If no instance exists,
   * a new instance is created.
   * Synchronized to ensure that only one thread at a time can access the controller instance.
   *
   * @return The single instance of the {@link BudgetEntriesController}.
   */
  public static synchronized BudgetEntriesController getBudgetEntriesController() {
    if (budgetEntriesController == null) {
      budgetEntriesController = new BudgetEntriesController();
    }

    return budgetEntriesController;
  }
}
