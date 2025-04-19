package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CategoryReaderTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");
  @Test
  @DisplayName("Test that the CategoryReader can read a category from a file with categories")
  void testReadAllCategoriesFromFileWithCategories() {
    CategoryReader categoryReader = new CategoryReader();
    File file = baeconFiles.getFile("categories.csv");
    try {
      HashMap<UUID, Category> categories = categoryReader.readAllCategoriesFromFile(file);
      assertTrue(categories.size() > 0);
      for (Category category : categories.values()) {
        assertEquals(UUID.class, category.getId().getClass());
        assertEquals(String.class, category.getName().getClass());
      }
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @DisplayName("Test that the CategoryReader throws an exception when trying to read a file that is not a csv file")
  void testReadAllCategoriesFromFileThatIsNotACsvFile() {
    CategoryReader categoryReader = new CategoryReader();
    File file = baeconFiles.getFile("categories.txt");
    try {
      categoryReader.readAllCategoriesFromFile(file);
    } catch (Exception e) {
      assertEquals("Unsupported file format. Only .csv files are supported.", e.getMessage());
    }
  }
}
