package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.NumberAndPunctuationTextField;
import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.factory.BudgetEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class responsible for handling the add budget entry view.
 */
public class AddBudgetEntryController implements CategoriesChangeListener {
  private static final String expectedAmountRegex = "([0-9]+([.,][0-9]{0,2})?|[.,;:!?'\"-])";
  @FXML private Text titleEntryText;
  @FXML private Label expectedAmountLabel;
  @FXML private Text expectedAmountErrorMessage;
  @FXML private NumberAndPunctuationTextField expectedAmountTextField;
  @FXML private Text currencyText;
  @FXML private ComboBox<String> categoryNameComboBox;
  @FXML private CheckBox repeatCheckBox;
  @FXML private ComboBox<String> repeatComboBox;
  @FXML private Button cancelEntryButton;

  private boolean isExpense;
  private Stage currentStage;
  private BudgetEntriesController budgetEntriesController;
  private CategoriesController categoriesController;
  private LocalDate date;

  /**
   * Initialize the FXML file with custom logic.
   * Fill categoryNameComboBox with the loaded categories.
   * Add listener to repeatCheckBox to make repeatComboBox visible when checked.
   * Fill repeatCheckBox with values from repeatOptions.
   *
   * @throws IOException io exception
   */
  @FXML
  public void initialize() throws IOException {
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    categoriesController.getCategories().forEach(category -> {
      categoryNameComboBox.getItems().add(category.getName());
      if (categoryNameComboBox.getValue() == null) {
        categoryNameComboBox.setValue(category.getName());
      }
    });
    categoriesController.addSubscriber(this);
    budgetEntriesController = BudgetEntriesControllerFactory.getBudgetEntriesController();

    currencyText.setText(SettingsControllerFactory.getSettingsController().getCurrency());

    expectedAmountErrorMessage.setVisible(false);

    cancelEntryButton.setOnAction(event -> cancelEntryButtonAction());

    repeatComboBox.setDisable(true);
    repeatComboBox.getItems().addAll("Monthly", "Quarterly", "Yearly");
    repeatComboBox.setValue("Monthly");
    repeatCheckBox.setOnAction(event -> {
      boolean selected = repeatCheckBox.isSelected();
      repeatComboBox.setDisable(!selected);
    });
  }

  /**
   * Method to init budget view with isExpense as a variable.
   *
   * @param isExpense is expense
   */
  public void initData(boolean isExpense, Stage currentStage, LocalDate date) {
    this.isExpense = isExpense;
    this.currentStage = currentStage;
    this.date = date;

    titleEntryText.setText("New " + (isExpense ? "Expense" : "Income"));
    expectedAmountLabel.setText(isExpense ? "Expected expense" : "Expected income");
  }

  /**
   * Method to add a new budget entry.
   */
  @FXML
  public void addNewBudgetEntry() {
    try {
      Category chosenCategory = categoriesController
          .getCategoryByName(categoryNameComboBox.getValue());
      expectedAmountTextField.setOnKeyTyped(event -> expectedAmountErrorMessage.setVisible(false));
      if (!inputsAreValid()) {
        return;
      }

      double amount = Double.parseDouble(expectedAmountTextField.getText());

      BudgetEntry newBudgetEntry = new BudgetEntry(isExpense, amount, chosenCategory.getId());

      if (repeatCheckBox.isSelected()) {
        budgetEntriesController.addNewRepeatingBudgetEntry(newBudgetEntry, date,
            repeatComboBox.getValue());
      } else {
        budgetEntriesController.addNewBudgetEntry(newBudgetEntry, date);
      }
      // Close stage
      currentStage.close();
    } catch (Exception exception) {
      Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
      alert.show();
    }
  }

  /**
   * Method to cancel the adding of a new budget entry.
   */
  @FXML
  public void cancelEntryButtonAction() {
    Stage stage = (Stage) cancelEntryButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Method to add a new category.
   */
  @FXML
  public void onAddNewCategory() {
    try {
      Stage newCategoryStage = new Stage();
      newCategoryStage.setTitle("Baecon - New Category");
      FXMLLoader settingsCategoryDialogFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher
          .class.getResource(View.SETTINGS_CATEGORY_ENTRY.getFileName())));
      settingsCategoryDialogFXML.load();

      SettingsCategoryDialogController setCatDiaController = settingsCategoryDialogFXML
          .getController();
      setCatDiaController.initData(true, newCategoryStage);

      newCategoryStage.setScene(new Scene(settingsCategoryDialogFXML.getRoot()));
      newCategoryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Method to check if the inputs are valid.
   */
  private boolean inputsAreValid() {
    boolean valid = true;
    if (expectedAmountTextField.getText().matches(expectedAmountRegex)) {
      expectedAmountErrorMessage.setText("Enter a positive number with max 2 decimals!");
      expectedAmountErrorMessage.setVisible(true);
      expectedAmountErrorMessage.setFill(javafx.scene.paint.Color.RED);
    }
    return valid;
  }

  @Override
  public void updateCategories(List<Category> categories, Category newestCategory) {
    categoryNameComboBox.getItems().clear();
    categories.forEach(category -> categoryNameComboBox.getItems().add(category.getName()));
    categoryNameComboBox.setValue(newestCategory.getName());
  }
}
