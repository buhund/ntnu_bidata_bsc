## Introduction

We have written test for all classes and methods that write, read or otherwise retrieve data.

The following packages and classes have been tested

```
test-package (/Baecon/src/test/java/edu/ntnu/idatt1002/baecon/)
├── controller
│   ├── AccountingEntriesControllerTest.java
│   ├── BudgetEntriesControllerTest.java
│   ├── CategoriesControllerTest.java
│   ├── DocumentsControllerTest.java
│   └── SettingsControllerTest.java
├── data
│   ├── AccountingEntryTest.java
│   ├── BudgetEntryTest.java
│   ├── CategoryTest.java
│   ├── DocumentTest.java
│   └── SettingsTest.java
├── readersAndWriters
│   ├── AccountingReaderTest.java
│   ├── AccountingWriterTest.java
│   ├── BudgetReaderTest.java
│   ├── BudgetWriterTest.java
│   ├── CategoryReaderTest.java
│   ├── CategoryWriterTest.java
│   ├── DocumentReaderTest.java
│   ├── DocumentWriterTest.java
│   ├── SettingsReaderTest.java
│   └── SettingsWriterTest.java
└── utilities
    ├── BaeconDateFormatterTest.java
    └── BaeconFilesTest.java
```

Since Categories is the foundation upon which all other entries are organized, having creation, editing, assigning and deleting the categories working correctly is likely the most business critical part of the testing.

## Categories Controller tests

<details>
    <summary>Click to expand or collapse the code block</summary>

```java
package edu.ntnu.idatt1002.baecon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.ntnu.idatt1002.baecon.controller.CategoriesController;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriesControllerTest {

  private CategoriesController controller;
  private TestCategoriesChangeListener listener;
  private BaeconFiles baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src",
    "test", "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());

  private class TestCategoriesChangeListener implements CategoriesChangeListener {
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
  public void testAddCategory() throws IOException {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    assertNotNull(listener.updatedCategories.stream().filter(category -> category.getId().equals(newCategory.getId())).findFirst().orElse(null));
  }

  @Test
  public void testEditCategory() throws IOException {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    Category updatedCategory = new Category(newCategory.getId(), "Updated Test Category");
    controller.editCategory(updatedCategory);

    assertEquals(updatedCategory.getName(), listener.updatedCategories.stream().filter(category -> category.getId().equals(newCategory.getId())).findFirst().orElse(null).getName());
  }

  @Test
  public void testDeleteCategory() throws IOException {
    controller.addSubscriber(listener);
    Category newCategory = new Category(UUID.randomUUID(), "Test Category");
    controller.addCategory(newCategory);

    controller.deleteCategory(newCategory);

    assertNull(listener.updatedCategories.stream().filter(category -> category.getId().equals(newCategory.getId())).findFirst().orElse(null));
  }
}
```
</details>

## Test Classes Diagrams

The complete tests can be found in the source code, under testing ([here](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/tree/main/src/test)). Below are the class diagrams for each package of tests.

#### Controller test classes

<details>
    <summary>Click to expand or collapse the code block</summary>


![classDiagram-tests-controller](uploads/6210dc646ea98642826fd5a383071ebd/classDiagram-tests-controller.png)

</details>

#### Data test classes

<details>
    <summary>Click to expand or collapse the image block</summary>


![classDiagram-tests-data](uploads/922de34c3a6be27829d8d3f9475370ce/classDiagram-tests-data.png)

</details>

#### Readers and Writers test classes

<details>
    <summary>Click to expand or collapse the image block</summary>

![classDiagram-tests-readersAndWriters](uploads/db9971dfa3fd2f30a87e86534e3b833b/classDiagram-tests-readersAndWriters.png)

</details>

#### Utilities test classes

<details>
    <summary>Click to expand or collapse the image block</summary>

![classDiagram-tests-utilities](uploads/152c8d32fbe6e488e23268dc51e1d04e/classDiagram-tests-utilities.png)

</details>