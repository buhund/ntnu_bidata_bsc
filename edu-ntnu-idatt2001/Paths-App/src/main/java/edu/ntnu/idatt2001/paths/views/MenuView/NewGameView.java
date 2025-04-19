/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.views.menuView;

import edu.ntnu.idatt2001.paths.assets.PlayerSpecializationEnum;
import edu.ntnu.idatt2001.paths.assets.Constants;
import edu.ntnu.idatt2001.paths.controller.GameState;
import edu.ntnu.idatt2001.paths.controller.GameStateWriter;
import edu.ntnu.idatt2001.paths.domain.Game;
import edu.ntnu.idatt2001.paths.domain.Player;
import edu.ntnu.idatt2001.paths.domain.Story;
import edu.ntnu.idatt2001.paths.domain.StoryParser;
import edu.ntnu.idatt2001.paths.domain.goals.*;
import edu.ntnu.idatt2001.paths.inputOutput.IntegerTextField;
import edu.ntnu.idatt2001.paths.views.ingameView.InGameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for the New Game GUI and functionality.
 */
public final class NewGameView {
  private static NewGameView INSTANCE;
  private final Stage mainStage;
  private final Scene sceneNewGame;


  // Class layouts
  private final VBox layoutNewGame = new VBox();
  private final TextField inputCharacterName;
  private TextField inputStartingHealth;
  private TextField inputStartingGold;
  private TextField inputStartingScore;
  private final Text textStartingItem;
  private static final String FILE_EXTENSION = ".paths";
  GameState gameState = GameState.getInstance();
  private final ComboBox<File> comboxSelectStory;

  private final Label storyDescriptionLabel;
  private final TextArea storyDescriptionArea;
  private final Pattern patternTitle = Pattern.compile("title=\"([^\"]*)\"");

  private int startingHP = 0;
  private int startingGold = 0;
  private int startingScore = 0;

  public Image selectedCharacterSpecializationImage;


  /**
   * Method building the New Game GUI.
   * Functionality in here can be refactored and separated out in smaller and neater methods.
   *
   * Builds a GUI in layers:
   * 1st layer:
   * - Top layer: Label, story selector combobox, and "Open new file" button.
   * - Middle layer: HBox with 3 horizontal layers.
   * - Bottom layer: Buttons for Main Menu, Save to file, and Start Game.
   *
   * @param primaryStage
   */
  public NewGameView(Stage primaryStage) {
    this.mainStage = primaryStage;
    setBackgroundImage();


    // Top Layer Columns and Rows. Set constraints
    GridPane topLayer = new GridPane();
    topLayer.setMaxHeight(160);
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
    labelNewGame.setStyle("-fx-font: 48 arial; -fx-font-weight: bold; -fx-text-fill: black;");
    // Center label in its cell
//    GridPane.setHalignment(labelNewGame, HPos.CENTER);
//    GridPane.setValignment(labelNewGame, VPos.CENTER);


    HBox selectGameContainer = new HBox();
    Label labelSelectStory = new Label("Select Game: ");
    labelSelectStory.setPadding(new Insets(4));
    labelSelectStory.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");

    Label selectedFileLabel = new Label("Selected Story: ");
    selectedFileLabel.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");
    Label fileNameLabel = new Label("No file selected");
    fileNameLabel.setStyle("-fx-font: 16 arial; -fx-text-fill: black;");


    storyDescriptionLabel = new Label("Story Description: ");
    storyDescriptionLabel.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");
    storyDescriptionArea = new TextArea("No story description available");
    storyDescriptionArea.setEditable(false);
    storyDescriptionArea.setDisable(false);
//      Pattern patternTitle = Pattern.compile("title=\"([^\"]*)\"");


    /**
     * Combobox for selecting a story file for the new game. This should be refactored out to its own method.
     * Reads only files in the specified directory, and only files with a given file extension (.paths).
     * Made to match with a RegEx and parse the Title and Description found.
     * THis functionality had been put on hold for the time being.
     * Will display the actual file name if no regex match is found.
     */
    comboxSelectStory = new ComboBox<>();
    File dir = new File("src/main/resources/story/");
    File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(FILE_EXTENSION));
    if (files != null) {
      comboxSelectStory.getItems().addAll(files);
    }

    comboxSelectStory.setCellFactory(listView -> new ListCell<>() {
      @Override
      protected void updateItem(File file, boolean empty) {
        super.updateItem(file, empty);
        if (file == null || empty) {
          setText(null);
        } else {
          try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
            String line;
            boolean foundTitle = false;
            while ((line = reader.readLine()) != null) {
              Matcher matcher = patternTitle.matcher(line);
              if (matcher.find()) {
                setText(matcher.group(1));
                foundTitle = true;
                break;
              }
            }
            if (!foundTitle) {
              setText(file.getAbsoluteFile().getName());
            }
          } catch (IOException e) {
            setText("Error reading file");
          }
        }
      }
    });
    comboxSelectStory.setButtonCell(comboxSelectStory.getCellFactory().call(null));

    comboxSelectStory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//      GameState gameState = GameState.getINSTANCE();
      gameState.setSelectedStoryFile(newValue);
      if (newValue != null) {
        fileNameLabel.setText(newValue.getName());
        //storyDescriptionArea.setText(newValue.getName());
      }
    });
    comboxSelectStory.getSelectionModel().selectFirst();
    comboxSelectStory.setPrefSize(300, 20);
    comboxSelectStory.setStyle("-fx-font: 16 arial;");
    selectGameContainer.getChildren().addAll(labelSelectStory, comboxSelectStory);

    HBox openFileContainer = new HBox();
    Label labelOpenFile = new Label("Open New Game File: ");
    labelOpenFile.setPadding(new Insets(4));
    labelOpenFile.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");

    /**
     * Open new file.
     * I.e. copy selected file to story directory.
     * This should be refactored out to its own method.
     */
    Button buttonOpenFile = new Button("Open File …");
    buttonOpenFile.setPrefSize(140, 20);
    buttonOpenFile.setStyle("-fx-font: 16 arial;");
    buttonOpenFile.setOnAction(event -> {
      openPathsFile();
      comboxSelectStory.getItems().clear();
      File[] updatedFiles = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(FILE_EXTENSION));
      if (updatedFiles != null) {
        comboxSelectStory.getItems().addAll(updatedFiles);
      }
    });
    openFileContainer.getChildren().addAll(labelOpenFile, buttonOpenFile);

    // Top Layer add elements
    topLayer.add(labelNewGame, 0, 0);
    topLayer.add(selectGameContainer, 0, 1);
    topLayer.add(openFileContainer, 1, 1);


    // Middle Layer and constraints
    HBox midLayer = new HBox();
    HBox.setHgrow(midLayer, Priority.ALWAYS);

    VBox midLayerLeft = new VBox();
    midLayerLeft.setStyle("-fx-background-color: #E5BD87"); // TODO Find a proper background for this area.
    Label labelSelectClass = new Label("Select class: ");
    labelSelectClass.setStyle("-fx-font: 18 arial; -fx-text-fill: black;");

    Label labelCharacterName = new Label("Name: ");
    labelCharacterName.setStyle("-fx-font: 18 arial; -fx-text-fill: black");
    inputCharacterName = new TextField("Generik Generiksen");
    inputCharacterName.setEditable(true);
    inputCharacterName.setDisable(false);
    inputCharacterName.setMaxWidth(160);


    ComboBox<PlayerSpecializationEnum> comboxSelectClass = new ComboBox<>(FXCollections.observableArrayList(PlayerSpecializationEnum.values()));
    // TODO Tie characterModel to a starting item
    Label labelStartingItem = new Label("Starting item: ");
    labelStartingItem.setStyle("-fx-font: 18 arial; -fx-text-fill: black");
    textStartingItem = new Text("Pocket lint");

    Label errorLabelHealth = new Label("");
    Label labelStartingHealth = new Label("Starting HP: ");
    labelStartingHealth.setStyle("-fx-font: 18 arial; -fx-text-fill: black");
    inputStartingHealth = createIntegerTextFieldWithLabel(errorLabelHealth);
    inputStartingHealth.setMaxWidth(160);


    Label errorLabelGold = new Label("");
    Label labelStartingGold = new Label("Starting Gold: ");
    labelStartingGold.setStyle("-fx-font: 18 arial; -fx-text-fill: black");
    inputStartingGold = createIntegerTextFieldWithLabel(errorLabelGold);
    inputStartingGold.setMaxWidth(160);

    Label errorLabelScore = new Label("");
    Label labelStartingScore = new Label("Starting Score: ");
    labelStartingScore.setStyle("-fx-font: 18 arial; -fx-text-fill: black");
    inputStartingScore = createIntegerTextFieldWithLabel(errorLabelScore);
    inputStartingScore.setMaxWidth(160);


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


    /**
     * Combobox for selecting a character class/model/specialization for the new game. This should be refactored out to its own method.
     * Uses enum class to get name and image.
     */

    comboxSelectClass.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      String imagePath = newValue.getImagePath();
      String startingItem = newValue.getStartingItem();
//      GameState gameState = GameState.getINSTANCE();
      // Set player Spec to gameState
      gameState.setPlayerSpecialization(newValue);

      if (imagePath != null) {
        try {
          Image newImage = new Image(new FileInputStream(imagePath));
          characterModelView.setImage(newImage);
          this.selectedCharacterSpecializationImage = newImage;
        } catch (FileNotFoundException exception) {
          System.err.println("Image not found: " + exception.getMessage());
        }
      } else {
        characterModelView.setImage(null);
        this.selectedCharacterSpecializationImage = null;
      } if (!Objects.equals(startingItem, "")) {
        textStartingItem.setText(startingItem);
      } else {
        textStartingItem.setText("Empty pockets");
      }
    });
    comboxSelectClass.getSelectionModel().selectFirst();

    HBox selectedStoryNameBox = new HBox(selectedFileLabel, fileNameLabel);
    selectedStoryNameBox.setAlignment( Pos.CENTER_LEFT);
    selectedStoryNameBox.setSpacing(8);
    VBox.setMargin(selectedStoryNameBox, new Insets(10, 0, 10, 0));

    midLayerLeft.getChildren().addAll(
        labelSelectClass, comboxSelectClass,
        labelStartingItem, textStartingItem,
        labelCharacterName, inputCharacterName,
        labelStartingHealth, inputStartingHealth, errorLabelHealth,
        labelStartingGold, inputStartingGold, errorLabelGold,
        labelStartingScore, inputStartingScore, errorLabelScore,
        selectedStoryNameBox,
        storyDescriptionLabel,
        storyDescriptionArea);

    HBox.setHgrow(midLayerLeft, Priority.ALWAYS);

    Pane midLayerRight = new Pane();

    HBox.setHgrow(midLayerRight, Priority.ALWAYS);

    midLayerRight.getChildren().add(goalSelectorArea());


    // Middle Layer add elements
    midLayer.getChildren().addAll(midLayerLeft, midLayerMiddle, midLayerRight);


    // Bottom Layer and constraints
    GridPane bottomLayer = new GridPane();
    bottomLayer.setMaxHeight(60);
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
    Button buttonStartGame = new Button("Start Game");

    /**
     * Start game, and save all settings to file and handling methods.
     */
    buttonStartGame.setPrefSize(240, 50);
    buttonStartGame.setStyle("-fx-font: 32 arial;");
    buttonStartGame.setOnAction(event -> {
      saveSelectionsToFile();
      setCurrentPlayer();
      setGameState();
      toInGameScene();
    });

    /**
     * Save the current game settings to file.
     */
    Button buttonSaveCurrentCharacter = new Button("Save Selection");
    buttonSaveCurrentCharacter.setOnAction(e -> {
      saveSelectionsToFile();
      setCurrentPlayer();
      setGameState();
    });


    Button buttonMainMenu = new Button("Main Menu");
    // TODO Singleton of MainMenu?

    buttonMainMenu.setOnAction(event -> backToMainMenu());
    buttonMainMenu.setPrefSize(240, 50);
    buttonMainMenu.setStyle("-fx-font: 32 arial;");


    // Middle Layer add elements
    bottomLayer.add(buttonMainMenu, 0, 0);
    bottomLayer.add(buttonSaveCurrentCharacter, 1, 0);
    bottomLayer.add(buttonStartGame, 2, 0);

    // Add layers to VBox
    layoutNewGame.getChildren().addAll(topLayer, midLayer, bottomLayer);
    VBox.setVgrow(midLayer, Priority.ALWAYS);
    // TODO Make midLayer expand all the way vertically
    // TODO I.e. make bottom and top stay on bottom and top


    // Set the column constraints to center the buttons horizontally
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    // layoutNewGame.getColumnConstraints().add(columnConstraints);

    // setCenter(layoutNewGame);
    layoutNewGame.setPadding(new Insets(10, 10, 10, 10));

    sceneNewGame = new Scene(layoutNewGame);
  }


  /**
   * File Picker for copying selected file to story directory.
   * Does not filter for .paths extension.
   * Users must pick the correct file themselves.
   */
  private void openPathsFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import .paths file");
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      String targetDirectory = "src/main/resources/story/";
      Path targetPath = Paths.get(targetDirectory, selectedFile.getName());
      try {
        Files.copy(selectedFile.toPath(), targetPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * Helper method for creating TextField that only accept int and adds a error message label.
   *
   * @param errorLabel
   * @return
   */
  private TextField createIntegerTextFieldWithLabel(Label errorLabel) {
//    Label label = new Label(labelText);
    TextField textField = new IntegerTextField(errorLabel);

    return textField;
  }

  /**
   * Helper method from parsing input in TextField from String to int.
   * If no input, then return default value set in constructor.
   *
   * @param textField
   * @return parseInt, or defaultValue
   */
  public int parseIntegerFromTextField(TextField textField, int defaultValue) {
    if (!textField.getText().isEmpty()) {
      return Integer.parseInt(textField.getText());
    } else {
      return defaultValue;
    }
  }



  /**
   * Helper method for setting background image.
   */
  private void setBackgroundImage() {
    // Load background image
    Image backgroundImageWatcher02 = new Image(Constants.IMG_WATCHER_02_BLUE);

    // Create a background image with the loaded image and set it to the layout container
    BackgroundImage background = new BackgroundImage(
      backgroundImageWatcher02,
      BackgroundRepeat.NO_REPEAT,
      BackgroundRepeat.NO_REPEAT,
      BackgroundPosition.DEFAULT,
      new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    layoutNewGame.setBackground(new Background(background));
  }

  /**
   * Save the current selected game
   * character
   * specialization/model
   * character name
   * starting Health, Gold, Score
   * story (file path) and starting item to file (.txt).
   */
  public void saveSelectionsToFile() {
    // TODO Add error handling for "empty" selection
//    GameState gameState = GameState.getINSTANCE();
    PlayerSpecializationEnum selectedPlayerSpecializationEnum = gameState.getPlayerSpecialization();
    Image selectedPlayerSpecializationIMG = gameState.getPlayerCharacterImage();
    File selectedStoryFile = gameState.getSelectedStoryFile();
    String characterName = inputCharacterName.getText();
    int health = startingHP;
    int gold = startingGold;
    int score = startingScore;

    GameStateWriter.writeGameStateToFile(selectedPlayerSpecializationEnum, selectedStoryFile, characterName, health, score, gold);
  }

  /**
   * Save the current selected character class/model, character name,
   * story (file path) and starting item to GameState class.
   */
  public void setGameState() {
    // TODO Add error handling for "empty" selection
    GameState gameState = GameState.getInstance();
    PlayerSpecializationEnum selectedPlayerSpecializationEnum = gameState.getPlayerSpecialization();
    File selectedStoryFile = gameState.getSelectedStoryFile();
    String storyPath = selectedStoryFile.getPath();

    Story story = StoryParser.parseStoryFromFile(storyPath);

    String characterName = inputCharacterName.getText();
    String startingItem = textStartingItem.getText();
    String playerSpecialization = selectedPlayerSpecializationEnum.toString();
    Image playerSpecializationImage = selectedCharacterSpecializationImage;
    List<String> inventory = new ArrayList<>();
    inventory.add(startingItem);
    List<Goal> goals = new ArrayList<>();

    this.startingHP = parseIntegerFromTextField(inputStartingHealth, 100);
    this.startingGold = parseIntegerFromTextField(inputStartingGold, 0);
    this.startingScore = parseIntegerFromTextField(inputStartingScore, 0);

    int health = startingHP;
    int gold = startingGold;
    int score = startingScore;

    gameState.setSelectedStoryFile(selectedStoryFile);
    gameState.setPlayerSpecialization(selectedPlayerSpecializationEnum);
    gameState.setPlayerCharacterImage(selectedCharacterSpecializationImage);
    gameState.setPlayerName(characterName);
    gameState.setStartingItem(startingItem);
    gameState.setStartingItemIcon(selectedPlayerSpecializationEnum.getStartingItemIcon());
    gameState.setStartingHP(health);
    gameState.setStartingGold(gold);
    gameState.setStartingScore(score);
    Player player = new Player(characterName, health, score, gold, inventory, playerSpecialization, playerSpecializationImage);
    Game game = new Game(player, story, goals);
    gameState.setPlayer(player);
  }

  /**
   * Helper method for setting values selected
   * or inputted in NewGameView to a Player object.
   */
  public void setCurrentPlayer() {
    GameState gameState = GameState.getInstance();

    String characterName = inputCharacterName.getText();

    startingHP = parseIntegerFromTextField(inputStartingHealth, 100);
    startingGold = parseIntegerFromTextField(inputStartingGold, 0);
    startingScore = parseIntegerFromTextField(inputStartingScore, 0);

    int health = startingHP;
    int score = startingScore;
    int gold = startingGold;

    List<String> inventory = new ArrayList<>();
    Image playerSpecImage = selectedCharacterSpecializationImage;
    String playerSpecialization = gameState.getPlayerSpecialization().toString();

    String startingItem = textStartingItem.getText();
    inventory.add(startingItem);

    Player player = new Player(characterName, health, score, gold, inventory, playerSpecialization, playerSpecImage);
    gameState.PlayerCharacter(player);
  }

  /**
   * GUI area for managing game goals.
   * Builds:
   * ListView for showing goals.
   * Buttons for add/delete goals.
   * ComboBox for selecting goal type.
   * TextField for input goal.
   *
   * @return VBox goalsBox
   */
  public VBox goalSelectorArea() {
    ObservableList<Goal> goals = FXCollections.observableArrayList();
//    List<Goal> goals = new ArrayList<>();
    ComboBox<String> comboxGoalType = new ComboBox<>();
    comboxGoalType.getItems().addAll("Health Goal",
                                          "Gold Goal",
                                          "Score Goal",
                                          "Item Goal"
    );

    TextField fieldGoalValue = new TextField();
    Button buttonAddGoal = new Button("Add Goal");
    buttonAddGoal.setOnAction(actionEvent -> {
      String selectedGoalType = comboxGoalType.getValue();
//      int goalValue = Integer.parseInt(fieldGoalValue.getText());
      String goalValue = fieldGoalValue.getText();
//      String goalItem = fieldItem.getText();
      try {
        switch (selectedGoalType) {
          case "Health Goal" -> goals.add(new HealthGoal(Integer.parseInt(goalValue)));
          case "Gold Goal" -> goals.add(new GoldGoal(Integer.parseInt(goalValue)));
          case "Score Goal" -> goals.add(new ScoreGoal(Integer.parseInt(goalValue)));
          case "Item Goal" -> goals.add(new ItemGoal(goalValue));
        }
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        // TODO Handle the exception
      }

    }); // End of buttonAddGoal.setOnAction

    ListView<Goal> goalList = new ListView<>(goals);

    Button deleteButton = new Button("Delete Goal");
    deleteButton.setOnAction(e -> {
      Goal selectedGoal = goalList.getSelectionModel().getSelectedItem();
      if (selectedGoal != null) {
        goals.remove(selectedGoal);
      }
    });

    VBox goalsBox = new VBox();
    goalsBox.getChildren().addAll(comboxGoalType,
                                  fieldGoalValue, buttonAddGoal,
                                  deleteButton,
                                  goalList);

    return goalsBox;
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
   * Method for Starting the game (i.e. set In-Game Scene).
   * Used for button.setOnAction event.
   * Call on Singleton instance and set to mainStage.
   * Get Instance of class, then get Scene.
   * Set Scene on mainStage.
   */
  private void toInGameScene () {
    System.out.println("Start Game button pressed");
    saveSelectionsToFile(); // Save character and story to file

    Scene sceneInGame = InGameView.getInstance(this.mainStage).getSceneInGame();
    this.mainStage.setScene(sceneInGame);
  }

  /**
   * Get Singleton Instance.
   * Check if instance exists (i.e. is null). If not, then create it.
   * Then return instance.
   *
   * @param mainStage
   * @return INSTANCE
   */
  public static NewGameView getInstance(Stage mainStage) {
    if (INSTANCE == null) { // Do the instance exist?
      INSTANCE = new NewGameView(mainStage); // If not (i.e. null), then create it
    }
    return INSTANCE; // Return instance
  }

  /**
   * Return reference to mainStage from the instance.
   *
   * @return mainStage
   */
  public Stage getStageNewGame() {
    return mainStage;
  }

  /**
   * Return scene from the instance.
   *
   * @return sceneNewGame
   */
  public Scene getSceneNewGame() {
    return sceneNewGame;
  }


} // End of class NewGameView

