package edu.ntnu.idatt1002.baecon.components;

import javafx.scene.control.Alert;

/**
 * Class responsible for showing error alerts.
 */
public class ErrorAlert {
  /**
   * Method to show an error alert.
   *
   * @param header  The header of the alert.
   * @param content The content of the alert.
   */
  public static void show(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Baecon - Error");
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
