## Introduction

The application base its calculation upon the assumption that both Budget and Accounting will be actively used. The calculations in Dashboard is particularly dependent on the Budgeted items actually being tracked in the Accounting.

- **Budget** is where you set up your known and estimated incomes and expenses;
- **Accounting** should be where you input your actual incomes and expenses.
- The result will be that you can see whether or not your actual incomes and expenses stay within, or exceeds, what you budgeted for.

## Navigation

- Navigation between pages is done via the left sidebar.
- Go to the named page by clicking these buttons.
- Settings can also be accessed via the menubar: File > Settings
- Menu item "Help" will let you go to the Online Wiki, User Manual or show the About dialog.

## Dashboard

The Dashboard acts as a home screen, showing an overview of the Budgeted and Accounted entries for a selected month and year.

![final-Dashboard-01](uploads/bbe9eee70984a19967e51a5daf923588/final-Dashboard-01.png)

- This will work best if you track your Accounted incomes and expenses in accordance with what you have set up in the Budget.
- The entries you track in Budget should be tracked and followed up in Accounting too, to ensure that you get the most out of the information, and not least, that the information if accurately represented.

#### Charts and graphs

* **Line chart:** Upper left. Shows two different lines: Budgeted and Accounted. The line represents the sum for each, as also seen in the bottom of bar the window.
* **Bar chart:** Upper right. Shows a comparison between Budgeted and Accounted incomes (left bars) and expenses (right bars).

#### Table and column view

* The far left column show which category the entry belongs to.
* The middle section show Budgeted and Accounted **incomes** for the selected month
* The **Deviation** column show how much the _Accounted_ (i.e. actual) income deviates from the _Budgeted_ (estimated, projected) income.
* The rightmost column works the same way, only that it represent **Expenses** (Budgeted and Accounted)

## Budget

The budget works by you adding all your known, estimated and projected expenses. They will be the base to which you compare your accounted (actual) expenses and incomes.

![final-Budget-01](uploads/3b309975a5abe87d1e10ae78971de7eb/final-Budget-01.png)

- **First**, select the year and month you wish to view or work in.
- Add budgeting items by using the "Add Income" and "Add Expense" buttons. The dialog is identical for both, except for the window title.
- The budget page is split in two tables: one for Incomes (top) and one for Expenses (bottom).
- This makes it easy to know which is which
- The **bottom bar** show the summed Incomes and Expensens, as well as the **Result**.
- **Result** is simply Budgeted Incomes minus Expenses.
- **Pie Chart:** The rightmost sidebar contains a pie chart which show the budgetary **balance**. If the Expenses covers more than half the pie, this means that the expenses exceed the income available.

#### New Budget Entry

![budget-02-expense-02-recurring](uploads/7ea4a9f302af6dc6a9096c53329bebcb/budget-02-expense-02-recurring.png)

- **The new entry must include:**
  * **Date:** Select from the date picker widget.
  * **Amount:** Input numerical amount.
  * **Category:** Select from the menu, or add a new by clicking the "+" button.
- **Optional:**
  * **Repeat:** By checking the Repeat checkbox, you can select a frequency of recurrence from the combobox dropdown menu. It can be either montly, quarterly or yearly.
    * **Monthly** will add a new identical entry at the same date every month, one year forward. E.g. if the initial entry is set to 13. April 2023, the next entries will be 13. May 2023, 13. June 2023, and so on.
    * **Quarterly** will add 4 entries with three months between from the date of the initial entry. E.g. if the initial entry is set to 02. February 2023, the next will be 02. May 2023, 02. August 2023, and finally 02. November 2023.
    * **Yearly** will add a new entry at the same date for two years. E.g. if the inital entry is set to 09. September 2023, the next will be 09. September 2024 and 09. September 2025.

#### Edit or Delete Budget Entry

You can **edit** the amount (numerical value) of an entry by using selecting an entry in the table and clicking the "Edit" button.

![budget-03-edit](uploads/177b7dd9e4ea65f8ce0a592c3795f211/budget-03-edit.png)

To **delete** an entry, simply select the entry you wish to remove and click "Delete". This will delete a single entry, and will not affect any others even if it was created as part of recurringentries.

## Accounting

Accounting represents all your actual incomes and expenses. The should ideally represent all the actual incomes and expenses that you set up in your Budget, so that you can compare what you estimated to what the actual result was.

![final-Accounting-01](uploads/ba5c91cd45113d7e472a410b156b4d5c/final-Accounting-01.png)

- **First**, select the year and month you wish to view or work in.
- Add accounting items by using the "Add Income" and "Add Expense" buttons. The dialog is identical for both, except for the window title.
- The Incomes and Expenses will be in two separate columns; Income in the next-to-last to the right, and Expenses in the rightmost column.
- This separates them, making it easier to see which is which.
- The **bottom bar** show the summed Incomes and Expensens, as well as the **Result**.
- **Result** is simply Accounted Incomes minus Expenses.

#### New Accounting Entry

![accounting-02-expense-01](uploads/acf985becdd59060e27eaaf6b2c552c5/accounting-02-expense-01.png)

- **The new entry must include:**
  * **Date:** Select from the date picker widget.
  * **Amount:** Input numerical amount.
  * **Category:** Select from the menu. The category must already exist.
- **Optional:**
  * **Receipt:** You can add a receipt, or other document, by using the "Choose file …" button in the new entry dialog.
  * **Repeat:** By checking the Repeat checkbox, you can select a frequency of recurrence from the combobox dropdown menu. It can be either weekly, montly, quarterly or yearly.
    * **Weekly** will add a new identical entry each week (7 days) for 52 weeks (1 year).
    * **Monthly** will add a new identical entry at the same date every month, one year forward. E.g. if the initial entry is set to 13. April 2023, the next entries will be 13. May 2023, 13. June 2023, and so on.
    * **Quarterly** will add 4 entries with three months between from the date of the initial entry. E.g. if the initial entry is set to 02. February 2023, the next will be 02. May 2023, 02. August 2023, and finally 02. November 2023.
    * **Yearly** will add a new entry at the same date for 2 years. E.g. if the inital entry is set to 09. September 2023, the next will be 09. September 2024 and 09. September 2025.

#### Edit or Delete Accounting Entry

You can **edit** the amount (numerical value) of an entry by using selecting an entry in the table and clicking the "Edit" button.

![accounting-03-edit_entry](uploads/0c8beb42d76f30129719ad35658ca838/accounting-03-edit_entry.png)

To **delete** an entry, simply select the entry you wish to remove and click "Delete". This will delete a single entry, and will not affect any others even if it was created as part of recurringentries.

## Documents

The Documents page will let you find and open all documents or receipts added via Accounting entries.

![documents-02-preview](uploads/b7882b6592984888cd9e0eae11aa3dae/documents-02-preview.png)

* Select any document to preview it in the Preview pane.
* Clicking "Open", in the upper right corner, will open the selected file in your system PDF viewer. From there, you can save, move or share the file, according to what the application allows. Usually "File > Save As …" will let you save the document to a new location, if you need to share or send it.
* "Delete" will delete the file from the application.


## Settings

Settings to configure Baecon. Ordered in three categories: General, Accessibility and Categories.

#### General settings

- **Default Currency**: Choose the currency to display in the app.
- **Change the displayed currency.** Does not affect anything other than changing which identifier is shown. E.g. "450 EUR" instead of "450 NOK".
- **Open file location for "Documents"** will show the directory in which the Baecon app have stored documents and receipts. This will open in your system's file explorer
  * NOTE: If you remove any files from this directory, they will no longer show up inside the Baecon application. It is recommended to **copy** (ctrl + c, or via right-click context menu) the files if you need to move them.

![final-Settings-01-General](uploads/b7a40746b737b60ce92257018211318c/final-Settings-01-General.png)

#### Accessibility settings

- **Show negative numbers in red**: Negative numbers will be colored red, in addition to the default minus sign.
- **Colorblind Mode**: Switches the colors in the graphs from Green to Black, and Red to Grey for Dashboard, Budget and Accounting page.

![final-Settings-02-Accessibility](uploads/d9a449cdb14ebdcacd5708cf0f409611/final-Settings-02-Accessibility.png)

#### Categories settings

- **New**: Add a new Category.
- **Edit**: Selecting a category from the list, then clicking Edit will open a dialog for chaning the name of the selected category. Click "Apply" to confirm, or "Cancel" to abort.

![final-Settings-03-Categories-01](uploads/28a96135d8644d9d4c1a950c4a048064/final-Settings-03-Categories-01.png)

##### New/Edit category

Showing the "Edit" dialog window. The old name will be pre-filled in the text field. Input the new name and click "Apply" to confirm, or "Cancel" to abort. The "New" dialog looks almost identical, but with an empty text field.

![final-Settings-03-Categories-02-New](uploads/42f156c048adee8a1dd457b64b848e93/final-Settings-03-Categories-02-New.png)

![final-Settings-03-Categories-03-Edit](uploads/179c2847c630b21c07225ab34d11f366/final-Settings-03-Categories-03-Edit.png)

##### Delete category

By deleting a category, you will also delete every entry in Budget of Accounting associated with the cateogry.

Deleting will require confirming the deletion dialog.

![final-Settings-03-Categories-04-Delete](uploads/da1f91d4df9fdb9e07cb62454dc4a046/final-Settings-03-Categories-04-Delete.png)