package edu.ntnu.idatt1002.baecon.factory;

import edu.ntnu.idatt1002.baecon.controller.CategoriesController;

/**
 * Class responsible for creating a single instance of the {@link CategoriesController}.
 */
public class CategoriesControllerFactory {
  private static CategoriesController categoriesController;

  /**
   * Returns a single instance of the {@link CategoriesController}. If no instance exists, a new
   * instance is created.
   * Synchronized to ensure that only one thread at a time can access the controller instance.
   *
   * @return The single instance of the {@link CategoriesController}.
   */
  public static synchronized CategoriesController getCategoriesController() {
    if (categoriesController == null) {
      categoriesController = new CategoriesController();
    }

    return categoriesController;
  }
}
