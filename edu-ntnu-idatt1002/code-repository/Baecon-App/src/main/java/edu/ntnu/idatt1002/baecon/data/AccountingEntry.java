package edu.ntnu.idatt1002.baecon.data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing an accounting entry.
 */
public class AccountingEntry extends Entry {
  private String description;
  private UUID documentId;
  private LocalDate date;

  /**
   * Constructor responsible for creating an accounting entry with a random ID.
   *
   * @param expense     true for expense and false for income (boolean)
   * @param date        the date of an entry (Date)
   * @param amount      the amount of an entry in norwegian kroner (double)
   * @param categoryId    the category of an entry (Category)
   * @param description the description of an account entry (String)
   * @param pdfId       the identity of a pdf receipt (UUID)
   */
  public AccountingEntry(boolean expense, LocalDate date, double amount, UUID categoryId,
      String description, UUID pdfId) {
    super(expense, amount, categoryId);
    this.description = description;
    this.documentId = pdfId;
    setDate(date);
  }

  /**
   * Constructor responsible for creating an accounting entry with an ID.
   *
   * @param id       the id of the entry (UUID)
   * @param expense  true for expense and false for income (boolean)
   * @param date     the date of an entry (Date)
   * @param amount   the amount of an entry in norwegian kroner (double)
   * @param categoryId the category of an entry (Category)
   * @param description the description of an account entry (String)
   * @param pdfId       the identity of a pdf receipt (UUID)
   */
  public AccountingEntry(UUID id, boolean expense, LocalDate date, double amount,
      UUID categoryId, String description, UUID pdfId) {
    super(id, expense, amount, categoryId);
    this.description = description;
    this.documentId = pdfId;
    setDate(date);
  }

  /**
   * Accessing the description of an accounting entry.
   *
   * @return description (String)
   */
  public String getDescription() {
    return description;
  }

  /**
   * Changing the description of an accounting entry.
   * Checking if the new description is empty or not, and throwing an exception if it is.
   *
   * @param description (String)
   * @throws IllegalArgumentException "The description cannot be empty."
   */
  public void setDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("The description can not be empty.");
    }
    this.description = description;
  }

  /**
   * Accessing the receipt ID of an accounting entry.
   *
   * @return receiptId (UUID)
   */
  public UUID getDocumentId() {
    return documentId;
  }

  /**
   * Changing the document ID of an accounting entry.
   * Checking if the new document ID is empty or not, and throwing an exception if it is.
   *
   * @param documentId (UUID)
   * @throws IllegalArgumentException "The document ID cannot be empty."
   */
  public void setDocumentId(UUID documentId) {
    if (documentId == null) {
      throw new IllegalArgumentException("The document ID can not be empty.");
    }
    this.documentId = documentId;
  }

  /**
   * Accessing the date of the entry.
   *
   * @return date (Date)
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Changing the date of the entry.
   * Not allowing an empty date.
   *
   * @param date (Date)
   * @throws IllegalArgumentException "The date cannot be empty."
   */
  public void setDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("The date can not be empty.");
    }
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountingEntry that = (AccountingEntry) o;
    return Objects.equals(description, that.description) && Objects.equals(
      documentId, that.documentId)
      && date.toString().equals(that.date.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, documentId, date);
  }
}
