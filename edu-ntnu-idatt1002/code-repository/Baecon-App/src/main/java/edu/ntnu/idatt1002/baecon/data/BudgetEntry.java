package edu.ntnu.idatt1002.baecon.data;

import java.util.UUID;

/**
 * Class representing a budget entry.
 */
public class BudgetEntry extends Entry {

  /**
   * Constructor responsible for creating a budget entry with a random ID.
   *
   * @param expense  true for expense and false for income (boolean)
   * @param amount   the amount of an entry in norwegian kroner (double)
   * @param categoryId the category of an entry (Category)
   */
  public BudgetEntry(boolean expense, double amount, UUID categoryId) {
    super(expense, amount, categoryId);
  }

  /**
   * Constructor responsible for creating a budget entry with an ID.
   *
   * @param id       the id of the entry (UUID)
   * @param expense  true for expense and false for income (boolean)
   * @param amount   the amount of an entry in norwegian kroner (double)
   * @param categoryId the category of an entry (Category)
   */
  public BudgetEntry(UUID id, boolean expense, double amount,
      UUID categoryId) {
    super(id, expense, amount, categoryId);
  }
}
