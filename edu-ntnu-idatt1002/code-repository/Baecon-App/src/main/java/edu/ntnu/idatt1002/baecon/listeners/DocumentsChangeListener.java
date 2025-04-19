package edu.ntnu.idatt1002.baecon.listeners;

import edu.ntnu.idatt1002.baecon.data.Document;
import java.util.List;

/**
 * Interface for listening to changes in the documents.
 */
public interface DocumentsChangeListener {
  /**
   * Called when the documents have been updated.
   *
   * @param documents the updated documents
   */
  void updateDocuments(List<Document> documents);
}
