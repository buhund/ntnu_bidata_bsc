package edu.ntnu.idatt1002.baecon.data;

import java.util.Objects;
import java.util.UUID;

/**
 * Class representing an entry. An entry can be either an accounting entry or a budget entry.
 */
public abstract class Entry {
  private final UUID id;
  private final boolean expense;
  private double amount;
  private UUID categoryId;

  /**
   * Constructor responsible for creating an entry with a random ID.
   *
   * @param expense  true for expense and false for income (boolean)
   * @param amount   the amount of an entry in norwegian kroner (double)
   * @param categoryId the category of an entry (Category)
   */
  public Entry(boolean expense, double amount, UUID categoryId) {
    this.id = UUID.randomUUID();
    this.expense = expense;
    setAmount(amount);
    setCategoryId(categoryId);
  }

  /**
   * Constructor responsible for creating an entry with an ID.
   *
   * @param id       the id of the entry (UUID)
   * @param expense  true for expense and false for income (boolean)
   * @param amount   the amount of an entry in norwegian kroner (double)
   * @param categoryId the category of an entry (Category)
   */
  public Entry(UUID id, boolean expense, double amount, UUID categoryId) {
    this.id = id;
    this.expense = expense;
    setAmount(amount);
    setCategoryId(categoryId);
  }

  /**
   * Accessing the id of the entry.
   *
   * @return id as a String
   */
  public UUID getId() {
    return id;
  }

  /**
   * Accessing the entry expense as a boolean.
   *
   * @return expense as a boolean
   */
  public boolean isExpense() {
    return expense;
  }

  /**
   * Accessing the amount of the entry.
   *
   * @return amount as a double
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Accessing the category of the entry.
   *
   * @return category (Category)
   */
  public UUID getCategoryId() {
    return categoryId;
  }

  /**
   * Changing the amount of the entry.
   * Not allowing an amount equal to or less than zero.
   *
   * @param amount (BigDecimal)
   * @throws IllegalArgumentException "The amount have to be greater than zero."
   */
  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount have to be greater than zero.");
    }
    this.amount = amount;
  }

  /**
   * Changing the category of the entry.
   * Not allowing an empty category.
   *
   * @param categoryId (Category)
   * @throws IllegalArgumentException "The category cannot be empty."
   */
  public void setCategoryId(UUID categoryId) {
    if (categoryId == null) {
      throw new IllegalArgumentException("The category can not be empty.");
    }
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entry entry = (Entry) o;
    return expense == entry.expense && Double.compare(entry.amount, amount) == 0
        && Objects.equals(id, entry.id) && Objects.equals(categoryId, entry.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, expense, amount, categoryId);
  }
}



