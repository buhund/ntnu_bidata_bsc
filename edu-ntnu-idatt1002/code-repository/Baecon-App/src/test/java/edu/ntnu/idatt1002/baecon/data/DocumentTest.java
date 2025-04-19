package edu.ntnu.idatt1002.baecon.data;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

  private Document document;
  private UUID testId;
  private String testDescription;
  private LocalDate testDate;
  private File testFile;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testDescription = "Test Document";
    testDate = LocalDate.now();
    testFile = new File("testFile.pdf");
    document = new Document(testId, testDescription, testDate, testFile);
  }

  @Test
  void testConstructorWithRandomUUID() {
    Document doc = new Document(testDescription, testDate, testFile);
    assertNotNull(doc.getId());
    assertEquals(testDescription, doc.getDescription());
    assertEquals(testDate, doc.getDate());
    assertEquals(testFile, doc.getPdfFile());
  }

  @Test
  void testConstructorWithGivenUUID() {
    assertEquals(testId, document.getId());
    assertEquals(testDescription, document.getDescription());
    assertEquals(testDate, document.getDate());
    assertEquals(testFile, document.getPdfFile());
  }

  @Test
  void testSetDescription() {
    String newDescription = "New Description";
    document.setDescription(newDescription);
    assertEquals(newDescription, document.getDescription());
  }

  @Test
  void testSetDescriptionNull() {
    assertThrows(IllegalArgumentException.class, () -> document.setDescription(null));
  }

  @Test
  void testSetDate() {
    LocalDate newDate = LocalDate.now();
    document.setDate(newDate);
    assertEquals(newDate, document.getDate());
  }

  @Test
  void testSetDateNull() {
    assertThrows(IllegalArgumentException.class, () -> document.setDate(null));
  }

  @Test
  void testSetPdfFile() {
    File newFile = new File("newFile.pdf");
    document.setPdfFile(newFile);
    assertEquals(newFile, document.getPdfFile());
  }

  @Test
  void testSetPdfFileNull() {
    assertThrows(IllegalArgumentException.class, () -> document.setPdfFile(null));
  }
}