package edu.ntnu.idatt2001.paths.views.menuView;

import edu.ntnu.idatt2001.paths.assets.Constants;
import edu.ntnu.idatt2001.paths.assets.PlayerSpecializationEnum;
import edu.ntnu.idatt2001.paths.views.viewManager.MainView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class to hold the Resume/Load Game GUI.
 */
public class LoadGameView {

  private static LoadGameView INSTANCE;
  private final Stage mainStage;
  private final Scene sceneLoadGame;
  private final VBox layoutLoadGame = new VBox();


  /**
   * Currently mostly just a copy-paste of the NewGameView class and methods.
   * Will be implemented at a later stage.
   *
   * @param primaryStage
   */
  public LoadGameView(Stage primaryStage) {
    this.mainStage = primaryStage;
    setBackgroundImage();

    layoutLoadGame.setPadding(new Insets(10, 10, 10, 10));

    // Top Layer Columns and Rows. Set constraints
    GridPane topLayer = new GridPane();

    ColumnConstraints topLeftColumn = new ColumnConstraints();
    topLeftColumn.setHgrow(Priority.ALWAYS);
    topLeftColumn.setHalignment(HPos.LEFT);
    ColumnConstraints topMidColumn = new ColumnConstraints();
    topMidColumn.setHgrow(Priority.ALWAYS);
    topMidColumn.setHalignment(HPos.CENTER);
    ColumnConstraints topRightColumn = new ColumnConstraints();
    topRightColumn.setHgrow(Priority.ALWAYS);
    topRightColumn.setHalignment(HPos.LEFT);

    topLayer.getColumnConstraints().addAll(topLeftColumn, topMidColumn, topRightColumn);

    RowConstraints topRow1 = new RowConstraints();
    topRow1.setMaxHeight(80);
    topRow1.setMinHeight(60);
    topRow1.setPrefHeight(60);

    RowConstraints topRow2 = new RowConstraints();
    topRow2.setMaxHeight(60);
    topRow2.setMinHeight(60);
    topRow2.setPrefHeight(60);

    topLayer.getRowConstraints().addAll(topRow1, topRow2);

    topLayer.setVgap(8);
    topLayer.setHgap(10);


    // Top Layer elements

    Label labelNewGame = new Label("New Game");
    labelNewGame.setPadding(new Insets(4));
    labelNewGame.setStyle("-fx-font: 48 arial;");
    // Center label in its cell
//    GridPane.setHalignment(labelNewGame, HPos.CENTER);
//    GridPane.setValignment(labelNewGame, VPos.CENTER);


    HBox selectGameContainer = new HBox();
    Label labelSelectStory = new Label("Select Game: ");
    labelSelectStory.setPadding(new Insets(4));
    labelSelectStory.setStyle("-fx-font: 18 arial;");

    ComboBox comboxSelectStory = new ComboBox();
    comboxSelectStory.setPrefSize(140, 20);
    comboxSelectStory.setStyle("-fx-font: 16 arial;");
    selectGameContainer.getChildren().addAll(labelSelectStory, comboxSelectStory);

    HBox openFileContainer = new HBox();
    Label labelOpenFile = new Label("Open New Game File: ");
    labelOpenFile.setPadding(new Insets(4));
    labelOpenFile.setStyle("-fx-font: 18 arial;");

    Button buttonOpenFile = new Button("Open File …");
    buttonOpenFile.setPrefSize(140, 20);
    buttonOpenFile.setStyle("-fx-font: 16 arial;");
    openFileContainer.getChildren().addAll(labelOpenFile, buttonOpenFile);

    // Top Layer add elements
    topLayer.add(labelNewGame, 0, 0);
    topLayer.add(selectGameContainer, 0, 1);
    topLayer.add(openFileContainer, 1, 1);


    // Middle Layer and constraints
    HBox midLayer = new HBox();
    HBox.setHgrow(midLayer, Priority.ALWAYS);

    VBox midLayerLeft = new VBox();
    midLayerLeft.setStyle("-fx-background-color: #767676");
    Label labelSelectClass = new Label("Select class: ");
    labelSelectClass.setStyle("-fx-font: 18 arial;");

    ComboBox<PlayerSpecializationEnum> comboxSelectClass = new ComboBox<>(FXCollections.observableArrayList(PlayerSpecializationEnum.values()));

    Label labelStartingItem = new Label("Starting item: ");
    labelStartingItem.setStyle("-fx-font: 18 arial;");
    Text textStartingItem = new Text("");

    Label labelCharacterName = new Label("Name: ");
    labelCharacterName.setStyle("-fx-font: 18 arial;");

    TextField inputCharacterName = new TextField("");
    midLayerLeft.getChildren().addAll(labelSelectClass, comboxSelectClass,
        labelStartingItem, textStartingItem,
        labelCharacterName, inputCharacterName);
    HBox.setHgrow(midLayerLeft, Priority.ALWAYS);

    StackPane midLayerMiddle = new StackPane();
    ImageView characterModelViewBackground = new ImageView(Constants.IMG_CHAR_MODEL_BACKGROUND_FOREST_01);
    characterModelViewBackground.setFitHeight(600);
    characterModelViewBackground.setFitWidth(400);
    ImageView characterModelView = new ImageView();
    //characterModelView.setFitHeight(600);
    characterModelView.setFitWidth(400);
    midLayerMiddle.getChildren().addAll(characterModelViewBackground, characterModelView);
    midLayerMiddle.setAlignment(Pos.CENTER);
    HBox.setHgrow(midLayerMiddle, Priority.ALWAYS);


    comboxSelectClass.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlayerSpecializationEnum>() {
      @Override
      public void changed(ObservableValue<? extends PlayerSpecializationEnum> observableValue, PlayerSpecializationEnum className, PlayerSpecializationEnum pathToMapImage) {
        String imagePath = pathToMapImage.getImagePath();

        if (imagePath != null) {
          try {
            Image newImage = new Image(new FileInputStream(imagePath));
            characterModelView.setImage(newImage);
          } catch (FileNotFoundException exception) {
            Throwable e;
            System.err.println("Image not found: " + exception.getMessage());
          }
        } else {
          characterModelView.setImage(null);
        }
      }
    });


    VBox midLayerRight = new VBox();
    ListView<String> listViewGoals = new ListView<>(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
    midLayerRight.getChildren().addAll(listViewGoals);
    HBox.setHgrow(midLayerRight, Priority.ALWAYS);
    // Goal add/remove buttons
    Button addHealthGoal = new Button();
    Button addGoldGoal = new Button();
    Button addScoreGoal = new Button();
    Button deleteGoal = new Button();


    // Middle Layer add elements
    midLayer.getChildren().addAll(midLayerLeft, midLayerMiddle, midLayerRight);


    // Bottom Layer and constraints
    GridPane bottomLayer = new GridPane();

    ColumnConstraints bottomLeftColumn = new ColumnConstraints();
    bottomLeftColumn.setHgrow(Priority.ALWAYS);
    bottomLeftColumn.setHalignment(HPos.LEFT);
    ColumnConstraints bottomMidColumn = new ColumnConstraints();
    bottomMidColumn.setHgrow(Priority.ALWAYS);
    bottomMidColumn.setHalignment(HPos.LEFT);
    ColumnConstraints bottomRightColumn = new ColumnConstraints();
    bottomRightColumn.setHgrow(Priority.ALWAYS);
    bottomRightColumn.setHalignment(HPos.RIGHT);

    bottomLayer.getColumnConstraints().addAll(bottomLeftColumn, bottomMidColumn, bottomRightColumn);

    RowConstraints bottomRow = new RowConstraints();
    bottomRow.setMaxHeight(60);
    bottomRow.setMinHeight(60);
    bottomRow.setPrefHeight(60);

    bottomLayer.getRowConstraints().add(bottomRow);

    bottomLayer.setVgap(8);
    bottomLayer.setHgap(10);

    // Bottom Layer elements
    Button buttomLoadGame = new Button("Resume Game");

    buttomLoadGame.setPrefSize(260, 50);
    buttomLoadGame.setStyle("-fx-font: 32 arial;");

    Button buttonMainMenu = new Button("Main Menu");
    // TODO Singleton of MainMenu?

    buttonMainMenu.setOnAction(event -> backToMainMenu());
    buttonMainMenu.setPrefSize(240, 50);
    buttonMainMenu.setStyle("-fx-font: 32 arial;");


    // Middle Layer add elements
    bottomLayer.add(buttonMainMenu, 0, 0);
    bottomLayer.add(buttomLoadGame, 2, 0);

    // Add layers to VBox
    layoutLoadGame.getChildren().addAll(topLayer, midLayer, bottomLayer);


    // Set the column constraints to center the buttons horizontally
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    // layoutNewGame.getColumnConstraints().add(columnConstraints);

    // setCenter(layoutNewGame);


    sceneLoadGame = new Scene(layoutLoadGame, 1280, 900);

  }


  /**
   * Method for returning to Main Menu.
   * Used for button.setOnAction event.
   * Call on Singleton instance and set to mainStage.
   * Get Instance of class, then get Scene.
   * Set Scene on mainStage.
   */
  private void backToMainMenu() {
    System.out.println("Main Menu button pressed");
    Scene sceneMainMenu = MainMenuView.getInstance(this.mainStage).getSceneMainMenu();
    this.mainStage.setScene(sceneMainMenu);
  }

  /**
   * Helper method for setting background image.
   */
  private void setBackgroundImage() {
    // Load background image
    Image loadGameBackground = new Image(Constants.IMG_WATCHER_03_EVENING);

    // Create a background image with the loaded image and set it to the layout container
    BackgroundImage background = new BackgroundImage(
        loadGameBackground,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    layoutLoadGame.setBackground(new Background(background));
  }

  /**
   * Get Singleton Instance.
   * Check if instance exists (i.e. is null). If not, then create it.
   * Then return instance.
   * @param mainStage
   * @return INSTANCE
   */
  public static LoadGameView getInstance(Stage mainStage) {
    if (INSTANCE == null) { // Do the instance exist?
      INSTANCE = new LoadGameView(mainStage); // If not (i.e. null), then create it
    }
    return INSTANCE; // Return instance
  }

  /**
   * Return reference to mainStage from the instance.
   *
   * @return mainStage
   */
  public Stage getStageLoadGame() {
    return mainStage;
  }

  /**
   * Return scene from the instance.
   *
   * @return sceneLoadGame
   */
  public Scene getSceneLoadGame() {
    return sceneLoadGame;
  }

} // End of class NewGameView

