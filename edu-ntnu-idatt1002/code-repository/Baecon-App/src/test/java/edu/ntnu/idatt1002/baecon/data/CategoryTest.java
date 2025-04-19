package edu.ntnu.idatt1002.baecon.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

  @Test
  void testCreateCategoryWithName() {
    String categoryName = "TestCategory";
    Category category = new Category(categoryName);
    assertNotNull(category.getId());
    assertEquals(categoryName, category.getName());
  }

  @Test
  void testCreateCategoryWithIdAndName() {
    UUID categoryId = UUID.randomUUID();
    String categoryName = "TestCategory";
    Category category = new Category(categoryId, categoryName);
    assertEquals(categoryId, category.getId());
    assertEquals(categoryName, category.getName());
  }

  @Test
  void testSetName() {
    Category category = new Category("TestCategory");
    String newName = "UpdatedCategory";
    category.setName(newName);
    assertEquals(newName, category.getName());
  }

  @Test
  void testSetNameEmptyThrowsException() {
    Category category = new Category("TestCategory");
    assertThrows(IllegalArgumentException.class, () -> category.setName(""));
  }

  @Test
  void testSetNameNullThrowsException() {
    Category category = new Category("TestCategory");
    assertThrows(IllegalArgumentException.class, () -> category.setName(null));
  }
}