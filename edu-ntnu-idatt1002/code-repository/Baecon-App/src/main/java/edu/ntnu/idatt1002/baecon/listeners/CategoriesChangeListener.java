package edu.ntnu.idatt1002.baecon.listeners;

import edu.ntnu.idatt1002.baecon.data.Category;
import java.util.List;

/**
 * Interface for listening to changes in the categories.
 */
public interface CategoriesChangeListener {
  /**
   * Called when the categories have been updated.
   *
   * @param categories     the updated categories
   * @param newestCategory the newest category
   */
  void updateCategories(List<Category> categories, Category newestCategory);
}
