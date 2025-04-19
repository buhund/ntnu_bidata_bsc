package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Category;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@link Category} files.
 */
public class CategoryReader {
  private static final String delimiter = ",|(\r\n|\n)";

  /**
   * Reads all categories from a file.
   *
   * @return A {@link HashMap} of {@link Category} objects.
   * @throws IOException if an I/O error occurs.
   */
  public HashMap<UUID, Category> readAllCategoriesFromFile(File file) throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    HashMap<UUID, Category> categories = new HashMap<>();
    try (Scanner fileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      fileReader.useDelimiter(delimiter);
      while (fileReader.hasNext()) {
        String idAsString = fileReader.next();
        UUID id = UUID.fromString(idAsString);
        String description = fileReader.next();
        Category category = new Category(id, description);
        categories.put(id, category);
      }
    }
    return categories;
  }
}
