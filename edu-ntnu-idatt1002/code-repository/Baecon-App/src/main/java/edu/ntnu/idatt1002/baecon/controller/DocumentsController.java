package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.listeners.DocumentsChangeListener;
import edu.ntnu.idatt1002.baecon.readersAndWriters.DocumentReader;
import edu.ntnu.idatt1002.baecon.readersAndWriters.DocumentWriter;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible for handling documents.
 */
public class DocumentsController {
  private final DocumentWriter documentWriter;
  private final DocumentReader documentReader;
  private final BaeconFiles baeconFiles;
  private final List<DocumentsChangeListener> listeners;
  private List<Document> documents;

  /**
   * Private constructor to prevent instantiation
   * and only create one instance of DocumentsController.
   */
  public DocumentsController() {
    this.baeconFiles = new BaeconFiles();
    listeners = new ArrayList<>();
    documentWriter = new DocumentWriter();
    documentReader = new DocumentReader();
    loadDocuments();
  }

  /**
   * Private constructor to prevent instantiation
   * and only create one instance of DocumentsController.
   */
  public DocumentsController(BaeconFiles baeconFiles) {
    this.baeconFiles = baeconFiles;
    listeners = new ArrayList<>();
    documentWriter = new DocumentWriter();
    documentReader = new DocumentReader();
    loadDocuments();
  }


  /**
   * Method to load all documents from file.
   */
  public void loadDocuments() {
    try {
      documents = documentReader.readAllDocumentsFromFile(baeconFiles
        .getFile("documents.csv"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      documents = new ArrayList<>();
    }
  }

  /**
   * Method to get a document by id.
   *
   * @param id The id of the document.
   * @return The document with the given id.
   */
  public Document getDocumentById(UUID id) {
    return documents.stream().filter(document -> document.getId().equals(id)).findFirst()
      .orElse(null);
  }

  /**
   * Method to add a new document.
   *
   * @param document The document to add.
   * @throws IOException If the document could not be written to file.
   */
  public void addDocument(Document document) throws IOException {
    documentWriter.writeDocumentToFile(document, baeconFiles
        .getFile("documents.csv"));
    documents.add(document);
    listeners.forEach(listener -> listener.updateDocuments(documents));
  }

  /**
   * Method to delete a document.
   *
   * @param document The document to delete.
   * @throws IOException If the document could not be deleted.
   */
  public void deleteDocument(Document document) throws IOException {
    File fileToDelete = document.getPdfFile();
    if (fileToDelete.exists()) {
      if (!fileToDelete.delete()) {
        throw new IOException("Could not delete file: " + fileToDelete.getName());
      }
    }
    documents.remove(document);
    documentWriter.writeDocumentsToFile(documents, baeconFiles
        .getFile("documents.csv"));
    listeners.forEach(listener -> listener.updateDocuments(documents));
  }

  public List<Document> getDocuments() {
    return documents;
  }

  /**
   * Method to search for documents by description.
   *
   * @param searchText The text to search for.
   */
  public void searchDocumentsByDescription(String searchText) {
    listeners.forEach(documentsChangeListener -> documentsChangeListener
        .updateDocuments(documents.stream().filter(document -> document.getDescription()
        .toLowerCase().contains(searchText.toLowerCase())).toList()));
  }

  /**
   * Method to add a new subscriber.
   *
   * @param listener The listener to add.
   */
  public void addSubscriber(DocumentsChangeListener listener) {
    listeners.add(listener);
  }
}
