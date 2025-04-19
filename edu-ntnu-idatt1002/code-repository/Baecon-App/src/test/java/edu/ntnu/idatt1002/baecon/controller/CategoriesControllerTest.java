package edu.ntnu.idatt1002.baecon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriesControllerTest {

  private CategoriesController controller;
  private TestCategoriesChangeListener listener;
  private final BaeconFiles baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src",
    "test", "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());

  private static final class TestCategoriesChangeListener implements CategoriesChangeListener {
    List<Category> updatedCategories;
    Category category;

    @Override
    public void updateCategories(List<Category> updatedCategories, Category category) {
      this.updatedCategories = updatedCategories;
      this.category = category;
    }
  }

  @BeforeEach
  public void setUp() {
    controller = new CategoriesController(baeconFiles);
    listener = new TestCategoriesChangeListener();
  }

  @Test
  public void testAddCategory() {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    assertNotNull(listener.updatedCategories.stream().filter(category -> category.getId().equals(newCategory.getId())).findFirst().orElse(null));
  }

  @Test
  public void testEditCategory() {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    newCategory.setName("Updated Category");
    controller.editCategory(newCategory);

    Category updatedCategory = listener.updatedCategories.stream()
        .filter(category -> category.getId().equals(newCategory.getId()))
        .findFirst().orElse(null);

    if (updatedCategory == null) {
      fail();
    }
    assertEquals(newCategory.getName(), updatedCategory.getName());
  }

  @Test
  public void testDeleteCategory() {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    controller.deleteCategory(newCategory);

    assertNull(listener.updatedCategories.stream().filter(category -> category.getId().equals(newCategory.getId())).findFirst().orElse(null));
  }
}