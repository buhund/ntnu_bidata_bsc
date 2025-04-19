package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.NumberAndPunctuationTextField;
import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.factory.AccountingEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class responsible for handling the adding of a new accounting entry.
 */
public class EditAccountingEntryController {
  private static final String expectedAmountRegex = "([0-9]+([.,][0-9]{0,2})?|[.,;:!?'\"-])";
  @FXML private Text titleEntryText;
  @FXML private DatePicker datePicker;
  @FXML private Text expectedAmountErrorMessage;
  @FXML private Text dateErrorMessage;
  @FXML private Text descriptionErrorMessage;
  @FXML private NumberAndPunctuationTextField textFieldAmount;
  @FXML private TextArea descriptionTextArea;
  @FXML private ComboBox<String> categoryNameComboBox;

  @FXML private Button cancelEntryButton;

  private AccountingEntry accountingEntryToEdit;
  private Stage currentStage;

  private AccountingEntriesController accountingEntriesController;
  private CategoriesController categoriesController;

  /**
   * Initializes the controller class.
   * This method handles the initialization of the controller,
   * this includes the choosing a category, a file and if the entry should be repeated.
   *
   * @throws IOException (IOException)
   */
  @FXML
  public void initialize() throws IOException {
    accountingEntriesController = AccountingEntriesControllerFactory
        .getAccountingEntriesController();
    categoriesController = CategoriesControllerFactory.getCategoriesController();

    categoriesController.getCategories().forEach(category -> {
      categoryNameComboBox.getItems().add(category.getName());
      if (categoryNameComboBox.getValue() == null) {
        categoryNameComboBox.setValue(category.getName());
      }
    });
  }

  /**
   * Method to init accounting view with isExpense as a variable.
   *
   * @param entryToEdit {@code AccountingEntry} to edit
   * @param currentStage (Stage)
   */
  public void initData(AccountingEntry entryToEdit, Stage currentStage) {
    this.currentStage = currentStage;
    accountingEntryToEdit = entryToEdit;
    titleEntryText.setText("Edit accounting entry");
    datePicker.setValue(entryToEdit.getDate());
    textFieldAmount.setText(String.valueOf(entryToEdit.getAmount()));
    descriptionTextArea.setText(entryToEdit.getDescription());
    categoryNameComboBox.setValue(categoriesController.getCategoryById(entryToEdit.getCategoryId())
        .getName());
  }

  /**
   * Method to add new accounting entry by pushing the button "Add expense" or "Add income".
   */
  @FXML
  public void editAccountingEntry() {
    try {
      textFieldAmount.setOnKeyTyped(event -> expectedAmountErrorMessage.setVisible(false));
      datePicker.setOnKeyTyped(event -> dateErrorMessage.setVisible(false));
      datePicker.setOnMouseClicked(event -> dateErrorMessage.setVisible(false));
      descriptionTextArea.setOnKeyTyped(event -> descriptionErrorMessage.setVisible(false));
      if (datePicker.getValue() == null) {
        dateErrorMessage.setText("Enter a date!");
        dateErrorMessage.setVisible(true);
        dateErrorMessage.setFill(javafx.scene.paint.Color.RED);
        return;
      }
      if (!validateInputs()) {
        expectedAmountErrorMessage.setText("Enter a positive number with max 2 decimals!");
        expectedAmountErrorMessage.setVisible(true);
        expectedAmountErrorMessage.setFill(javafx.scene.paint.Color.RED);
        return;
      }
      if (descriptionTextArea.getText().isEmpty()) {
        descriptionErrorMessage.setText("Enter a description!");
        descriptionErrorMessage.setVisible(true);
        descriptionErrorMessage.setFill(javafx.scene.paint.Color.RED);
        return;
      }

      accountingEntryToEdit.setDate(datePicker.getValue());
      accountingEntryToEdit.setAmount(Double.parseDouble(textFieldAmount.getText()));
      accountingEntryToEdit.setDescription(descriptionTextArea.getText());
      accountingEntryToEdit.setCategoryId(categoriesController
          .getCategoryByName(categoryNameComboBox.getValue()).getId());

      accountingEntriesController.editAccountingEntry(accountingEntryToEdit);
      currentStage.close();
    } catch (IllegalArgumentException | IOException numberFormatException) {
      numberFormatException.printStackTrace();
      System.out.println(numberFormatException.getMessage());
    }
  }

  /**
   * Method to cancel the editing of an accounting entry.
   */
  @FXML
  public void cancelEntryButtonAction() {
    Stage stage = (Stage) cancelEntryButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Method to check if the inputs are valid.
   */
  private boolean validateInputs() {
    return textFieldAmount.getText().matches(expectedAmountRegex);
  }
}
