package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

/**
 * Class responsible for handling the general layout of the application.
 */
public class GeneralLayoutController {

  /**
   * Switches to the dashboard view.
   */
  @FXML
  public void onClickDashboard() {
    ViewSwitcher.switchTo(View.DASHBOARD);
  }

  /**
   * Switches to the budget view.
   */
  @FXML
  public void onClickBudget() {
    ViewSwitcher.switchTo(View.BUDGET);
  }

  /**
   * Switches to the accounting view.
   */
  @FXML
  public void onClickAccounting() {
    ViewSwitcher.switchTo(View.ACCOUNTING);
  }

  /**
   * Switches to the documents view.
   */
  @FXML
  public void onClickReceipts() {
    ViewSwitcher.switchTo(View.DOCUMENTS);
  }

  /**
   * Switches to the settings view.
   */
  @FXML
  public void onClickSettings() {
    ViewSwitcher.switchTo(View.SETTINGS);
  }

  /**
   * Opens the user manual in the default browser.
   */
  @FXML
  public void onOpenWikiPage() {
    try {
      Desktop.getDesktop().browse(new URI("https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/home"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens the user manual in the default browser.
   */
  @FXML
  public void onOpenUserManual() {
    try {
      Desktop.getDesktop().browse(new URI("https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/User-Manual"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens the aboutDialog.
   *
   * @throws IOException if the aboutDialog fxml file cannot be found.
   */
  @FXML
  public void onAboutDialog() throws IOException {
    Stage newAboutStage = new Stage();
    newAboutStage.setTitle("Baecon - About");
    FXMLLoader aboutDialogFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.ABOUT_DIALOG.getFileName())));
    aboutDialogFXML.load();

    newAboutStage.setScene(new Scene(aboutDialogFXML.getRoot()));
    newAboutStage.show();
  }

  /**
   * Opens the quitDialog.
   */
  @FXML
  public void onQuit() {
    Dialog<ButtonType> quitDialog = new Dialog<>();
    quitDialog.setTitle("Quit Baecon");
    quitDialog.setHeaderText("Are you sure you want to quit?");

    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    quitDialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

    quitDialog.setResultConverter(buttonType -> {
      if (buttonType == yesButton) {
        return buttonType;
      }
      return null;
    });

    Optional<ButtonType> result = quitDialog.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
      System.exit(0);
    }
  }
}
