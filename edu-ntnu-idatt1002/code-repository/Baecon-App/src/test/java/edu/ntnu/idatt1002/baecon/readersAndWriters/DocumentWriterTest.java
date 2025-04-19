package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentWriterTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");

  @Test
  void write_document_to_file() {
    String testDescription = "Test Description";
    LocalDate testDate = LocalDate.now();
    File testPdfFilePath = new File("");
    File testFilePath = baeconFiles.getFile("documents.csv");

    Document document = new Document(testDescription, testDate, testPdfFilePath);
    DocumentWriter documentWriter = new DocumentWriter();
    long numberOfLinesBefore = testFilePath.length();

    try {
      documentWriter.writeDocumentToFile(document, testFilePath);
      long numberOfLinesAfter = testFilePath.length();
      assertTrue(numberOfLinesAfter > numberOfLinesBefore);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void write_to_non_valid_file() {
    String testDescription = "Test Description";
    LocalDate testDate = LocalDate.now();
    File testPdfFilePath = new File("");
    File testFilePath = baeconFiles.getFile("documents.txt");
    String expectedMessage = "Unsupported file format. Only .csv files are supported.";

    Document document = new Document(testDescription, testDate, testPdfFilePath);
    DocumentWriter documentWriter = new DocumentWriter();

    try {
      documentWriter.writeDocumentToFile(document, testFilePath);
    } catch (IOException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  void write_to_file_with_wrong_directory () {
    String testDescription = "Test Description";
    LocalDate testDate = LocalDate.now();
    File testPdfFilePath = new File("");
    File testFilePath = new File("nonexistenFile.csv");

    Document document = new Document(testDescription, testDate, testPdfFilePath);
    DocumentWriter documentWriter = new DocumentWriter();

    try {
      documentWriter.writeDocumentToFile(document, testFilePath);
    } catch (IOException e) {
      assertEquals(e.getMessage(), "Unsupported directory. Only files in the documents "
        + "folder are supported.");
    }
  }
}
