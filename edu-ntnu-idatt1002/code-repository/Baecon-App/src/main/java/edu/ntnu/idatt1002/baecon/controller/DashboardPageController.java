package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.data.BudgetEntry;
import edu.ntnu.idatt1002.baecon.data.Category;
import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.factory.AccountingEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.BudgetEntriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.CategoriesControllerFactory;
import edu.ntnu.idatt1002.baecon.factory.SettingsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.AccountingEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.BudgetEntriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.CategoriesChangeListener;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Class responsible for handling the dashboard view.
 */
public class DashboardPageController implements AccountingEntriesChangeListener,
    BudgetEntriesChangeListener, CategoriesChangeListener, SettingsListener {

  @FXML
  private LineChart<String, Number> trendChart;
  @FXML
  private BarChart<String, Number> overviewChart;
  @FXML
  private ComboBox<String> monthPicker;
  @FXML
  private ComboBox<Integer> yearPicker;


  @FXML
  private TableView<TableViewRow> overviewView;
  @FXML
  private TableColumn<TableViewRow, String> categoryName;
  @FXML
  private TableColumn<TableViewRow, Double> budgetedIncome;
  @FXML
  private TableColumn<TableViewRow, Double> accountedIncome;
  @FXML
  private TableColumn<TableViewRow, Double> deviationIncome;
  @FXML
  private TableColumn<TableViewRow, Double> budgetedExpense;
  @FXML
  private TableColumn<TableViewRow, Double> accountedExpense;
  @FXML
  private TableColumn<TableViewRow, Double> deviationExpense;


  @FXML
  Text sumBudgetText;
  @FXML
  Text sumAccountedText;
  @FXML
  Text sumResultText;

  private AccountingEntriesController accountingEntriesController;
  private BudgetEntriesController budgetEntriesController;
  private CategoriesController categoriesController;
  private SettingsController settingsController;

  /**
   * Method to initialize the dashbaord page.
   *
   * @throws IOException    if the file is not found
   * @throws ParseException if the date is not in the correct format
   */
  @FXML
  public void initialize() throws IOException, ParseException {
    categoriesController = CategoriesControllerFactory.getCategoriesController();
    categoriesController.addSubscriber(this);

    accountingEntriesController = AccountingEntriesControllerFactory
        .getAccountingEntriesController();
    accountingEntriesController.addSubscriber(this);

    budgetEntriesController = BudgetEntriesControllerFactory.getBudgetEntriesController();
    budgetEntriesController.addSubscriber(this);

    settingsController = SettingsControllerFactory.getSettingsController();
    settingsController.addSubscriber(this);

    categoryName.setCellValueFactory(new PropertyValueFactory<>("category"));
    categoryName.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(item);
        }
      }
    });


    budgetedIncome.setCellValueFactory(new PropertyValueFactory<>("budgetedIncome"));
    budgetedIncome.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          }
        }
      }
    });


    accountedIncome.setCellValueFactory(new PropertyValueFactory<>("accountedIncome"));
    accountedIncome.setCellFactory(column -> new TableCell<>() {
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          }
        }
      }
    });


    deviationIncome.setCellValueFactory(new PropertyValueFactory<>("deviationIncome"));
    deviationIncome.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          } else {
            setTextFill(Color.BLACK);
          }
        }
      }
    });

    budgetedExpense.setCellValueFactory(new PropertyValueFactory<>("budgetedExpense"));
    budgetedExpense.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          } else {
            setTextFill(Color.BLACK);
          }
        }
      }
    });


    accountedExpense.setCellValueFactory(new PropertyValueFactory<>("accountedExpense"));
    accountedExpense.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          } else {
            setTextFill(Color.BLACK);

          }
        }
      }
    });

    deviationExpense.setCellValueFactory(new PropertyValueFactory<>("deviationExpense"));
    deviationExpense.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
          setTextFill(Color.BLACK);
        } else {
          setText(String.format("%.2f %s", item, settingsController.getCurrency()));
          if (item < 0 && settingsController.getNegativeNumbersInRed()
              && !settingsController.getColorBlindMode()) {
            setTextFill(Color.RED);
          } else {
            setTextFill(Color.BLACK);
          }
        }
      }
    });

    LocalDate todaysDate = LocalDate.now();

    yearPicker.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026,
        2027, 2028, 2029, 2030);
    yearPicker.setValue(todaysDate.getYear());
    yearPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
              monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      accountingEntriesController.loadEntries(date);
      budgetEntriesController.loadEntries(date);
    });

    monthPicker.getItems().addAll("January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December");
    monthPicker.setValue(todaysDate.getMonth().toString().substring(0, 1).toUpperCase()
        + todaysDate.getMonth().toString().toLowerCase().substring(1));
    monthPicker.setOnAction(event -> {
      LocalDate date = LocalDate.of(yearPicker.getSelectionModel().getSelectedItem(),
              monthPicker.getSelectionModel().getSelectedIndex() + 1, 1);
      accountingEntriesController.loadEntries(date);
      budgetEntriesController.loadEntries(date);
    });

    accountingEntriesController.loadEntries(LocalDate.of(
        yearPicker.getSelectionModel().getSelectedItem(),
        monthPicker.getSelectionModel().getSelectedIndex() + 1,
        1));
    budgetEntriesController.loadEntries(LocalDate.of(
        yearPicker.getSelectionModel().getSelectedItem(),
        monthPicker.getSelectionModel().getSelectedIndex() + 1,
        1));

    showTableData(categoriesController.getCategories(),
            accountingEntriesController.getAccountingEntries(),
            budgetEntriesController.getBudgetEntries());
    updateMonthlySums(accountingEntriesController.getAccountingEntries(),
            budgetEntriesController.getBudgetEntries(), settingsController.getCurrency());
    updateLineChart(settingsController.getCurrency());
    updateBarChart(accountingEntriesController.getAccountingEntries(),
            budgetEntriesController.getBudgetEntries(), settingsController.getCurrency());
  }

  /**
   * Method to populate the table view with data.
   *
   * @param categories the categories
   * @param accountingEntries the accounting entries
   * @param budgetEntries the budget entries
   */
  public void showTableData(List<Category> categories, List<AccountingEntry> accountingEntries,
                           List<BudgetEntry> budgetEntries) {

    overviewView.getItems().clear();

    for (Category category : categories) {
      double budgetedIncome = 0.0;
      double accountedIncome = 0.0;
      double budgetedExpense = 0.0;
      double accountedExpense = 0.0;

      for (AccountingEntry accountingEntry : accountingEntries) {
        if (category.getId().equals(accountingEntry.getCategoryId())
            && accountingEntry.isExpense()) {
          accountedExpense -= accountingEntry.getAmount();
        }
        if (category.getId().equals(accountingEntry.getCategoryId())
            && !accountingEntry.isExpense()) {
          accountedIncome += accountingEntry.getAmount();
        }
      }
      for (BudgetEntry budgetEntry : budgetEntries) {
        if (category.getId().equals(budgetEntry.getCategoryId()) && budgetEntry.isExpense()) {
          budgetedExpense -= budgetEntry.getAmount();
        }
        if (category.getId().equals(budgetEntry.getCategoryId()) && !budgetEntry.isExpense()) {
          budgetedIncome += budgetEntry.getAmount();
        }
      }
      double deviationIncome = accountedIncome - budgetedIncome;
      double deviationExpense = accountedExpense - budgetedExpense;

      overviewView.getItems().add(new TableViewRow(category.getName(), budgetedIncome,
          accountedIncome, deviationIncome, budgetedExpense, accountedExpense,
          deviationExpense));
    }
  }

  private void updateBarChart(List<AccountingEntry> accountingEntries,
                              List<BudgetEntry> budgetEntries, String currency) {
    double budgetedIncome = 0.0;
    double accountedIncome = 0.0;
    double budgetedExpense = 0.0;
    double accountedExpense = 0.0;

    for (AccountingEntry accountingEntry : accountingEntries) {
      if (accountingEntry.isExpense()) {
        accountedExpense += accountingEntry.getAmount();
      } else {
        accountedIncome += accountingEntry.getAmount();
      }
    }

    for (BudgetEntry budgetEntry : budgetEntries) {
      if (budgetEntry.isExpense()) {
        budgetedExpense += budgetEntry.getAmount();
      } else {
        budgetedIncome += budgetEntry.getAmount();
      }
    }

    XYChart.Series<String, Number> budgetSeries = new XYChart.Series<>();
    budgetSeries.getData().add(new XYChart.Data<>("Incomes", budgetedIncome));
    budgetSeries.getData().add(new XYChart.Data<>("Expenses", budgetedExpense));
    budgetSeries.setName("Budgeted");

    XYChart.Series<String, Number> accountingSeries = new XYChart.Series<>();
    accountingSeries.getData().add(new XYChart.Data<>("Incomes", accountedIncome));
    accountingSeries.getData().add(new XYChart.Data<>("Expenses", accountedExpense));
    accountingSeries.setName("Accounted");



    // add the series to the chart
    overviewChart.getData().clear(); // clear existing data
    overviewChart.getData().addAll(budgetSeries, accountingSeries);

    overviewChart.getYAxis().setLabel(currency);
    overviewChart.setTitle("Budgeted vs. Accounted - " +  monthPicker.getValue() + " "
        + yearPicker.getValue());

    overviewChart.lookup(".chart-title").setStyle("-fx-opacity: 1;");
    Node labelNode = overviewChart.getYAxis().lookup(".axis-label");
    labelNode.setOpacity(1);

    List<Node> items = overviewChart.lookupAll("Label.chart-legend-item").stream().toList();
    Label incomeLabel = (Label) items.get(0);
    Label expenseLabel = (Label) items.get(1);



    if (settingsController.getColorBlindMode()) {
      budgetSeries.getData().get(0).getNode().setStyle("-fx-bar-fill: black;");
      budgetSeries.getData().get(1).getNode().setStyle("-fx-bar-fill: black;");
      incomeLabel.getGraphic().setStyle("-fx-background-color: black;");
      incomeLabel.setOpacity(1);
      accountingSeries.getData().get(0).getNode().setStyle("-fx-bar-fill: gray;");
      accountingSeries.getData().get(1).getNode().setStyle("-fx-bar-fill: gray;");
      expenseLabel.getGraphic().setStyle("-fx-background-color: gray;");
      expenseLabel.setOpacity(1);
    } else {
      budgetSeries.getData().get(0).getNode().setStyle("-fx-bar-fill: #5EB68C;");
      budgetSeries.getData().get(1).getNode().setStyle("-fx-bar-fill: #5EB68C;");
      incomeLabel.getGraphic().setStyle("-fx-background-color: #5EB68C;");
      incomeLabel.setOpacity(1);
      accountingSeries.getData().get(0).getNode().setStyle("-fx-bar-fill: #ff5757;");
      accountingSeries.getData().get(1).getNode().setStyle("-fx-bar-fill: #ff5757;");
      expenseLabel.getGraphic().setStyle("-fx-background-color: #ff5757;");
      expenseLabel.setOpacity(1);
    }
  }

  private void updateMonthlySums(List<AccountingEntry> accountingEntries,
                          List<BudgetEntry> budgetEntries, String currency) {
    overviewChart.getData().clear();

    double accounted = 0.0;
    double budgeted = 0.0;

    for (AccountingEntry accountingEntry : accountingEntries) {
      if (accountingEntry.isExpense()) {
        accounted -= accountingEntry.getAmount();
      } else {
        accounted += accountingEntry.getAmount();
      }
    }

    for (BudgetEntry budgetEntry : budgetEntries) {
      if (budgetEntry.isExpense()) {
        budgeted -= budgetEntry.getAmount();
      } else {
        budgeted += budgetEntry.getAmount();
      }
    }

    sumAccountedText.setText(String.format("%.2f %s", accounted, currency));
    sumBudgetText.setText(String.format("%.2f %s", budgeted, currency));
    sumResultText.setText(String.format("%.2f %s", accounted - budgeted, currency));

    if (accounted < 0 && settingsController.getNegativeNumbersInRed()
        && !settingsController.getColorBlindMode()) {
      sumAccountedText.setFill(Color.RED);
    } else {
      sumAccountedText.setFill(Color.BLACK);
    }
    if (budgeted < 0 && settingsController.getNegativeNumbersInRed()
        && !settingsController.getColorBlindMode()) {
      sumBudgetText.setFill(Color.RED);
    } else {
      sumBudgetText.setFill(Color.BLACK);
    }
    if (accounted - budgeted < 0 && settingsController.getNegativeNumbersInRed()
        && !settingsController.getColorBlindMode()) {
      sumResultText.setFill(Color.RED);
    } else {
      sumResultText.setFill(Color.BLACK);
    }


  }

  /**
   * Class representing a row in the table view.
   */
  public static class TableViewRow {
    private final String category;
    private final double budgetedIncome;
    private final double accountedIncome;
    private final double deviationIncome;
    private final double budgetedExpense;
    private final double accountedExpense;
    private final double deviationExpense;

    /**
     * Constructor for the table view row.
     *
     * @param category category of the row
     * @param budgetedIncome budgeted income
     * @param accountedIncome accounted income
     * @param deviationIncome deviation income
     * @param budgetedExpense budgeted expense
     * @param accountedExpense accounted expense
     * @param deviationExpense deviation expense
     */
    public TableViewRow(String category, Double budgetedIncome,
                        Double accountedIncome, Double deviationIncome,
                        Double budgetedExpense, Double accountedExpense, Double deviationExpense) {
      this.category = category;
      this.budgetedIncome = budgetedIncome;
      this.accountedIncome = accountedIncome;
      this.deviationIncome = deviationIncome;
      this.budgetedExpense = budgetedExpense;
      this.accountedExpense = accountedExpense;
      this.deviationExpense = deviationExpense;
    }

    public String getCategory() {
      return category;
    }

    public double getBudgetedIncome() {
      return budgetedIncome;
    }

    public double getAccountedIncome() {
      return accountedIncome;
    }

    public double getDeviationIncome() {
      return deviationIncome;
    }

    public double getBudgetedExpense() {
      return budgetedExpense;
    }

    public double getAccountedExpense() {
      return accountedExpense;
    }

    public double getDeviationExpense() {
      return deviationExpense;
    }
  }

  @Override
  public void updateAccountingEntries(List<AccountingEntry> accountingEntries) {
    showTableData(categoriesController.getCategories(), accountingEntries,
            budgetEntriesController.getBudgetEntries());
    updateMonthlySums(accountingEntries, budgetEntriesController.getBudgetEntries(),
        settingsController.getCurrency());
    updateLineChart(settingsController.getCurrency());
  }

  @Override
  public void updateBudgetEntries(List<BudgetEntry> budgetEntries, LocalDate date) {
    showTableData(categoriesController.getCategories(),
            accountingEntriesController.getAccountingEntries(),
            budgetEntries);
    updateMonthlySums(accountingEntriesController.getAccountingEntries(), budgetEntries,
        settingsController.getCurrency());
    updateLineChart(settingsController.getCurrency());
    updateBarChart(accountingEntriesController.getAccountingEntries(), budgetEntries,
        settingsController.getCurrency());
  }

  public void updateSettings(Settings settings) {
    updateMonthlySums(accountingEntriesController.getAccountingEntries(),
        budgetEntriesController.getBudgetEntries(), settings.getCurrency());
  }

  @Override
  public void updateCategories(List<Category> categories, Category newestCategory) {
    showTableData(categories, accountingEntriesController.getAccountingEntries(),
            budgetEntriesController.getBudgetEntries());
  }

  private void updateLineChart(String currency) {
    trendChart.getData().clear();

    XYChart.Series<String, Number> accountingSeries = new XYChart.Series<>();
    XYChart.Series<String, Number> budgetSeries = new XYChart.Series<>();
    trendChart.getYAxis().setLabel(currency);
    trendChart.setTitle("Yearly Trend - " + yearPicker.getValue());
    Node labelNode = trendChart.getYAxis().lookup(".axis-label");
    labelNode.setOpacity(1);
    trendChart.setLegendSide(Side.RIGHT);

    for (int i = 1; i < 13; i++) {
      LocalDate date = LocalDate.of(yearPicker.getValue(), i, 1);

      double accounted = 0.0;
      double budgeted = 0.0;
      for (AccountingEntry accountingEntry : accountingEntriesController.retrieveEntries(date)) {
        if (accountingEntry.isExpense()) {
          accounted -= accountingEntry.getAmount();
        } else {
          accounted += accountingEntry.getAmount();
        }
      }
      for (BudgetEntry budgetEntry : budgetEntriesController.retrieveEntries(date)) {
        if (budgetEntry.isExpense()) {
          budgeted -= budgetEntry.getAmount();
        } else {
          budgeted += budgetEntry.getAmount();
        }
      }
      budgetSeries.getData().add(new XYChart.Data<>(date.getMonth().toString()
          .substring(0, 3), budgeted));
      accountingSeries.getData().add(new XYChart.Data<>(date.getMonth().toString()
          .substring(0, 3), accounted));
    }


    trendChart.getData().addAll(budgetSeries, accountingSeries);
    trendChart.lookup(".chart-title").setStyle("-fx-opacity: 1;");

    budgetSeries.setName("Budgeted");
    accountingSeries.setName("Accounted");


    for (XYChart.Data<String, Number> data : budgetSeries.getData()) {
      data.getNode().setStyle("-fx-background-color: transparent;");
      data.setNode(null);
    }
    for (XYChart.Data<String, Number> data : accountingSeries.getData()) {
      data.getNode().setStyle("-fx-background-color: transparent;");
      data.setNode(null);
    }
    Line budgetLine = new Line(0, 0, 10, 0);
    budgetLine.setStrokeWidth(3);
    Line accountedLine = new Line(0, 0, 10, 0);
    accountedLine.setStrokeWidth(3);

    List<Node> items = trendChart.lookupAll("Label.chart-legend-item").stream().toList();

    Label budgetLabel = (Label) items.get(0);
    budgetLabel.setGraphic(budgetLine);

    Label accountedLabel = (Label) items.get(1);
    accountedLabel.setGraphic(accountedLine);

    if (settingsController.getColorBlindMode()) {
      budgetSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: black;");
      accountingSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: gray;");
      budgetLine.setStroke(Color.BLACK);
      budgetLabel.setOpacity(1);
      accountedLine.setStroke(Color.GRAY);
      accountedLabel.setOpacity(1);
    } else {
      budgetSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #5EB68C;");
      accountingSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #ff5757;");
      Color budgetColor = Color.web("#5EB68C");
      budgetLine.setStroke(budgetColor);
      budgetLabel.setOpacity(1);
      Color accountedColor = Color.web("#ff5757");
      accountedLine.setStroke(accountedColor);
      accountedLabel.setOpacity(1);
    }
  }
}

