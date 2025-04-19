package edu.ntnu.idatt1002.baecon.listeners;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import java.util.List;

/**
 * Interface for listening to changes in the accounting entries.
 */
public interface AccountingEntriesChangeListener {
  /**
   * Called when the accounting entries have been updated.
   *
   * @param accountingEntries the updated accounting entries
   */
  void updateAccountingEntries(List<AccountingEntry> accountingEntries);
}
