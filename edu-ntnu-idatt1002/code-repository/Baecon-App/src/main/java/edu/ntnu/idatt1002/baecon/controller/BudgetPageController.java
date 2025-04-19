package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.NumberAndPunctuationTextField;
import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.factory.BudgetEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.BudgetEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import edu.ntnu.idatt1002.baecon.scenes.View;
import edu.ntnu.idatt1002.baecon.scenes.ViewSwitcher;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class responsible for handling the budget page.
 */
public class BudgetPageController implements BudgetEntriesChangeListener, SettingsListener {
  @FXML private TableView<BudgetEntry> budgetIncomeEntriesTableView;
  @FXML private TableColumn<BudgetEntry, UUID> categoryIncomeTableColumn;
  @FXML private TableColumn<BudgetEntry, Double> incomeTableColumn;

  @FXML private TableView<BudgetEntry> budgetExpenseEntriesTableView;
  @FXML private TableColumn<BudgetEntry, UUID> categoryExpenseTableColumn;
  @FXML private TableColumn<BudgetEntry, Double> expenseTableColumn;

  @FXML private Button editButton;
  @FXML private Button deleteButton;

  @FXML private PieChart incomeExpensesPieChart;
  @FXML private ComboBox<Integer> yearPicker;
  @FXML private ComboBox<String> monthPicker;
  @FXML private Text sumExpensesText;
  @FXML private Text sumIncomesText;
  @FXML private Text resultText;
  private BudgetEntriesController budgetEntriesController;
  private CategoriesController categoriesController;
  private SettingsController settingsController;

  /**
   * Initializes the budget page.
   *
   * @throws IOException if the budget page cannot be loaded
   */
  @FXML
  public void initialize() throws IOException {
    budgetEntriesController = BudgetEntriesControllerFactory.getBudgetEntriesController();
    budgetEntriesController.addSubscriber(this);
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    settingsController = SettingsControllerFactory.getSettingsController();
    settingsController.addSubscriber(this);

    budgetIncomeEntriesTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            budgetExpenseEntriesTableView.getSelectionModel().clearSelection();
            editButton.setDisable(false);
            deleteButton.setDisable(false);
          } else {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
          }
        });

    budgetExpenseEntriesTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            budgetIncomeEntriesTableView.getSelectionModel().clearSelection();
            editButton.setDisable(false);
            deleteButton.setDisable(false);
          } else {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
          }
        });

    categoryIncomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    categoryIncomeTableColumn.setCellFactory(column -> new TableCell<>() {
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
    incomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    incomeTableColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
        }
      }
    });

    categoryExpenseTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    categoryExpenseTableColumn.setCellFactory(column -> new TableCell<>() {
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

    expenseTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    expenseTableColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(String.format("-%.2f %s", item, settingsController.getCurrency()));
          if (settingsController.getNegativeNumbersInRed() && !settingsController
              .getColorBlindMode()) {
            setTextFill(Color.RED);
          } else {
            setTextFill(Color.BLACK);
          }
        }
      }
    });
    LocalDate todaysDate = LocalDate.now();
    budgetEntriesController.loadEntries(todaysDate);

    yearPicker.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029,
        2030);
    yearPicker.setValue(todaysDate.getYear());
    yearPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
          monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      budgetEntriesController.loadEntries(date);
    });


    monthPicker.getItems().addAll("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December");
    monthPicker.setValue(todaysDate.getMonth().toString().substring(0, 1).toUpperCase()
        + todaysDate.getMonth().toString().toLowerCase().substring(1));
    monthPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
          monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      budgetEntriesController.loadEntries(date);
    });

    showBudgetEntries(budgetEntriesController.getBudgetEntries(), settingsController.getCurrency());
  }

  /**
   * This method loads a budget entry into the view.
   *
   * @param budgetEntries {@code List} of {@code BudgetEntry}
   */
  private void showBudgetEntries(List<BudgetEntry> budgetEntries, String currency) {
    double sumExpenses = 0.0;
    double sumIncomes = 0.0;
    budgetIncomeEntriesTableView.getItems().clear();
    budgetExpenseEntriesTableView.getItems().clear();

    for (BudgetEntry budgetEntry : budgetEntries) {
      if (budgetEntry.isExpense()) {
        budgetExpenseEntriesTableView.getItems().add(budgetEntry);
        sumExpenses += budgetEntry.getAmount();
      } else {
        budgetIncomeEntriesTableView.getItems().add(budgetEntry);
        sumIncomes += budgetEntry.getAmount();
      }
    }

    sumIncomesText.setText(String.format("%.2f %s", sumIncomes, currency));
    sumExpensesText.setText(String.format("%.2f %s", sumExpenses, currency));

    resultText.setText(String.format("%.2f %s", sumIncomes - sumExpenses, currency));
    if (sumIncomes - sumExpenses < 0 && settingsController.getNegativeNumbersInRed()
        && !settingsController.getColorBlindMode()) {
      resultText.setFill(Color.RED);
    } else {
      resultText.setFill(Color.BLACK);
    }

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
      new PieChart.Data("Incomes", sumIncomes),
      new PieChart.Data("Expenses", sumExpenses));
    incomeExpensesPieChart.setData(pieChartData);

    List<Node> items = incomeExpensesPieChart.lookupAll("Label.chart-legend-item").stream()
        .toList();
    Label incomeLabel = (Label) items.get(0);
    Label expenseLabel = (Label) items.get(1);

    if (settingsController.getColorBlindMode()) {
      pieChartData.get(0).getNode().setStyle("-fx-pie-color: black;");
      pieChartData.get(1).getNode().setStyle("-fx-pie-color: gray;");
      incomeLabel.getGraphic().setStyle("-fx-background-color: black;");
      expenseLabel.getGraphic().setStyle("-fx-background-color: gray;");
    } else {
      pieChartData.get(0).getNode().setStyle("-fx-pie-color: #5EB68C;");
      pieChartData.get(1).getNode().setStyle("-fx-pie-color: #ff5757;");
      incomeLabel.getGraphic().setStyle("-fx-background-color: #5EB68C;");
      expenseLabel.getGraphic().setStyle("-fx-background-color: #ff5757;");
    }
  }

  /**
   * This method loads an add income entry window.
   *
   * @throws IOException io exception
   */
  @FXML
  void onAddNewIncome() throws IOException {
    Stage newIncomeStage = new Stage();
    newIncomeStage.setTitle("Baecon - Add income");
    FXMLLoader addToBudgetFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.ADD_TO_BUDGET.getFileName())));
    addToBudgetFXML.load();

    LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
        monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
    AddBudgetEntryController addBudgetEntryController = addToBudgetFXML.getController();
    addBudgetEntryController.initData(false, newIncomeStage, date);


    newIncomeStage.setScene(new Scene(addToBudgetFXML.getRoot()));
    newIncomeStage.show();
  }

  /**
   * This method loads an add expense entry window.
   *
   * @throws IOException io exception
   */
  @FXML
  void onAddNewExpense() throws IOException {
    Stage newExpenseStage = new Stage();
    newExpenseStage.setTitle("Baecon - Add expense");
    System.out.println("New expense");
    FXMLLoader addToBudgetFXML = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class
        .getResource(View.ADD_TO_BUDGET.getFileName())));
    addToBudgetFXML.load();

    LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
        monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
    AddBudgetEntryController addBudgetEntryController = addToBudgetFXML.getController();
    addBudgetEntryController.initData(true, newExpenseStage, date);

    newExpenseStage.setScene(new Scene(addToBudgetFXML.getRoot()));
    newExpenseStage.show();
  }

  /**
   * Method to edit a budget entry.
   */
  @FXML
  public void onEditEntry() {
    BudgetEntry selectedEntry = budgetIncomeEntriesTableView.getSelectionModel().getSelectedItem();
    if (selectedEntry == null) {
      selectedEntry = budgetExpenseEntriesTableView.getSelectionModel().getSelectedItem();
    }
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit entry");
    dialog.setHeaderText("Edit " + categoriesController.getCategoryById(selectedEntry
        .getCategoryId()).getName());

    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

    NumberAndPunctuationTextField amountTextField = new NumberAndPunctuationTextField();
    amountTextField.setText(String.valueOf(selectedEntry.getAmount()));

    GridPane layout = new GridPane();
    layout.setHgap(10);
    layout.setVgap(10);
    layout.addRow(0, new Label("Amount"), amountTextField);
    dialog.getDialogPane().setContent(layout);

    dialog.setResultConverter(buttonType -> {
      if (buttonType == okButtonType) {
        return buttonType;
      }
      return null;
    });

    Optional<ButtonType> result = dialog.showAndWait();
    BudgetEntry finalSelectedEntry = selectedEntry;
    if (result.isPresent() && result.get() == okButtonType) {
      try {
        selectedEntry.setAmount(Double.parseDouble(amountTextField.getText()));
        LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
            monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
        budgetEntriesController.editBudgetEntry(finalSelectedEntry, date);
      } catch (IOException | NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
      }
    }
  }

  /**
   * Method to delete a budget entry.
   */
  @FXML
  public void onDeleteEntry() {
    BudgetEntry selectedEntry = budgetIncomeEntriesTableView.getSelectionModel().getSelectedItem();
    if (selectedEntry == null) {
      selectedEntry = budgetExpenseEntriesTableView.getSelectionModel().getSelectedItem();
    }

    // Create the confirmation dialog
    Dialog<ButtonType> deleteEntryDialog = new Dialog<>();
    deleteEntryDialog.setTitle("Baecon - Delete Budget Entry");
    deleteEntryDialog.setHeaderText("Are you sure you want to delete entry "
        + categoriesController.getCategoryById(selectedEntry.getCategoryId()).getName() + "?");

    // Set the dialog buttons
    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    deleteEntryDialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

    // Set the result converter for the dialog
    deleteEntryDialog.setResultConverter(buttonType -> {
      if (buttonType == yesButton) {
        return buttonType;
      }
      return null;
    });

    // Show the dialog and wait for the user to make a choice
    Optional<ButtonType> result = deleteEntryDialog.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
      try {
        LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
            monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
        budgetEntriesController.deleteBudgetEntry(selectedEntry, date);
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

  /**
   * This method updates the budget entries.
   *
   * @param budgetEntries as {@code List} of {@code BudgetEntry}
   */
  @Override
  public void updateBudgetEntries(List<BudgetEntry> budgetEntries, LocalDate date) {
    yearPicker.setValue(date.getYear());
    showBudgetEntries(budgetEntries, settingsController.getCurrency());
  }

  @Override
  public void updateSettings(Settings newSettings) {
    showBudgetEntries(budgetEntriesController.getBudgetEntries(), newSettings.getCurrency());
  }
}
