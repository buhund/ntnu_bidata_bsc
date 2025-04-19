package edu.ntnu.idatt1002.baecon.data;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Class representing a document.
 */
public class Document {

  private final UUID id;
  private String description;
  private LocalDate date;
  private File file;

  /**
   * This constructor creates a document object.
   *
   * @param description as String
   * @param date as {@code LocalDate}
   * @param file as {@code File}
   */
  public Document(String description, LocalDate date, File file) {
    this.id = UUID.randomUUID();
    setDescription(description);
    setDate(date);
    setPdfFile(file);
  }

  /**
   * This constructor creates a document object.
   *
   * @param id as UUID
   * @param description as String
   * @param date as {@code Date}
   * @param pdfFile as {@code File}
   */
  public Document(UUID id, String description, LocalDate date, File pdfFile) {
    this.id = id;
    setDescription(description);
    setDate(date);
    setPdfFile(pdfFile);
  }

  /**
   * This method retrieves an ID from a {@code Receipt} object.
   *
   * @return Id as {@code UUID}
   */
  public UUID getId() {
    return id;
  }

  /**
   * This method retrieves a description from a {@code Document} object.
   *
   * @return description as String
   */
  public String getDescription() {
    return description;
  }

  /**
   * This method sets a description on a {@code Document} object.
   *
   * @param description as String
   */
  public void setDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("Description missing");
    }
    this.description = description;
  }

  /**
   * This method retrieves a date from a {@code Document} object.
   *
   * @return date as {@code Date}
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * This method sets a Date on a {@code Document} object.
   *
   * @param date as {@code Date}
   */
  public void setDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date must be set!");
    }
    this.date = date;
  }

  /**
   * This method retrieves a file path from a {@code Document} object.
   *
   * @return file path as {@code File}
   */
  public File getPdfFile() {
    return file;
  }

  /**
   * This method sets a file path on a {@code Document} object.
   *
   * @param file as {@code File}
   */
  public void setPdfFile(File file) {
    if (file == null) {
      throw new IllegalArgumentException("No file path defined");
    }
    this.file = file;
  }
}
