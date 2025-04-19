package edu.ntnu.idatt1002.baecon.listeners;

import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for listening to changes in the budget entries.
 */
public interface BudgetEntriesChangeListener {
  /**
   * Called when the budget entries have been updated.
   *
   * @param budgetEntries the updated budget entries
   * @param date          the date of the budget entries
   */
  void updateBudgetEntries(List<BudgetEntry> budgetEntries, LocalDate date);
}
