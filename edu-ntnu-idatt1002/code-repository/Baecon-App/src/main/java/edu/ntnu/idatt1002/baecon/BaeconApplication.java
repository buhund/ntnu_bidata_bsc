package edu.ntnu.idatt1002.baecon;

import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class responsible for first page of the program.
 */
public class BaeconApplication extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    Scene scene = new Scene(new Pane());
    ViewSwitcher.setScene(scene);
    ViewSwitcher.switchTo(View.DASHBOARD);
    stage.setTitle("Baecon");
    stage.setScene(scene);
    stage.show();

    Image pathLogo = new Image("file:src/main/resources/edu/ntnu/idatt1002/baecon/icon/iconBaecon.png");
    stage.getIcons().add(pathLogo);
  }

  /**
   * Starts the program with the help of the method 'launch' from its parent class Application.
   *
   * @param args args
   */
  public static void main(String[] args) {
    launch();
  }
}
