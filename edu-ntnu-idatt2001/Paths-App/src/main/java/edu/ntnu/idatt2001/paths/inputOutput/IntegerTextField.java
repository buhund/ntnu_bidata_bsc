/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.inputOutput;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Helper class for Integer Input in TextField with error message.
 */
public class IntegerTextField extends TextField {

  /**
   * Error label for desired error feedback.
   */
  private Label errorLabel;

  /**
   * Helper method for Integer Input in TextField with error message.
   *
   * @param errorLabel
   */
  public IntegerTextField(Label errorLabel) {
    this.errorLabel = errorLabel;
    this.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
          setText(newValue.replaceAll("[^\\d]", ""));
          errorLabel.setText("Input must be an integer!"); // Error -> Message
          errorLabel.setStyle("-fx-text-fill: red;"); // Error -> Red text color
        } else {
          errorLabel.setText(""); // No error -> Clear error message
          errorLabel.setStyle("-fx-text-fill: black;"); // No error -> Reset text color to black.
        }
      }
    });
  }
}
