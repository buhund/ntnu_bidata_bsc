package edu.ntnu.idatt1002.baecon.factory;

import edu.ntnu.idatt1002.baecon.controller.DocumentsController;

/**
 * Class responsible for creating a single instance of the {@link DocumentsController}.
 */
public class DocumentsControllerFactory {
  private static DocumentsController instance = null;

  /**
   * Returns a single instance of the {@link DocumentsController}. If no instance exists,
   * a new instance is created.
   * Synchronized to ensure that only one thread at a time can access the controller instance.
   *
   * @return The single instance of the {@link DocumentsController}.
   */
  public static synchronized DocumentsController getDocumentsController() {
    if (instance == null) {
      instance = new DocumentsController();
    }
    return instance;
  }
}
