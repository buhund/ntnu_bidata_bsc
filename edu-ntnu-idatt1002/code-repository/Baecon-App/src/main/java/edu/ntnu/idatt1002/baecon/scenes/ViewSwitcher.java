package edu.ntnu.idatt1002.baecon.scenes;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Class responsible for switching between views.
 */
public class ViewSwitcher {
  private static Scene scene;
  private static BorderPane generalLayout = null;

  /**
   * Method to set the scene.
   *
   * @param scene the scene to set
   */
  public static void setScene(Scene scene) {
    ViewSwitcher.scene = scene;
  }

  /**
   * Method to switch between views.
   *
   * @param view the view to switch to
   */
  public static void switchTo(View view) {
    try {
      if (generalLayout == null) {
        generalLayout = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class
          .getResource(View.GENERAL_LAYOUT.getFileName())));
      }
      Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class
          .getResource(view.getFileName())));
      generalLayout.setCenter(root);
      scene.setRoot(generalLayout);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
