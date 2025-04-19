package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.List;

/**
 * Class responsible for writing {@code Document} files.
 */
public class DocumentWriter {

  private static final String delimiter = ",";
  private static final String newLine = "\r\n";

  /**
   * This method writes a {@link Document} file.
   *
   * @param document as {@link Document}
   * @throws IOException io exception
   */
  public void writeDocumentToFile(Document document, File file) throws IOException {
    if (document == null) {
      throw new NullPointerException("Document cannot be null");
    }
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
      fileWriter.write(
          document.getId() + delimiter + document.getDescription() + delimiter
            + BaeconDateFormatter.format(document.getDate()) + delimiter + document.getPdfFile()
            + newLine);
    }
  }

  /**
   * This method writes a list of {@code Document} to file.
   *
   * @param documents as {@code List<Document>}
   * @param file as {@code File}
   * @throws IOException io exception
   */
  public void writeDocumentsToFile(List<Document> documents, File file) throws IOException {
    checkIfFileIsValid(file);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, false)) {
      for (Document document : documents) {
        fileWriter.write(
            document.getId() + delimiter + document.getDescription() + delimiter
                + BaeconDateFormatter.format(document.getDate()) + delimiter + document.getPdfFile()
                + newLine);
      }
    }
  }

  /**
   * This method checks if the file is valid.
   *
   * @param file as {@code File}
   * @throws IOException io exception
   */
  private void checkIfFileIsValid(File file) throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    if (!file.getPath().startsWith(FileSystems.getDefault().getPath("src", "main",
        "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString())
        && !file.getPath().startsWith(FileSystems.getDefault().getPath("src", "test",
        "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString())) {
      throw new IOException(
        "Unsupported directory. Only files in the documents folder are supported.");
    }
  }
}
