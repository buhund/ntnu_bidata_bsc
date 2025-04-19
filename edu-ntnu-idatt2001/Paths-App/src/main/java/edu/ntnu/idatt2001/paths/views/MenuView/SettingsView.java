package edu.ntnu.idatt2001.paths.views.menuView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class for Settings GUI and functionality.
 */
public class SettingsView {

    private static SettingsView INSTANCE;
    private final Stage mainStage;
    private final Scene sceneSettings;
    private final StackPane layoutSettings = new StackPane();


    /**
     * Settings menu view with basic functilnalty and temporary elements.
     *
     * @param primaryStage
     */
    public SettingsView(Stage primaryStage) {
        // TODO Import/add custom backgrounds
        this.mainStage = primaryStage;

        VBox settingsBox = new VBox();

        Label settingsLabel = new Label("Settings page");
        settingsLabel.setStyle("-fx-font: 48 arial; -fx-font-weight: bold; -fx-text-fill: black;");

        Label labelUnderConstruction1 = new Label("This page is currently under construction.");
        settingsLabel.setStyle("-fx-font: 32 arial; -fx-text-fill: black;");
        Label labelUnderConstruction2 = new Label("Please check back later!");
        settingsLabel.setStyle("-fx-font: 32 arial; -fx-text-fill: black;");

        Button buttonMainMenu = new Button("Main Menu");
        buttonMainMenu.setOnAction(event -> backToMainMenu());
        buttonMainMenu.setPrefSize(240, 50);
        buttonMainMenu.setStyle("-fx-font: 32 arial;");

        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.getChildren().addAll(settingsLabel, labelUnderConstruction1, labelUnderConstruction2, buttonMainMenu);

        layoutSettings.getChildren().add(settingsBox);
        sceneSettings = new Scene(layoutSettings);
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
     * @param mainStage
     * @return INSTANCE
     */
    public static SettingsView getInstance(Stage mainStage) {
        if (INSTANCE == null) { // Does the instance exist?
            INSTANCE = new SettingsView(mainStage); // If not (i.e. null), then create it
        }
        return INSTANCE; // Return instance
    }

    /**
     * Return reference to mainStage from the instance.
     *
     * @return mainStage
     */
    public Stage getStageSettings() {
        return mainStage;
    }

    /**
     * Getter for class main scene.
     *
     * @return mainScene
     */
    public Scene getSceneSettings() {
        return sceneSettings;
    }
}
