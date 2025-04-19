package edu.ntnu.idatt1002.baecon.scenes;

/**
 * Enum representing the different views in the application.
 */
public enum View {
  GENERAL_LAYOUT("generalLayout.fxml"),
  DASHBOARD("dashboardPage.fxml"),
  BUDGET("budgetPage.fxml"),
  ACCOUNTING("accountingPage.fxml"),
  ADD_TO_BUDGET("addBudgetEntry.fxml"),
  ADD_TO_ACCOUNTING("addAccountingEntry.fxml"),
  EDIT_ACCOUNTING_ENTRY("editAccountingEntry.fxml"),
  DOCUMENTS("documentsPage.fxml"),
  SETTINGS("settingsPage.fxml"),
  SETTINGS_CATEGORY_ENTRY("settingsCategoryDialog.fxml"),
  ABOUT_DIALOG("aboutDialog.fxml");


  private final String fileName;

  /**
   * Constructor for the enum.
   *
   * @param fileName the name of the FXML file associated with the view
   */
  View(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Returns the name of the FXML file associated with the view.
   *
   * @return the name of the FXML file associated with the view
   */
  public String getFileName() {
    return fileName;
  }
}
