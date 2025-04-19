package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DocumentReaderTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");
  @Test
  void read_all_documents_from_file() {

    DocumentReader documentReader = new DocumentReader();
    File testFilePath = baeconFiles.getFile("documents.csv");

    try {
      List<Document> documents = documentReader.readAllDocumentsFromFile(testFilePath);

      assertTrue(documents.size() > 0);

      for (Document document : documents) {
        assertEquals(document.getId().getClass(), UUID.class);
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void throws_exception_when_giving_non_valid_file_format() {
    DocumentReader documentReader = new DocumentReader();
    File testFilePath = baeconFiles.getFile("documents.txt");
    String expectedMessage = "Unsupported file format. Only .csv files are supported.";

    try {
      documentReader.readAllDocumentsFromFile(testFilePath);
    } catch (IOException e) {
      assertEquals(expectedMessage, e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}