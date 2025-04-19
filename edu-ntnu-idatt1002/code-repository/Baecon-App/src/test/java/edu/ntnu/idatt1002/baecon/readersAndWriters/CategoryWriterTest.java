package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Category;

import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class CategoryWriterTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");
  @Test
  @DisplayName("Test that the CategoryWriter can write a category to a file")
  void testWriteCategoryToFile() {
    Category category = new Category("Test of CategoryWriter");
    CategoryWriter categoryWriter = new CategoryWriter();
    File file = baeconFiles.getFile("categories.csv");
    long numberOfLinesBefore = file.length();
    try {
      categoryWriter.writeCategoryToFile(category, file);
      long numberOfLinesAfter = file.length();
      assertTrue(numberOfLinesAfter > numberOfLinesBefore);
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @DisplayName("Test that the CategoryWriter throws an exception when trying to write to a file that is not a csv file")
  void testWriteCategoryToFileThatIsNotACsvFile() {
    Category category = new Category("Test of CategoryWriter");
    CategoryWriter categoryWriter = new CategoryWriter();
    File file = baeconFiles.getFile("categories.txt");
    try {
      categoryWriter.writeCategoryToFile(category, file);
    } catch (Exception e) {
      assertEquals("Unsupported file format. Only .csv files are supported.", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test that the CategoryWriter can not write a category to a file that does not " +
          "exist")
  void testWriteCategoryToFileThatDoesNotExist() {
    Category category = new Category("Test of CategoryWriter");
    CategoryWriter categoryWriter = new CategoryWriter();
    File file = baeconFiles.getFile("nonexistent.csv");
    try {
      categoryWriter.writeCategoryToFile(category, file);
    } catch (Exception e) {
      assertEquals("File does not exist. Unable to write to file that does not exist.", e.getMessage());
    }
  }
}


