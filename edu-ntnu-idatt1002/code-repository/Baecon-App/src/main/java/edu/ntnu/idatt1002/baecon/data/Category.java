package edu.ntnu.idatt1002.baecon.data;

import java.util.UUID;

/**
 * Represents the category of an entry.
 */
public class Category {
  private final UUID id;
  private String name;

  /**
   * Creates an instance of Category.
   * The id of the category is generated randomly.
   *
   * @param name the description of the category
   */
  public Category(String name) {
    this.id = UUID.randomUUID();
    setName(name);
  }

  /**
   * Creates an instance of Category.
   * The id of the category is given as a parameter.
   *
   * @param id the id of the category
   * @param name the description of the category
   */
  public Category(UUID id, String name) {
    this.id = id;
    setName(name);
  }

  /**
   * Returns the id of the category.
   *
   * @return the id of the category
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the description of the category.
   *
   * @return the description of the category
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the description of the category.
   *
   * @param name the description of the category
   */
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    this.name = name;
  }
}
