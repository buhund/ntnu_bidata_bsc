package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.listeners.DocumentsChangeListener;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DocumentsControllerTest {
  private DocumentsController documentsController;
  private Document testDocument;
  private final BaeconFiles baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src",
    "test", "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());

  @BeforeEach
  void setUp() {
    documentsController = new DocumentsController(baeconFiles);
    testDocument = new Document("Test Document", LocalDate.now(), new File("src/test/resources/testfile.pdf"));
  }

  @Test
  void testAddDocument() throws Exception {
    int initialSize = documentsController.getDocuments().size();
    documentsController.addDocument(testDocument);
    assertEquals(initialSize + 1, documentsController.getDocuments().size());
    assertTrue(documentsController.getDocuments().contains(testDocument));
  }

  @Test
  void testDeleteDocument() throws Exception {
    documentsController.addDocument(testDocument);
    int initialSize = documentsController.getDocuments().size();
    documentsController.deleteDocument(testDocument);
    assertEquals(initialSize - 1, documentsController.getDocuments().size());
    assertFalse(documentsController.getDocuments().contains(testDocument));
  }

  @Test
  void testSearchDocumentsByDescription() {
    documentsController.addSubscriber(
      updatedDocuments -> assertTrue(updatedDocuments.stream().anyMatch(document -> document.getDescription().toLowerCase().contains("test"))));
    documentsController.searchDocumentsByDescription("test");
  }

  @Test
  void testAddSubscriber() {
    DocumentsChangeListener listener = updatedDocuments -> {
    };
    documentsController.addSubscriber(listener);
    // Since the listeners list is private, we can test the addSubscriber indirectly through searchDocumentsByDescription
    documentsController.searchDocumentsByDescription("test");
  }
}