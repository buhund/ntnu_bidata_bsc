package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Category;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

/**
 * Class responsible for writing {@link Category} files.
 */
public class CategoryWriter {
  private static final String delimiter = ",";
  private static final String newLine = "\r\n";

  /**
   * Writes a category to a file.
   *
   * @param category the category
   * @param file the file
   * @throws IOException if an I/O error occurs
   */
  public void writeCategoryToFile(Category category, File file) throws IOException {
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
      fileWriter.write(category.getId() + delimiter + category.getName() + newLine);
    }
  }

  /**
   * Writes a categories to a file.
   *
   * @param categories the category
   * @param file the file
   * @throws IOException io exception
   */
  public void writeCategoriesToFile(HashMap<UUID, Category> categories, File file)
      throws IOException {
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, false)) {
      for (Category category : categories.values()) {
        fileWriter.write(category.getId() + delimiter + category.getName() + newLine);
      }
    }
  }

  /**
   * Checks if the file is valid.
   *
   * @param file the file
   * @throws IOException io exception
   */
  private void checkIfFileIsValid(File file) throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    if (!file.exists()) {
      throw new IOException("File does not exist. Unable to write to file that does not exist.");
    }
  }
}
