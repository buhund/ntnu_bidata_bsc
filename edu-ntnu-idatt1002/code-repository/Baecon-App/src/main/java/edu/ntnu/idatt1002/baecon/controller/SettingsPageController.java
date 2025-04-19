package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.ErrorAlert;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.factory.AccountingEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.BudgetEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Class responsible for handling the settings page.
 */
public class SettingsPageController implements CategoriesChangeListener, SettingsListener {

  private SettingsController settingsController;
  private String currency = "NOK";
  @FXML ListView<String> categoriesListView;
  @FXML private ComboBox<String> currencyPicker;
  @FXML CheckBox negativeNumbersRed;
  @FXML CheckBox colorBlindMode;

  @FXML private Button editCategoryButton;
  @FXML private Button deleteCategoryButton;

  private CategoriesController categoriesController;

  /**
   * Method to initialize the settings page.
   *
   * @throws IOException If the settings file cannot be found
   * @throws ParseException If the settings file cannot be parsed
   */
  @FXML
  public void initialize() throws IOException, ParseException {
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    categoriesController.addSubscriber(this);

    settingsController = SettingsControllerFactory.getSettingsController();
    settingsController.addSubscriber(this);

    categoriesController.getCategories().forEach(category -> categoriesListView.getItems()
        .add(category.getName()));

    currencyPicker.getItems().addAll("NOK", "EUR", "USD", "GBP");
    currencyPicker.setValue(settingsController.getCurrency());
    currencyPicker.setOnAction(event -> {
      currency = currencyPicker.getValue();
      settingsController.setCurrency(currency);
    });

    negativeNumbersRed.setSelected(settingsController.getNegativeNumbersInRed());
    negativeNumbersRed.setOnAction(event -> settingsController
        .setNegativeNumbersInRed(negativeNumbersRed.isSelected()));

    colorBlindMode.setSelected(settingsController.getColorBlindMode());
    colorBlindMode.setOnAction(event -> settingsController.setColorBlindMode(colorBlindMode
        .isSelected()));

    categoriesListView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {
            editCategoryButton.setDisable(false);
            deleteCategoryButton.setDisable(false);
          } else {
            editCategoryButton.setDisable(true);
            deleteCategoryButton.setDisable(true);
          }
        });
  }

  /**
   * Method to open a new-category-dialog.
   *
   * @throws IOException If the new-category-dialog cannot be found
   */
  @FXML
  void onSettingsNewCategoryDialog() throws IOException {
    Stage newCategoryStage = new Stage();
    newCategoryStage.setTitle("Baecon - New Category");
    FXMLLoader settingsCategoryDialogFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.SETTINGS_CATEGORY_ENTRY.getFileName())));
    settingsCategoryDialogFXML.load();

    SettingsCategoryDialogController setCatDiaController = settingsCategoryDialogFXML
        .getController();
    setCatDiaController.initData(true, newCategoryStage);


    newCategoryStage.setScene(new Scene(settingsCategoryDialogFXML.getRoot()));
    newCategoryStage.show();
  }

  /**
   * Method to open an edit-category-dialog.
   *
   * @throws IOException If the edit-category-dialog cannot be found
   */
  @FXML
  void onSettingsEditCategoryDialog() throws IOException {
    Stage editCategoryStage = new Stage();
    editCategoryStage.setTitle("Baecon - Edit Category");
    FXMLLoader settingsCategoryDialogFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.SETTINGS_CATEGORY_ENTRY.getFileName())));
    settingsCategoryDialogFXML.load();

    SettingsCategoryDialogController setCatDiaController = settingsCategoryDialogFXML
        .getController();
    setCatDiaController.initData(false, editCategoryStage,
        categoriesController.getCategoryByName(categoriesListView.getSelectionModel()
        .getSelectedItem()));

    editCategoryStage.setScene(new Scene(settingsCategoryDialogFXML.getRoot()));
    editCategoryStage.show();

  }

  /**
   * Method to open a confirmation dialog for deleting a category.
   */
  @FXML
  void onSettingsDeleteCategoryConfirmation() {
    String selectedCategory = categoriesListView.getSelectionModel().getSelectedItem();
    if (selectedCategory == null) {
      // If no category is selected, show an error message and return
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Baecon - Error");
      alert.setHeaderText("No category selected");
      alert.setContentText("Please select a category to delete.");
      alert.showAndWait();
      return;
    }

    // Create the confirmation dialog
    Dialog<ButtonType> deleteCategoryDialog = new Dialog<>();
    deleteCategoryDialog.setTitle("Baecon - Delete Category");
    deleteCategoryDialog.setHeaderText("Are you sure you want to delete category "
        + selectedCategory + "?");

    // Set the dialog buttons
    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    deleteCategoryDialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

    // Set the result converter for the dialog
    deleteCategoryDialog.setResultConverter(buttonType -> {
      if (buttonType == yesButton) {
        return buttonType;
      }
      return null;
    });

    // Show the dialog and wait for the user to make a choice
    Optional<ButtonType> result = deleteCategoryDialog.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
      // Delete all budget entries with the selected category
      BudgetEntriesController budgetController = BudgetEntriesControllerFactory
          .getBudgetEntriesController();
      AccountingEntriesController accountingEntriesController = AccountingEntriesControllerFactory
          .getAccountingEntriesController();
      try {
        budgetController.deleteBudgetEntriesByCategory(categoriesController
            .getCategoryByName(selectedCategory).getId());
        accountingEntriesController.deleteAccountingEntriesByCategory(categoriesController
            .getCategoryByName(selectedCategory).getId());
      } catch (IOException ioException) {
        ErrorAlert.show("Could not delete budget entries with given category",
            ioException.getMessage());
      }
      categoriesController.deleteCategory(categoriesController.getCategoryByName(selectedCategory));
    }
  }

  /**
   * Method to open the 'documents' folder.
   */
  @FXML
  public void onOpenDocumentsLocation() {
    String path = "src/main/resources/edu/ntnu/idatt1002/baecon/documents";
    File file = new File(path);
    if (Desktop.isDesktopSupported() && file.exists()) {
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException e) {
        ErrorAlert.show("Could not open documents folder", e.getMessage());
      }
    }
  }

  @Override
  public void updateCategories(List<Category> categories, Category newestCategory) {
    categoriesListView.getItems().clear();
    categories.forEach(category -> categoriesListView.getItems().add(category.getName()));
  }

  /**
   * Updates the currencyPicker with the currency from the newSettings.
   *
   * @param newSettings the new user settings containing the updated currency information
   */
  @Override
  public void updateSettings(Settings newSettings) {
    currencyPicker.setValue(newSettings.getCurrency());
  }
}
