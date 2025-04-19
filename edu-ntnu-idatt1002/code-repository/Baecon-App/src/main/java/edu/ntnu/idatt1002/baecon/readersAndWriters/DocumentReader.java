package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@code Receipt} files.
 */
public class DocumentReader {
  private static final String delimiter = ",|(\r\n|\n)";

  /**
   * This method reads all Receipts from a file.
   *
   * @return a list of {@code Receipt}
   * @throws IOException if the file is not found.
   * @throws ParseException if date is in wrong format.
   */
  public List<Document> readAllDocumentsFromFile(File file) throws IOException, ParseException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    List<Document> documents = new ArrayList<>();
    try (Scanner receiptFileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      receiptFileReader.useDelimiter(delimiter);
      while (receiptFileReader.hasNext()) {

        String idAsString = receiptFileReader.next();
        UUID id = UUID.fromString(idAsString);

        String description = receiptFileReader.next();

        LocalDate formattedDate = BaeconDateFormatter.parse(receiptFileReader.next());

        File pdfFilePath = new File(receiptFileReader.next());

        Document document = new Document(id, description, formattedDate, pdfFilePath);

        documents.add(document);
      }
    }
    return documents;
  }
}
