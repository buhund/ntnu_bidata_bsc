/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.views.menuView;

import edu.ntnu.idatt2001.paths.assets.Constants;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Class containing Main Menu GUI and functionality.
 */
public class MainMenuView {

  /**
   * Singleton MainMenuView.
   */
  private static MainMenuView INSTANCE;

  /**
   * Main stage.
   */
  private final Stage mainStage;

  /**
   * Main scene.
   */
  private final Scene sceneMainMenu;

  /**
   * Main Layout for MainMenuView.
   */
  private final GridPane layoutMainMenu = new GridPane();


  /**
   * Method for building the Main Menu GUI.
   *
   * @param primaryStage
   */
  public MainMenuView(Stage primaryStage) {
    this.mainStage = primaryStage;
    setBackgroundImage();

    // Set size and padding for layout
    layoutMainMenu.setPadding(new Insets(10, 10, 10, 10));
    layoutMainMenu.setVgap(8);
    layoutMainMenu.setHgap(10);

    // Title Label and constraints
    Label labelMain = new Label("Main Menu");
    GridPane.setConstraints(labelMain, 0, 0);
    labelMain.setPadding(new Insets(12));
    labelMain.setStyle("-fx-font: 86 arial;");

    // Buttons and constraints
    Button buttonNewGame = new Button("New Game");
    GridPane.setConstraints(buttonNewGame, 0, 2);
    buttonNewGame.setPrefSize(260, 60);
    buttonNewGame.setStyle("-fx-font: 32 arial;");
    buttonNewGame.setOnAction(event -> toNewGameScene());

    Button buttonResumeGame = new Button("Load Game");
    GridPane.setConstraints(buttonResumeGame, 0, 3);
    buttonResumeGame.setPrefSize(260, 60);
    buttonResumeGame.setStyle("-fx-font: 32 arial;");
    buttonResumeGame.setOnAction(event -> toLoadGameScene());

    Button buttonSettings = new Button("Settings");
    GridPane.setConstraints(buttonSettings, 0, 4);
    buttonSettings.setPrefSize(260, 60);
    buttonSettings.setStyle("-fx-font: 32 arial;");
    buttonSettings.setOnAction(event -> toSettingsScene());

    Button buttonQuitGame = new Button("Quit");
    GridPane.setConstraints(buttonQuitGame, 0, 5);
    buttonQuitGame.setPrefSize(260, 60);
    buttonQuitGame.setStyle("-fx-font: 32 arial;");
    buttonQuitGame.setOnAction(event -> System.exit(0));

    // Add label and buttons to view
    layoutMainMenu.getChildren().addAll(labelMain, buttonNewGame, buttonResumeGame, buttonSettings, buttonQuitGame);

    // Set the alignment of the grid pane to center
    layoutMainMenu.setAlignment(Pos.CENTER);

    // Set the column constraints to center the buttons horizontally
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    layoutMainMenu.getColumnConstraints().add(columnConstraints);

    sceneMainMenu = new Scene(layoutMainMenu, 1280, 900);

  }

  /**
   * Get Singleton Instance.
   * Check if instance exists (i.e. is null). If not, then create it.
   * Then return instance.
   * @param mainStage
   * @return INSTANCE
   */
  public static MainMenuView getInstance(Stage mainStage) {
    if (INSTANCE == null) { // Does the instance exist?
      INSTANCE = new MainMenuView(mainStage); // If not (i.e. null), then create it
    }
    return INSTANCE; // Return instance
  }

  /**
   * Return reference to mainStage from the instance.
   *
   * @return mainStage
   */
  public Stage getStageMainMenu() {
    return mainStage;
  }

  /**
   * Return scene from the instance.
   *
   * @return sceneMainMenuView
   */
  public Scene getSceneMainMenu() {
    return sceneMainMenu;
  }

  /**
   * Method for navigating to New Game
   * Used for button.setOnAction event.
   * Call on Singleton instance and set to mainStage.
   * Get Instance of class, then get Scene.
   * Set Scene on mainStage.
   */
  private void toNewGameScene() {
    System.out.println("New Game button pressed");
    // Get "NewGame" instance, then get scene
    Scene sceneNewGame = NewGameView.getInstance(this.mainStage).getSceneNewGame();
    // Set scene on mainStage
    this.mainStage.setScene(sceneNewGame);
  }

  /**
   * Method for navigating to Load Game
   * Used for button.setOnAction event.
   * Call on Singleton instance and set to mainStage.
   * Get Instance of class, then get Scene.
   * Set Scene on mainStage.
   */
  private void toLoadGameScene() {
    System.out.println("Load Game button pressed");
    // Get "LoadGame" instance, then get scene
    Scene sceneLoadGame = LoadGameView.getInstance(this.mainStage).getSceneLoadGame();
    // Set scene on mainStage
    this.mainStage.setScene(sceneLoadGame);
  }

  /**
   * Method for navigating to Settings
   * Used for button.setOnAction event.
   * Call on Singleton instance and set to mainStage.
   * Get Instance of class, then get Scene.
   * Set Scene on mainStage.
   */
  private void toSettingsScene() {
    System.out.println("Settings button pressed");
    Scene sceneSettings = SettingsView.getInstance(this.mainStage).getSceneSettings();
    this.mainStage.setScene(sceneSettings);
  }

  /**
   * Helper method for setting background image.
   */
  private void setBackgroundImage() {
    // Load background image
    Image backgroundImageBird = new Image(Constants.IMG_JAPANESE_VINTAGE_BIRD);
    Image backgroundImageWatcher01 = new Image(Constants.IMG_WATCHER_01_GREEN);

    // Create a background image with the loaded image and set it to the layout container
    BackgroundImage background = new BackgroundImage(
        backgroundImageWatcher01,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    layoutMainMenu.setBackground(new Background(background));
  }

} // End of class MainMenuView