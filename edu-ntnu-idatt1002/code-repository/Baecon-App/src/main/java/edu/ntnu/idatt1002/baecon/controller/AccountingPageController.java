package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.ErrorAlert;
import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.factory.AccountingEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.DocumentsControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.AccountingEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.awt.Desktop;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class responsible for handling the accounting page.
 */
public class AccountingPageController implements AccountingEntriesChangeListener, SettingsListener {
  @FXML private TableView<AccountingEntry>  accountingEntriesTableView;
  @FXML private TableColumn<AccountingEntry, UUID> accountingCategoryColumn;
  @FXML private TableColumn<AccountingEntry, LocalDate> accountingDateColumn;
  @FXML private TableColumn<AccountingEntry, String> accountingDescriptionColumn;
  @FXML private TableColumn<AccountingEntry, Double> accountingExpenseColumn;
  @FXML private TableColumn<AccountingEntry, Double> accountingIncomeColumn;
  @FXML private TableColumn<AccountingEntry, UUID> accountingDocumentColumn;

  @FXML private ComboBox<Integer> yearPicker;
  @FXML private ComboBox<String> monthPicker;
  @FXML private Button editEntryButton;
  @FXML private Button deleteEntryButton;
  @FXML private Text sumIncomesText;
  @FXML private Text sumExpensesText;
  @FXML private Text resultText;

  private AccountingEntriesController accountingEntriesController;
  private CategoriesController categoriesController;
  private SettingsController settingsController;
  private DocumentsController documentsController;

  /**
   * Method to initialize the controller.
   *
   * @throws IOException if the controller could not be initialized
   */
  @FXML
  public void initialize() throws IOException  {
    accountingEntriesController = AccountingEntriesControllerFactory
      .getAccountingEntriesController();
    accountingEntriesController.addSubscriber(this);
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    settingsController = SettingsControllerFactory.getSettingsController();
    settingsController.addSubscriber(this);
    documentsController = DocumentsControllerFactory.getDocumentsController();

    accountingEntriesTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            editEntryButton.setDisable(false);
            deleteEntryButton.setDisable(false);
          } else {
            editEntryButton.setDisable(true);
            deleteEntryButton.setDisable(true);
          }
        });

    accountingDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    accountingDateColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(BaeconDateFormatter.formatWithoutTime(item));
        }
      }
    });

    accountingDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    accountingCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    accountingCategoryColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(UUID item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(categoriesController.getCategoryById(item).getName());
        }
      }
    });

    accountingIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    accountingIncomeColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          TableRow<AccountingEntry> row = getTableRow();
          if (row != null && row.getItem() != null) {
            if (row.getItem().isExpense()) {
              setText("");
            } else {
              setText(String.format("%.2f %s", item, settingsController.getCurrency()));
            }
          }
        }
      }
    });

    accountingExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    accountingExpenseColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          TableRow<AccountingEntry> row = getTableRow();
          if (row != null && row.getItem() != null) {
            if (row.getItem().isExpense()) {
              setText(String.format("-%.2f %s", item, settingsController.getCurrency()));
              if (settingsController.getNegativeNumbersInRed()
                  && !settingsController.getColorBlindMode()) {
                setTextFill(Color.RED);
              } else {
                setTextFill(Color.BLACK);
              }
            } else {
              setText("");
            }
          }
        }
      }
    });

    accountingDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("documentId"));
    accountingDocumentColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(UUID item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setGraphic(null);
        } else {
          Document document = documentsController.getDocumentById(item);
          if (document != null) {
            Hyperlink link = new Hyperlink(document.getDescription());
            link.setOnAction(event -> {
              try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("linux")) {
                  ProcessBuilder pb = new ProcessBuilder("xdg-open",
                    document.getPdfFile().getAbsolutePath());
                  pb.start();
                  return;
                }
                Desktop.getDesktop().open(document.getPdfFile());
              } catch (IOException e) {
                e.printStackTrace();
                ErrorAlert.show("Error", "Could not open the document."
                    + e.getMessage());
              }
            });
            setGraphic(link);
          }
        }
      }
    });

    LocalDate todaysDate = LocalDate.now();
    accountingEntriesController.loadEntries(todaysDate);

    // ComboBox/Dropdown Year Picker
    yearPicker.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029,
        2030);
    yearPicker.setValue(todaysDate.getYear());
    yearPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
          monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      accountingEntriesController.loadEntries(date);
    });

    // ComboBox/Dropdown Month Picker
    monthPicker.getItems().addAll("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December");
    monthPicker.setValue(todaysDate.getMonth().toString().substring(0, 1).toUpperCase()
        + todaysDate.getMonth().toString().toLowerCase().substring(1));
    monthPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
          monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      accountingEntriesController.loadEntries(date);
    });

    // Load into view
    showAccountingEntries(accountingEntriesController.getAccountingEntries());
  }

  /**
   * Method to show the accounting entries.
   *
   * @param accountingEntries the accounting entries to show
   */
  private void showAccountingEntries(List<AccountingEntry> accountingEntries) {
    double sumIncomes = 0.0;
    double sumExpenses = 0.0;
    accountingEntriesTableView.getItems().clear();
    for (AccountingEntry accountingEntry : accountingEntries) {
      accountingEntriesTableView.getItems().add(accountingEntry);
      if (accountingEntry.isExpense()) {
        sumExpenses += accountingEntry.getAmount();
      } else {
        sumIncomes += accountingEntry.getAmount();
      }
    }

    sumIncomesText.setText(String.format("%.2f %s", sumIncomes, settingsController.getCurrency()));
    sumExpensesText.setText(String.format("%.2f %s", sumExpenses,
        settingsController.getCurrency()));
    resultText.setText(String.format("%.2f %s", sumIncomes - sumExpenses,
        settingsController.getCurrency()));
    if (sumIncomes - sumExpenses < 0 && settingsController.getNegativeNumbersInRed()
        && !settingsController.getColorBlindMode()) {
      resultText.setFill(Color.RED);
    } else {
      resultText.setFill(Color.BLACK);
    }
  }

  /**
   * Method to open the add new income dialog.
   *
   * @throws IOException if the fxml file could not be loaded
   */
  @FXML
  void onAddNewIncome() throws IOException {
    Stage newIncomeStage = new Stage();
    newIncomeStage.setTitle("Baecon - Add income");
    FXMLLoader addToAccountingFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.ADD_TO_ACCOUNTING.getFileName())));
    addToAccountingFXML.load();

    AddAccountingEntryController addAccountingEntryController = addToAccountingFXML.getController();
    addAccountingEntryController.initData(false, newIncomeStage);


    newIncomeStage.setScene(new Scene(addToAccountingFXML.getRoot()));
    newIncomeStage.show();
  }

  /**
   * Method to open the add new expense dialog.
   *
   * @throws IOException if the fxml file could not be loaded
   */
  @FXML
  void onAddNewExpense() throws IOException {
    Stage newExpenseStage = new Stage();
    newExpenseStage.setTitle("Baecon - Add expense");
    FXMLLoader addToAccountingFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.ADD_TO_ACCOUNTING.getFileName())));
    addToAccountingFXML.load();

    AddAccountingEntryController addAccountingEntryController = addToAccountingFXML.getController();
    addAccountingEntryController.initData(true, newExpenseStage);


    newExpenseStage.setScene(new Scene(addToAccountingFXML.getRoot()));
    newExpenseStage.show();
  }

  /**
   * Opens a new window to edit the selected entry.
   *
   * @throws IOException if the FXML file cannot be loaded
   */
  @FXML
  public void onEditEntry() throws IOException {
    AccountingEntry selectedEntry = accountingEntriesTableView.getSelectionModel()
        .getSelectedItem();
    Stage editEntryStage = new Stage();
    editEntryStage.setTitle("Baecon - Edit entry");
    FXMLLoader addToAccountingFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.EDIT_ACCOUNTING_ENTRY.getFileName())));
    addToAccountingFXML.load();

    EditAccountingEntryController editAccountingEntryController = addToAccountingFXML
        .getController();
    editAccountingEntryController.initData(selectedEntry, editEntryStage);

    editEntryStage.setScene(new Scene(addToAccountingFXML.getRoot()));
    editEntryStage.show();
  }

  /**
   * Opens a dialog to delete the selected entry.
   */
  @FXML
  public void onDeleteEntry() {
    AccountingEntry selectedEntry = accountingEntriesTableView.getSelectionModel()
        .getSelectedItem();
    Dialog<ButtonType> deleteEntryDialog = new Dialog<>();
    deleteEntryDialog.setTitle("Baecon - Delete entry");
    deleteEntryDialog.setHeaderText("Are you sure you want to delete entry " + selectedEntry
        .getDescription() + "?");

    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    deleteEntryDialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

    deleteEntryDialog.setResultConverter(buttonType -> {
      if (buttonType == yesButton) {
        return buttonType;
      }
      return null;
    });

    Optional<ButtonType> result = deleteEntryDialog.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
      try {
        accountingEntriesController.deleteAccountingEntry(selectedEntry);
      } catch (IOException ioException) {
        ioException.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Baecon - Error");
        alert.setHeaderText("Error writing file");
        alert.setContentText("Error: " + ioException.getMessage());
        alert.showAndWait();
      }
    }
  }

  @Override
  public void updateAccountingEntries(List<AccountingEntry> accountingEntries) {
    showAccountingEntries(accountingEntries);
    if (accountingEntries.size() > 0) {
      LocalDate date = accountingEntries.get(0).getDate();
      yearPicker.setValue(date.getYear());
      monthPicker.setValue(date.getMonth().toString().substring(0, 1).toUpperCase()
          + date.getMonth().toString().toLowerCase().substring(1));
    }
  }

  @Override
  public void updateSettings(Settings newSettings) {
    showAccountingEntries(accountingEntriesController.getAccountingEntries());
  }
}
