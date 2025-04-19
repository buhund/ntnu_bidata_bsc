/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.views.ingameView;

import edu.ntnu.idatt2001.paths.assets.Constants;
import edu.ntnu.idatt2001.paths.builders.ColumnConstraintsBuilder;
import edu.ntnu.idatt2001.paths.builders.RowConstraintsBuilder;
import edu.ntnu.idatt2001.paths.controller.GameState;
import edu.ntnu.idatt2001.paths.controller.GameStateWriter;
import edu.ntnu.idatt2001.paths.domain.*;

import edu.ntnu.idatt2001.paths.views.menuView.MainMenuView;
import edu.ntnu.idatt2001.paths.views.viewManager.MainView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Class for the "In-Game" GUI and functionality.
 */
public class InGameView {

  GameState gameState = GameState.getInstance();

  private final VBox layoutInGameVBox;
  private GridPane inventoryBox;
  private TextArea storyTextArea = new TextArea();
  private HBox storyBox = new HBox();
  private HBox dialogueAndActionsArea = new HBox();
  private HBox dialogueArea = new HBox();
  private FlowPane dialogueLinkButtonArea;
  private final String storyPath = gameState.getSelectedStoryFile().getPath();


  private static InGameView INSTANCE;
  private final Stage mainStage;
  private final Scene mainScene;
  private final Story story = StoryParser.parseStoryFromFile(storyPath);
  private Player player = gameState.getInstance().getPlayer();


  /**
   * Meta method for the In-Game GUI.
   * GUI is structured in three vertical layers:
   * topLayer
   * midLayer
   * bottomLayer
   * Each contained in their own methods.
   *
   * @param primaryStage
   */
  public InGameView(Stage primaryStage) {
    this.mainStage = primaryStage;
    this.layoutInGameVBox = new VBox();

//    story = StoryParser.parseStoryFromFile(storyPath);
    Passage openingPassage = story.getOpeningPassage();

    layoutInGameVBox.getChildren().addAll(buildTopLayer(), buildMidLayer(), buildBottomLayer());
    mainScene = new Scene(layoutInGameVBox, 1280, 900);
  }

  /**
   * Method for building Top Layer.
   * Adds all elements to topLayer and returns same.
   * Holds the Top Status bar, with Name, Health, Gold, Score, and Quit button.
   *
   * @return topLayer
   */
  private GridPane buildTopLayer() {
    GridPane topLayer = new GridPane();

    ColumnConstraints columnLabelName = ColumnConstraintsBuilder.create() // Cell 0, 0
        .withMinWidth(60)
        .withPrefWidth(60)
        .withMaxWidth(80)
        .build();
    ColumnConstraints columnPlayerName = ColumnConstraintsBuilder.create() // Cell 1, 0
        .withMinWidth(120)
        .withPrefWidth(120)
        .withMaxWidth(200)
        .build();
    ColumnConstraints columnLabelHP = ColumnConstraintsBuilder.create() // Cell 2, 0
        .withMinWidth(60)
        .withPrefWidth(60)
        .withMaxWidth(60)
        .build();
    ColumnConstraints columnCurrentHP = ColumnConstraintsBuilder.create() // Cell 3, 0
        .withMinWidth(60)
        .withPrefWidth(80)
        .withMaxWidth(80)
        .build();
    ColumnConstraints columnLabelGold = ColumnConstraintsBuilder.create() // Cell 4, 0
        .withMinWidth(60)
        .withPrefWidth(60)
        .withMaxWidth(90)
        .build();
    ColumnConstraints columnCurrentGold = ColumnConstraintsBuilder.create() // Cell 5, 0
        .withMinWidth(60)
        .withPrefWidth(80)
        .withMaxWidth(80)
        .build();
    ColumnConstraints columnLabelScore = ColumnConstraintsBuilder.create() // Cell 6, 0
        .withMinWidth(60)
        .withPrefWidth(60)
        .withMaxWidth(90)
        .build();
    ColumnConstraints columnCurrentScore = ColumnConstraintsBuilder.create() // Cell 7, 0
        .withMinWidth(60)
        .withPrefWidth(80)
        .withMaxWidth(80)
        .build();
    ColumnConstraints columnSpacer = ColumnConstraintsBuilder.create() // Cell 8, 0
        .withMinWidth(60)
        .build();
    columnSpacer.setHgrow(Priority.ALWAYS);
    ColumnConstraints columnMenuButtonCell = ColumnConstraintsBuilder.create() // Cell 9, 0
        .withMinWidth(100)
        .withPrefWidth(100)
        .withMaxWidth(100)
        .build();

    topLayer.getColumnConstraints().addAll(
        columnLabelName,
        columnPlayerName,
        columnLabelHP,
        columnCurrentHP,
        columnLabelGold,
        columnCurrentGold,
        columnLabelScore,
        columnCurrentScore,
        columnSpacer,
        columnMenuButtonCell
    );

    String name = gameState.getPlayerName();
    String health = String.valueOf(gameState.getPlayer().getHealth());
    String gold = String.valueOf(gameState.getPlayer().getGold());
    String score = String.valueOf(gameState.getPlayer().getScore());

    Text labelPlayerName = new Text("Name: ");
    labelPlayerName.setStyle("-fx-font: 20 arial;");
    GridPane.setMargin(labelPlayerName, new Insets(4));
    Text playerName = new Text(name);
    playerName.setStyle("-fx-font: 18 arial;");
    GridPane.setHalignment(playerName, HPos.LEFT);

    int statusBarIconSizeWxH = 24;

    ImageView healthIconImageView = new ImageView(new Image(Constants.HEART_ICON_01));
    healthIconImageView.setFitWidth(statusBarIconSizeWxH);
    healthIconImageView.setFitHeight(statusBarIconSizeWxH);
    Text labelHP = new Text("HP: ");
    labelHP.setStyle("-fx-font: 20 arial;");
      HBox healthBox = new HBox(healthIconImageView, labelHP);
      healthBox.setAlignment(Pos.CENTER_LEFT);
    Text currentHP = new Text(health);
    currentHP.setStyle("-fx-font: 18 arial;");
    GridPane.setHalignment(currentHP, HPos.LEFT);

    ImageView goldIconImageView = new ImageView(new Image(Constants.GOLD_ICON_01));
    goldIconImageView.setFitWidth(statusBarIconSizeWxH);
    goldIconImageView.setFitHeight(statusBarIconSizeWxH);
    Text labelGold = new Text("Gold: ");
    labelGold.setStyle("-fx-font: 20 arial;");
      HBox goldBox = new HBox(goldIconImageView, labelGold);
      goldBox.setAlignment(Pos.CENTER_LEFT);
    Text currentGold = new Text(gold);
    currentGold.setStyle("-fx-font: 18 arial;");
    GridPane.setHalignment(currentGold, HPos.LEFT);

    ImageView scoreIconImageView = new ImageView(new Image(Constants.SCORE_ICON_01));
    scoreIconImageView.setFitWidth(statusBarIconSizeWxH);
    scoreIconImageView.setFitHeight(statusBarIconSizeWxH);
    Text labelScore = new Text("Score: ");
    labelScore.setStyle("-fx-font: 20 arial;");
      HBox scoreBox = new HBox(scoreIconImageView, labelScore);
      scoreBox.setAlignment(Pos.CENTER_LEFT);
    Text currentScore = new Text(score);
    currentScore.setStyle("-fx-font: 18 arial;");
    GridPane.setHalignment(currentScore, HPos.LEFT);

    Pane spacerPane = new Pane();
    spacerPane.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(spacerPane, Priority.ALWAYS);

    Button buttonInGameMenu = new Button("Quit"); // To be a Menu Button at some point. Atm, just takes you to Main Menu
    buttonInGameMenu.setStyle("-fx-font: 18 arial;");
    buttonInGameMenu.setPrefSize(90, 20);
    buttonInGameMenu.setOnAction(event -> backToMainMenu());
    GridPane.setHalignment(buttonInGameMenu, HPos.RIGHT);

    topLayer.add(labelPlayerName, 0, 0);
    topLayer.add(playerName, 1, 0);
    topLayer.add(healthBox, 2, 0);
    topLayer.add(currentHP, 3, 0);
    topLayer.add(goldBox, 4, 0);
    topLayer.add(currentGold, 5, 0);
    topLayer.add(scoreBox, 6, 0);
    topLayer.add(currentScore, 7, 0);
    topLayer.add(spacerPane, 8, 0);
    topLayer.add(buttonInGameMenu, 9, 0);

    topLayer.setPrefHeight(36);
//    topLayer.setAlignment(Pos.CENTER);
    return topLayer;
  }

  /**
   * Method for building Middle Layer.
   * Adds all elements to midLayer and returns same.
   * Holds the "scene" of the game, i.e. where the character models etc. appears and plays out.
   *
   * @return midLayer
   */
  private StackPane buildMidLayer() {
    // TODO Make background fetch depending on passage/link/stuff
    // TODO Make character model and NPC models appear
    StackPane midLayer = new StackPane();
    Image currentSceneBackground= new Image(Constants.IMG_FOREST_GAME_01);
    BackgroundImage background = new BackgroundImage(
        currentSceneBackground,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    midLayer.setBackground(new Background(background));

    // Making the "stage" on which the graphical part of the game plays out
    HBox gameField = new HBox();

    // Player character image
    Image imagePlayer = gameState.getPlayer().getPlayerSpecializationImage();
    ImageView playerImageArea = new ImageView(imagePlayer);
    playerImageArea.setFitHeight(600);
    playerImageArea.setFitWidth(400);
    // Box for actions and stuff between PC and NPC
    VBox someActions = new VBox();


    // Non-player character image
    Image imageNpc = new Image(Constants.CLOUD_01);

    ImageView npcImageArea = new ImageView(imageNpc);
    npcImageArea.setFitHeight(600);
    npcImageArea.setFitWidth(400);

    gameField.getChildren().add(playerImageArea);
    gameField.getChildren().add(someActions);
    gameField.getChildren().add(npcImageArea);

    HBox.setHgrow(playerImageArea, Priority.ALWAYS);
//    playerImageArea.fitWidthProperty().bind(gameField.widthProperty().divide(3));

    HBox.setHgrow(someActions, Priority.ALWAYS);

    HBox.setHgrow(npcImageArea, Priority.ALWAYS);
//    npcImageArea.fitWidthProperty().bind(gameField.widthProperty().divide(3));

    playerImageArea.setPreserveRatio(true);
    npcImageArea.setPreserveRatio(true);

    gameField.setAlignment(Pos.CENTER);


    VBox.setVgrow(midLayer, Priority.ALWAYS);
    midLayer.getChildren().addAll(gameField);
    return midLayer;
  }

  /**
   * Method for building Bottom Layer.
   * Adds all elements to bottomLayer and returns same.
   * Holds Inventory view, TextArea for displaying story, and area for showing
   * buttons with Link/Reference dialogue choices.
   *
   * @return bottomLayer
   */
  private HBox buildBottomLayer() {
    HBox bottomLayer = new HBox();
    VBox inventoryBox = new VBox();
    inventoryBox.setAlignment(Pos.TOP_CENTER);
    Label inventoryLabel = new Label("Inventory");
    inventoryLabel.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");

    GridPane inventoryGrid = new GridPane();
    HBox dialogueContainer = new HBox();

    int inventoryCellWidth = 64;
    int inventoryCellHeight = 64;

    // Inventory Grid 2x2
    ColumnConstraints columnLeft = ColumnConstraintsBuilder.create()
      .withMinWidth(inventoryCellWidth)
      .withPrefWidth(inventoryCellWidth)
      .withMaxWidth(inventoryCellWidth)
      .build();

    ColumnConstraints columnRight = ColumnConstraintsBuilder.create()
      .withMinWidth(inventoryCellWidth)
      .withPrefWidth(inventoryCellWidth)
      .withMaxWidth(inventoryCellWidth)
      .build();

    RowConstraints rowFirst = RowConstraintsBuilder.create()
      .withMinHeight(inventoryCellHeight)
      .withPrefHeight(inventoryCellHeight)
      .withMaxHeight(inventoryCellHeight)
      .build();
    RowConstraints rowSecond = RowConstraintsBuilder.create()
      .withMinHeight(inventoryCellHeight)
      .withPrefHeight(inventoryCellHeight)
      .withMaxHeight(inventoryCellHeight)
      .build();


    inventoryGrid.getColumnConstraints().addAll(columnLeft, columnRight);
    inventoryGrid.getRowConstraints().addAll(rowFirst, rowSecond);

    Image inventoryItem1 = new Image("file:src/main/resources/items/128x128/tome.png");
    Image inventoryItem2 = new Image("file:src/main/resources/items/128x128/scroll.png");
    Image inventoryItem3 = new Image("file:src/main/resources/items/128x128/tools.png");
    Image inventoryItem4 = new Image("file:src/main/resources/items/128x128/sword2.png");

    ImageView inventorySlot1 = new ImageView(inventoryItem1);
    inventorySlot1.setFitWidth(inventoryCellWidth);
    inventorySlot1.setFitHeight(inventoryCellHeight);
    ImageView inventorySlot2 = new ImageView(inventoryItem2);
    inventorySlot2.setFitWidth(inventoryCellWidth);
    inventorySlot2.setFitHeight(inventoryCellHeight);
    ImageView inventorySlot3 = new ImageView(inventoryItem3);
    inventorySlot3.setFitWidth(inventoryCellWidth);
    inventorySlot3.setFitHeight(inventoryCellHeight);
    ImageView inventorySlot4 = new ImageView(inventoryItem4);
    inventorySlot4.setFitWidth(inventoryCellWidth);
    inventorySlot4.setFitHeight(inventoryCellHeight);

    inventoryGrid.add(inventorySlot1, 0, 0);
    inventoryGrid.add(inventorySlot2, 1, 0);
    inventoryGrid.add(inventorySlot3, 0, 1);
    inventoryGrid.add(inventorySlot4, 1, 1);

    inventoryBox.getChildren().add(inventoryLabel);
    inventoryBox.getChildren().add(inventoryGrid);

    // Load paths story
    Passage openingPassage = story.getOpeningPassage();

    storyTextArea = new TextArea(openingPassage.getContent());
    storyTextArea.setWrapText(true);
    storyTextArea.setMinHeight(160);
    storyTextArea.setEditable(false);
    storyTextArea.setStyle("-fx-font: 16 arial;");

    dialogueLinkButtonArea = new FlowPane();
    dialogueLinkButtonArea.setHgap(8);
    dialogueLinkButtonArea.setVgap(8);
    dialogueLinkButtonArea.setPadding(new Insets(8, 8, 8, 8));

    dialogueLinkButtonArea.getChildren().addAll(buildLinkButtons(openingPassage));

    dialogueContainer.getChildren().addAll(storyTextArea, dialogueLinkButtonArea);

    bottomLayer.setMaxHeight(200);
    bottomLayer.setPrefHeight(160);
    bottomLayer.setMinHeight(160);
//    bottomLayer.setPrefWidth(2650);
    bottomLayer.setStyle("-fx-background-color: #767676");

    bottomLayer.getChildren().add(inventoryBox);
    bottomLayer.getChildren().add(dialogueContainer);

    return bottomLayer;
  }

  /**
   * Helper method to build link buttons for navigating the story.
   *
   * @param passage
   * @return buttons
   */
  private ArrayList<Button> buildLinkButtons(Passage passage) {
    ArrayList<Button> buttons = new ArrayList<>();
    for (Link link : passage.getLinks()) {
      Button button = new Button(link.getText());
      button.setOnAction(e -> {
        Passage nextPassage = story.getPassage(link);
        storyTextArea.setText(nextPassage.getContent());
        dialogueLinkButtonArea.getChildren().clear();
        dialogueLinkButtonArea.getChildren().addAll(buildLinkButtons(nextPassage));
        // Passes current Passage title to gameState.txt
        GameStateWriter.writeCurrentPassage(nextPassage.getTitle());
        GameState.getInstance().setPassage(nextPassage);

      });
      buttons.add(button);
    }
    return buttons;
  }

  /**
   * Method for navigating to Main Menu.
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
   * Get Singleton Instance.
   * Check if instance exists (i.e. is null). If not, then create it.
   * Then return instance.
   *
   * @param mainStage
   * @return INSTANCE
   */
  public static InGameView getInstance(Stage mainStage) {
    if (INSTANCE == null) { // Does the instance exist?
      INSTANCE = new InGameView(mainStage); // If not (i.e. null), then create it
    }
    return INSTANCE; // Return instance
  }

  /**
   * Return reference to mainStage from the instance.
   *
   * @return mainStage
   */
  public Stage getStageInGame() {
    return mainStage;
  }

  /**
   * Getter for class main scene.
   *
   * @return mainScene
   */
  public Scene getSceneInGame() {
    return mainScene;
  }

} // End of class InGameView