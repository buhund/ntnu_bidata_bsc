package edu.ntnu.idatt1002.baecon.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Class responsible for the AboutDialog.
 */
public class AboutDialogController {

  @FXML
  private Button closeAboutButton;

  /**
   * Method to close the AboutDialog.
   */
  @FXML
  public void onCloseButton() {
    Stage stage = (Stage) closeAboutButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Method to open the GPL page.
   */
  @FXML
  public void onOpenGPLv3Page() {
    try {
      Desktop.getDesktop().browse(new URI("https://www.gnu.org/licenses/gpl-3.0.en.html"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
}

