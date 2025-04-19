package edu.ntnu.idatt1002.baecon.controller;


import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class responsible for handling the settings category dialog.
 */
public class SettingsCategoryDialogController {
  private CategoriesController categoriesController;
  @FXML private Text titleCategoryDialogText;
  @FXML private TextField categoryNameTextField;
  @FXML private Text errorMessageText;
  @FXML private Button cancelButton;


  private boolean isNewCategory;
  private Stage currentStage;
  private Category category;

  /**
   * Method to initialize the controller.
   *
   * @throws IOException if the categories controller could not be initialized.
   */
  @FXML
  public void initialize() throws IOException {
    categoriesController = CategoriesControllerFactory.getCategoriesController();

    categoryNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
        errorMessageText.setVisible(false));
  }

  /**
   * Method to initialize the controller with data.
   *
   * @param isNewCategory {@code true} if the category is new, {@code false} otherwise.
   * @param currentStage the current stage.
   */
  public void initData(boolean isNewCategory, Stage currentStage) {
    this.isNewCategory = isNewCategory;
    this.currentStage = currentStage;
    titleCategoryDialogText.setText((isNewCategory ? "New" : "Edit") + " Category");
  }

  /**
   * Method to initialize the controller with data.
   *
   * @param isNewCategory {@code true} if the category is new, {@code false} otherwise.
   * @param currentStage the current stage.
   * @param category the category.
   */
  public void initData(boolean isNewCategory, Stage currentStage, Category category) {
    this.isNewCategory = isNewCategory;
    this.currentStage = currentStage;
    this.category = category;
    categoryNameTextField.setText(category.getName());
    titleCategoryDialogText.setText((isNewCategory ? "New" : "Edit") + " Category");
  }

  /**
   * Method to handle the cancel button.
   */
  @FXML
  public void onCancelButton() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Method to handle the apply button.
   */
  @FXML
  public void onApplyButton() {
    if (!categoryIsValid()) {
      return;
    }

    Category existingCategory = categoriesController
        .getCategoryByName(categoryNameTextField.getText());
    if (existingCategory != null && existingCategory != category) {
      errorMessageText.setText("Category already exists!");
      errorMessageText.setVisible(true);
      return;
    }

    if (isNewCategory) {
      Category category = new Category(categoryNameTextField.getText());
      categoriesController.addCategory(category);
    } else {
      category.setName(categoryNameTextField.getText());
      categoriesController.editCategory(category);
    }
    currentStage.close();
  }

  /**
   * Checks if the category is valid.
   *
   * @return {@code true} if the category is valid, {@code false} otherwise.
   */
  private boolean categoryIsValid() {
    boolean valid = true;
    if (categoryNameTextField.getText().isEmpty()) {
      errorMessageText.setText("Category name cannot be empty!");
      errorMessageText.setVisible(true);
      valid = false;
    } else if (categoryNameTextField.getText().contains(",")) {
      errorMessageText.setText("Category name cannot contain commas!");
      errorMessageText.setVisible(true);
      valid = false;
    }
    return valid;
  }
}