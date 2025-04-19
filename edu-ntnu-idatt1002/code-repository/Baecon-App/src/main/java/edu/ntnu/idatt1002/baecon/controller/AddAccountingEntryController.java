package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.ErrorAlert;
import edu.ntnu.idatt1002.baecon.components.NumberAndPunctuationTextField;
import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.factory.AccountingEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.DocumentsControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class responsible for handling the adding of a new accounting entry.
 */
public class AddAccountingEntryController {
  private static final String expectedAmountRegex = "([0-9]+([.,][0-9]{0,2})?|[.,;:!?'\"-])";
  @FXML private Text titleEntryText;
  @FXML private DatePicker datePicker;
  @FXML private Text expectedAmountErrorMessage;
  @FXML private Text currencyText;
  @FXML private Text dateErrorMessage;
  @FXML private Text descriptionErrorMessage;
  @FXML private NumberAndPunctuationTextField textFieldAmount;
  @FXML private TextField descriptionTextField;
  @FXML private ComboBox<String> categoryNameComboBox;
  @FXML private Label receiptFileNameLabel;

  @FXML private Button chooseFileButton;
  @FXML private CheckBox repeatCheckBox;
  @FXML private ComboBox<String> repeatComboBox;
  @FXML private Button cancelEntryButton;

  private boolean isExpense;
  private Stage currentStage;

  private AccountingEntriesController accountingEntriesController;
  private DocumentsController documentsController;
  private CategoriesController categoriesController;
  private UUID documentId = null;
  private File selectedFile = null;

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
    documentsController = DocumentsControllerFactory.getDocumentsController();
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    SettingsController settingsController = SettingsControllerFactory.getSettingsController();

    currencyText.setText(settingsController.getCurrency());
    repeatComboBox.setDisable(true);
    repeatComboBox.getItems().addAll("Weekly", "Monthly", "Quarterly", "Yearly");
    repeatComboBox.setValue("Monthly");
    repeatCheckBox.setOnAction(event -> {
      boolean selected = repeatCheckBox.isSelected();
      repeatComboBox.setDisable(!selected);
    });


    categoriesController.getCategories().forEach(category -> {
      categoryNameComboBox.getItems().add(category.getName());
      if (categoryNameComboBox.getValue() == null) {
        categoryNameComboBox.setValue(category.getName());
      }
    });

    chooseFileButton.setOnAction(event -> fileChooser());
    cancelEntryButton.setOnAction(event -> cancelEntryButtonAction());

    textFieldAmount.setOnKeyTyped(event -> expectedAmountErrorMessage.setVisible(false));
    datePicker.setOnKeyTyped(event -> dateErrorMessage.setVisible(false));
    datePicker.setOnMouseClicked(event -> dateErrorMessage.setVisible(false));
    descriptionTextField.setOnKeyTyped(event -> descriptionErrorMessage.setVisible(false));
  }

  /**
   * Method to init accounting view with isExpense as a variable.
   *
   * @param isExpense is expense
   */
  public void initData(boolean isExpense, Stage currentStage) {
    this.isExpense = isExpense;
    this.currentStage = currentStage;
    titleEntryText.setText("New " + (isExpense ? "expense" : "income"));
  }

  /**
   * Method to add new accounting entry by pushing the button "Add expense" or "Add income".
   */
  @FXML
  public void addNewAccountingEntry() {
    try {
      if (!inputsAreValid()) {
        return;
      }

      if (selectedFile != null) {
        try {
          File destinationDirectory = new File("src/main/resources/edu/ntnu/idatt1002/"
              + "baecon/documents/" + selectedFile.getName());
          Files.copy(selectedFile.toPath(), destinationDirectory.toPath(),
              StandardCopyOption.REPLACE_EXISTING);
          documentId = UUID.randomUUID();
          Document document = new Document(documentId, descriptionTextField.getText(),
              datePicker.getValue(), destinationDirectory);
          documentsController.addDocument(document);
        } catch (IOException e) {
          e.printStackTrace();
          ErrorAlert.show("Error while adding document", e.getMessage());
        }
      }
      double amount = Double.parseDouble(textFieldAmount.getText());
      String description = descriptionTextField.getText();
      Category chosenCategory = categoriesController
          .getCategoryByName(categoryNameComboBox.getValue());
      AccountingEntry accountingEntry = new AccountingEntry(isExpense, datePicker.getValue(),
          amount, chosenCategory.getId(), description, documentId);

      if (repeatCheckBox.isSelected()) {
        accountingEntriesController.addNewRepeatingAccountingEntry(accountingEntry,
            repeatComboBox.getValue());
      } else {
        accountingEntriesController.addNewAccountingEntry(accountingEntry);
      }
      currentStage.close();
    } catch (IllegalArgumentException | IOException numberFormatException) {
      numberFormatException.printStackTrace();
      System.out.println(numberFormatException.getMessage());
    }
  }

  /**
   * Method to cancel the adding of a new accounting entry.
   */
  @FXML
  public void cancelEntryButtonAction() {
    Stage stage = (Stage) cancelEntryButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Method to check if the inputs are valid and set error texts if they are not.
   */
  private boolean inputsAreValid() {
    boolean valid = true;

    if (datePicker.getValue() == null) {
      dateErrorMessage.setText("Enter a date in the given format: dd.mm.yyyy!");
      dateErrorMessage.setVisible(true);
      dateErrorMessage.setFill(javafx.scene.paint.Color.RED);
      valid = false;
    }

    if (!textFieldAmount.getText().matches(expectedAmountRegex)) {
      expectedAmountErrorMessage.setText("Enter a valid amount!");
      expectedAmountErrorMessage.setVisible(true);
      expectedAmountErrorMessage.setFill(javafx.scene.paint.Color.RED);
      valid = false;
    }

    if (descriptionTextField.getText().isEmpty()) {
      descriptionErrorMessage.setText("Enter a description!");
      descriptionErrorMessage.setVisible(true);
      descriptionErrorMessage.setFill(javafx.scene.paint.Color.RED);
      valid = false;
    } else if (descriptionTextField.getText().contains(",")) {
      descriptionErrorMessage.setText("Description cannot contain a comma!");
      descriptionErrorMessage.setVisible(true);
      descriptionErrorMessage.setFill(javafx.scene.paint.Color.RED);
      valid = false;
    }

    return valid;
  }

  /**
   * Method to choose a file.
   */
  private void fileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose a file");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("PDF", "*.pdf"),
        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("PNG", "*.png")
    );
    selectedFile = fileChooser.showOpenDialog(currentStage);
    if (selectedFile != null) {
      receiptFileNameLabel.setText(selectedFile.getName());
    }
  }
}
