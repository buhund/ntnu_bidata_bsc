package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.readersAndWriters.CategoryReader;
import edu.ntnu.idatt1002.baecon.readersAndWriters.CategoryWriter;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible for handling categories.
 */
public class CategoriesController {
  private final BaeconFiles baeconFiles;
  private final List<CategoriesChangeListener> listeners;
  private HashMap<UUID, Category> categories;
  private final CategoryReader categoryReader = new CategoryReader();
  private final CategoryWriter categoryWriter = new CategoryWriter();

  /**
   * Constructor for instantiating a new CategoriesController.
   */
  public CategoriesController() {
    baeconFiles = new BaeconFiles();
    listeners = new ArrayList<>();
    loadCategoriesFromFile();
  }

  /**
   * Constructor for instantiating a new CategoriesController.
   */
  public CategoriesController(BaeconFiles baeconFiles) {
    this.baeconFiles = baeconFiles;
    listeners = new ArrayList<>();
    loadCategoriesFromFile();
  }

  /**
   * Method to load categories from file.
   */
  public void loadCategoriesFromFile() {
    try {
      File file = baeconFiles.getFile("categories.csv");
      categories = categoryReader.readAllCategoriesFromFile(file);
    } catch (IOException ioException) {
      System.out.println(ioException.getMessage());
    }
  }

  /**
   * Method to get the categories.
   *
   * @return a list of {@code Category}
   */
  public List<Category> getCategories() {
    return categories.values().stream().toList();
  }

  /**
   * Method to get a {@code Category} by {@code categoryId}.
   *
   * @param categoryId the categoryId
   * @return the Category with the given id
   */
  public Category getCategoryById(UUID categoryId) {
    return categories.get(categoryId);
  }

  /**
   * Method to get a {@code Category} by {@code name}.
   *
   * @param name the name of the Category
   * @return the Category with the given name
   */
  public Category getCategoryByName(String name) {
    return categories
      .values()
      .stream()
      .filter(category -> category.getName().equalsIgnoreCase(name))
      .findFirst()
      .orElse(null);
  }

  /**
   * Method to add a new {@code Category}.
   *
   * @param category the category
   */
  public void addCategory(Category category) {
    try {
      categoryWriter.writeCategoryToFile(category,
          baeconFiles.getFile("categories.csv"));
      categories.put(category.getId(), category);
      listeners.forEach(listener -> listener.updateCategories(getCategories(), category));
    } catch (IOException ioException) {
      System.out.println(ioException.getMessage());
    }
  }

  /**
   * Method to edit a {@link Category}.
   *
   * @param category the category
   */
  public void editCategory(Category category) {
    try {
      categories.get(category.getId()).setName(category.getName());
      categoryWriter.writeCategoriesToFile(categories,
          baeconFiles.getFile("categories.csv"));
      listeners.forEach(listener -> listener.updateCategories(getCategories(), category));
    } catch (IOException ioException) {
      System.out.println(ioException.getMessage());
    }
  }

  /**
   * Method to delete a {@link Category}.
   *
   * @param category the category
   */
  public void deleteCategory(Category category) {
    try {
      categories.remove(category.getId());
      categoryWriter.writeCategoriesToFile(categories,
          baeconFiles.getFile("categories.csv"));
      listeners.forEach(listener -> listener.updateCategories(getCategories(), category));
    } catch (IOException ioException) {
      System.out.println(ioException.getMessage());
    }
  }

  /**
   * Method to add a new subscriber to the list of listeners.
   *
   * @param listener the new listener.
   */
  public void addSubscriber(CategoriesChangeListener listener) {
    listeners.add(listener);
  }
}
