/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.views.viewManager;

import edu.ntnu.idatt2001.paths.views.ingameView.InGameView;
import edu.ntnu.idatt2001.paths.views.menuView.MainMenuView;
import edu.ntnu.idatt2001.paths.views.menuView.NewGameView;
import edu.ntnu.idatt2001.paths.views.menuView.LoadGameView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class for holding, setting and running views and application.
 */
public class MainView extends Application {
  private static Stage stage;
  private static Scene scene;
  private final NewGameView newGameView = getNewGameView();
  private final InGameView inGameView = getInGameView();
  private final LoadGameView loadGameView = getLoadGameView();


  /**
   * Main method.
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Start method for running the application.
   *
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {
    MainView.stage = primaryStage;

    // Set the stage size
    primaryStage.setMinWidth(1280);
    primaryStage.setMinHeight(720);
    primaryStage.setMaxWidth(2560);
    primaryStage.setMaxHeight(1440);
    primaryStage.setWidth(1400);
    primaryStage.setHeight(900);

    // Load icon
    primaryStage.getIcons().add(new Image("file:icon/pathsIcon1.png"));
    primaryStage.setTitle("Paths - The Game");

    // Setting the scene
    Scene mainScene = MainMenuView.getInstance(primaryStage).getSceneMainMenu();
    primaryStage.setScene(mainScene);

    primaryStage.show();
  }


  /**
   * Setter for scene.
   *
   * @param scene
   */
  public static void setScene(Scene scene) {
    stage.setScene(scene);
  }

  /**
   * Getter for NewGameView.
   *
   * @return
   */
  public NewGameView getNewGameView() {
    return newGameView;
  }

  /**
   * Getter for InGameView.
   *
   * @return
   */
  public InGameView getInGameView() {
    return inGameView;
  }

  /**
   * Getter for LoadGameView.
   *
   * @return
   */
  public LoadGameView getLoadGameView() {
    return loadGameView;
  }


} // End of class MainView
