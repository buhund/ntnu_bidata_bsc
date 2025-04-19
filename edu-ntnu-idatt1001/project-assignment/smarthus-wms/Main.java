/**
 * @author Kandidatnummer: XYZ
 * @project Mappevurdering, IDATT1001, BIDATA
 * @version v3.4
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class. Client class.
 */
public class Main {

  /**
   * Implementer for product register.
   */
  public ProductRegister productRegister = new ProductRegister();

  /**
   * Class scanner.
   */
  public Scanner scanner = new Scanner(System.in);

  /**
   * Main method. Initiate Client Menu.
   *
   * @param args application arguments
   */
  public static void main(String[] args) {
    Main mainClient = new Main();
    mainClient.userMenu();
  }

  /**
   * User Menu for WMS application.
   */
  public void userMenu() {
    int menuOptionInput = -1;
    while (menuOptionInput != 0) {
      System.out.println("-------------------------");
      System.out.println("*---- WMS USER MENU ----*");
      System.out.println("-------------------------");
      System.out.println("1: Print full inventory.");
      System.out.println("2: Search by Item ID Number or description.");
      System.out.println("3: Print items by product category.");
      System.out.println("4: Add new Item to inventory.");
      System.out.println("5: Update inventory for Item.");
      System.out.println("6: Delete item from inventory.");
      System.out.println("7: Edit Item Price.");
      System.out.println("8: Edit Item Description");
      System.out.println("9: Set Item Discount.");
      System.out.println("11: Add test data to inventory.");
      System.out.println("21: Functionality Testing: RW Permission");
      System.out.println("22: Deep/Shallow test");
      System.out.println("0: EXIT");

      // Scanner scanner = new Scanner(System.in);
      try {
        menuOptionInput = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException exception) {
        System.out.println("Please input a valid menu option.");
      }

      switch (menuOptionInput) {
        case 1 -> viewItemsInRegister();
        case 2 -> searchInventoryPartialMatch();
        case 3 -> viewAllInventoryInCategory();
        case 4 -> addNewItem();
        case 5 -> updateInventoryForItem();
        case 6 -> deleteItem();
        case 7 -> changeItemPrice();
        case 8 -> changeItemDescription();
        case 9 -> setItemDiscountPrice();
        case 11 -> addTestData();
        case 21 -> testReadWritePermission();
        case 22 -> deepShallowTest();
        case 0 -> System.out.println("Exiting. Bye, bye.");
        default -> System.out.println("ERROR. Please select a valid menu option!");
      }
    }
  }

  /**
   * Testing for shallow copy. Used to confirm the difference between deep and shallow copy og
   * ArrayList.
   */
  public void deepShallowTest() {
    System.out.println("Making a shallow copy of inventory. Shallow if true:");
    System.out.println(productRegister.testIsShallow());

    System.out.println(
        "Check if getter for inventory return a Deep Copy (false) or a Shallow Copy (true):");
    System.out.println(productRegister.testIsDeep());
  }

  /**
   * Testing the Deep Copy for unintentional write permission.
   */
  public void testReadWritePermission() {
    int testReadWriteMenuInput = -1;
    // Scanner scanner = new Scanner(System.in);
    System.out.println("System Functionality Testing: Read/Write Permission");
    System.out.println("1: Add badMenuItem");
    System.out.println("2: removeAll on inventory.");
    System.out.println("0: Exit.");
    try {
      testReadWriteMenuInput = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    }
    switch (testReadWriteMenuInput) {
      case 1 -> {
        System.out.println("Adding badTestItem: \n");
        Item badTestItem = new Item("badTestItem-123-BANAN", "Gulv med hull", 9999, 0, "Erik Gamle",
            123.4, 123.4, 123.4, 123.4, "Kufiolilla", 1000, Category.Flooring.name());
        productRegister.getInventoryOfItems().add(badTestItem);
        System.out.println("Inventory after attempting to add badTestItem");
        printShortStringInventory();
      }
      case 2 -> {
        System.out.println("Attempt to removeAll.");
        productRegister.getInventoryOfItems().removeAll(productRegister.getInventoryOfItems());
        System.out.println("Printing inventory/register. \n");
        System.out.println(productRegister.getInventoryOfItems());
      }
      case 0 -> System.out.println("Exiting.");
      default -> System.out.println("ERROR. Please select a valid menu option!");
    }
  }

  /**
   * Print/view all items currently in Register.
   */
  public void viewItemsInRegister() {
    int menuOptionInput = -1;
    System.out.println("Full list or short list?");
    System.out.println("1: Short information (Item ID + Description + Inventory).");
    System.out.println("2: Full information.");
    System.out.println("0: Exit to main menu.");
    // Scanner scanner = new Scanner(System.in);
    try {
      menuOptionInput = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    }
    switch (menuOptionInput) {
      case 1 -> printShortStringInventory();
      case 2 -> printFullStringInventory();
      case 0 -> System.out.println("Returning to main menu.");
      default -> System.out.println("Error. Please select a valid input option.");
    }
  }

  /**
   * Print shortened information.
   */
  public void printShortStringInventory() {
    for (int i = 0; i < productRegister.getInventoryOfItems().size(); i++) {
      System.out.println("IN" + (i + 1) + ": " + productRegister.getInventoryOfItems().get(i)
          .toShortStringItemId());
    }
  }

  /**
   * Print full information.
   */
  public void printFullStringInventory() {
    for (int i = 0; i < productRegister.getInventoryOfItems().size(); i++) {
      System.out.println("Index number " + (i + 1) + ":");
      System.out.println(productRegister.getInventoryOfItems().get(i));
    }
  }

  /**
   * Helper method to print search results without Java basic comma-separated ArrayList formatting.
   *
   * @param arrayToPrint Array to print
   */
  public void printArrayList(ArrayList<Item> arrayToPrint) {
    for (int i = 0; i < arrayToPrint.size(); i++) {
      System.out.println("Index number " + (i + 1) + ":");
      System.out.println(arrayToPrint.get(i));
    }
  }

  /**
   * Search inventory by partial match of either Item ID/Number or description. Printing using
   * {@link #printArrayList(ArrayList)}
   */
  public void searchInventoryPartialMatch() {
    // Scanner scanner = new Scanner(System.in);
    ArrayList<Item> searchResults;
    System.out.println("Input item ID number, or do a description-based search.");
    String searchString = scanner.nextLine();
    searchResults = productRegister.searchForItemsPartialMatch(searchString);
    if (searchResults.size() == 0) {
      System.out.println("No items found for query \"" + searchString + "\"");
    } else {
      System.out.println("Found the following items with search query " + "'" + searchString + "'");
      //System.out.println(searchResults.toString());
      printArrayList(searchResults);
    }
  }

  /**
   * Print every item in selected item/product category.
   */
  public void viewAllInventoryInCategory() {
    // Scanner scanner = new Scanner(System.in);
    ArrayList<Item> searchResults;
    System.out.println("Select the category of items you wish to view inventory for:");
    System.out.println(Category.toLongString());
    String strCategory = scanner.nextLine();
    Category category = Category.fromString(strCategory);
    if (category == null) {
      System.out.println("The category '" + strCategory + "' does not exist");
      return;
    }

    searchResults = productRegister.searchByCategory(category);

    if (searchResults.size() == 0) {
      System.out.println("No items found in that category.");
    } else {
      System.out.println(
          "Found the following items with search query " + "'" + category.name() + "'");
      printArrayList(searchResults);
    }
  }

  /**
   * Add new Item to register.
   */
  public void addNewItem() {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input Item info, as specified.");
    System.out.println("All numbers must be positive.");
    try {
      System.out.println("Input item ID number.");
      System.out.println("(See Supplier Item Catalogue for correct product IDs)");
      String itemIdNumber = scanner.nextLine();

      System.out.println("Input item description: ");
      String description = scanner.nextLine();

      System.out.println("Input item price: ");
      int itemPrice = Integer.parseInt(scanner.nextLine());

      System.out.println("Input brand name: ");
      String brandName = scanner.nextLine();

      System.out.println("Input item weight (kg): ");
      double weight = Double.parseDouble(scanner.nextLine());

      System.out.println("Input item length (mm): ");
      double length = Integer.parseInt(scanner.nextLine());

      System.out.println("Input item height (mm): ");
      double height = Double.parseDouble(scanner.nextLine());

      System.out.println("Input item width (mm): ");
      double width = Double.parseDouble(scanner.nextLine());

      System.out.println("Input item color: ");
      String color = scanner.nextLine();

      System.out.println("Input the number of items to be put on shelf: ");
      int itemsOnShelf = Integer.parseInt(scanner.nextLine());

      System.out.println("Assign item category: ");
      System.out.println(Category.toLongString());
      String strCategory = scanner.nextLine();
      if (Category.fromString(strCategory) == null) {
        System.out.println("The category '" + strCategory + "' does not exist");
        return;
      }

      if (!productRegister.addNewItem(itemIdNumber, description, itemPrice, itemPrice, brandName,
          weight, length, height, width, color, itemsOnShelf, strCategory)) {
        System.out.println("Item already exists");
      } else {
        System.out.println("Item added to inventory register.");
      }

    } catch (Exception exception) {
      System.out.println("Exception occurred: " + exception.getMessage());
    }
  }

  /**
   * Update number of items in stock for a given Item. Identify Item by "Item ID Number" to update.
   * Using separate methods for manipulating stock/inventory:
   * {@link #addNumberToInventory(String, int)} {@link #subtractNumberFromInventory(String, int)}
   * {@link #setNewTotalInventory(String, int)}
   */
  public void updateInventoryForItem() {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input ID Number of item you wish to update stock for.");
    System.out.println(
        "The Item ID must be an exact match. Use the item search, if unsure it item ID.");
    try {
      String itemIdNumber = scanner.nextLine();
      Item itemOnShelf = productRegister.searchForitemIdNumber(itemIdNumber);
      if (itemOnShelf == null) {
        System.out.println("Item not found. Please try again.");
      } else {
        int currentStock = itemOnShelf.getItemsOnShelf();
        System.out.println("Current stock/status for item " + itemIdNumber + ": " + currentStock);
        System.out.println("Select action:");
        System.out.println("1: Add to current stock.");
        System.out.println("2: Subtract from current stock.");
        System.out.println("3: Set new stock total, i.e. overwrite previous stock.");
        System.out.println("0: Exi0t to main menu.");

        int menuOptionInput = Integer.parseInt(scanner.nextLine());

        switch (menuOptionInput) {
          case 1 -> addNumberToInventory(itemIdNumber, currentStock); // Add to stock.
          case 2 -> subtractNumberFromInventory(itemIdNumber, currentStock); // Subtract from stock.
          case 3 -> setNewTotalInventory(itemIdNumber,
              currentStock); // Set new total, i.e. overwrite old.
          case 0 -> System.out.println("Returning to main menu.");
          default -> System.out.println("Error. Please select a valid input option.");
        }
      }
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    } catch (Exception exception) {
      System.out.println("Exception occurred: " + exception.getMessage());
    }
  }

  /**
   * Update inventory --> Add number of items to inventory. See "parent" method:
   * {@link #updateInventoryForItem()}
   *
   * @param itemIdNumber Item ID Number
   * @param currentStock Current amount of Item available in inventory
   */

  public void addNumberToInventory(String itemIdNumber, int currentStock) {
    System.out.println("Input the number to be added to current stock.");
    int addToShelf = Integer.parseInt(scanner.nextLine());
    if (addToShelf < 0) {
      throw new IllegalArgumentException("Input must be a positive integer.");
    }
    int newStock = currentStock + addToShelf; // Adding.
    if (productRegister.updateAmountItemsOnShelf(itemIdNumber, newStock)) {
      System.out.println(
          "Confirming new stock: " + productRegister.searchForitemIdNumber(itemIdNumber)
              .getItemsOnShelf());
    }
    System.out.println("Item does not exist.");
  }

  /**
   * Update inventory --> Subtract number of items from inventory See "parent" method.
   * {@link #updateInventoryForItem()}
   *
   * @param itemIdNumber Item ID Number
   * @param currentStock Current amount of Item available in inventory
   */
  public void subtractNumberFromInventory(String itemIdNumber, int currentStock) {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input the number to be subtracted from stock.");
    int subtractFromShelf = Integer.parseInt(scanner.nextLine());
    if (subtractFromShelf < 0) {
      throw new IllegalArgumentException("Input a positive value.");
    }
    int newStock = currentStock - subtractFromShelf; // Subtract.
    productRegister.updateAmountItemsOnShelf(itemIdNumber, newStock);
    System.out.println(
        "Confirming new stock: " + productRegister.searchForitemIdNumber(itemIdNumber)
            .getItemsOnShelf());
  }

  /**
   * Update inventory --> Set new total inventory. I.e. overwrite old stock with new. See "parent"
   * method: {@link #updateInventoryForItem()}
   *
   * @param itemIdNumber    Item ID Number
   * @param currentOldStock Current amount of Item available in inventory prior to update, "the old"
   *                        after update
   */
  public void setNewTotalInventory(String itemIdNumber, int currentOldStock) {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input new total stock on shelf for item " + itemIdNumber);
    int newTotal = Integer.parseInt(scanner.nextLine());
    if (newTotal < 0) {
      throw new IllegalArgumentException("Input can not be less than zero.");
    }
    productRegister.searchForitemIdNumber(itemIdNumber).setItemsOnShelf(newTotal); // New total.
    System.out.println(
        "Confirming new stock: " + productRegister.searchForitemIdNumber(itemIdNumber)
            .getItemsOnShelf());
    System.out.println("(Previously: " + currentOldStock + ")");
  }


  /**
   * Delete Item. Identify item by "Item ID Number". Confirmation dialogue.
   */
  public void deleteItem() {
    System.out.println("Deletion Menu. Continue, or go back?");
    System.out.println("1: Go back to the main menu.");
    System.out.println("2: Continue, I know what I'm doing.");
    // Scanner scanner = new Scanner(System.in);
    try {
      int menuInput = Integer.parseInt(scanner.nextLine());
      switch (menuInput) {
        case 1 -> System.out.println("Returning to main menu.\n");
        case 2 -> {
          System.out.println("Input the Item ID Number for the product you wish to delete: ");
          String itemIdNumber = scanner.nextLine();
          System.out.println(
              "This will delete the item from inventory database. Are you really sure?");
          System.out.println("1: No, please take me back to the main menu.");
          System.out.println("2: Yes, I know what I'm doing.");
          int deleteConfirmation = Integer.parseInt(scanner.nextLine());
          switch (deleteConfirmation) {
            case 1 -> System.out.println("Returning to main menu.\n");
            case 2 -> {
              if (productRegister.deleteItemFromInventory(itemIdNumber)) {
                System.out.println("Item deleted.");
              } else {
                System.out.println("Could not delete item");
              }
            }
            default -> System.out.println("ERROR. Please select a valid menu options!");
          }
        }
        default -> System.out.println("Please input a valid menu option.");
      }
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    }
  }

  /**
   * Change/update price for Item.
   */
  public void changeItemPrice() {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input Item ID Number for the item you wish to change price: ");
    String itemIdNumber = scanner.nextLine();
    int currentPrice = productRegister.searchForitemIdNumber(itemIdNumber).getItemPrice();
    System.out.println("Current price for item " + itemIdNumber + ": " + currentPrice);

    System.out.println("Input new price: ");
    try {
      int newPrice = Integer.parseInt(scanner.nextLine());
      if (productRegister.updateItemPrice(itemIdNumber, newPrice)) {
        System.out.println("Price updated!");
      } else {
        System.out.println("Could not update price!");
      }
    } catch (Exception exception) {
      System.out.println("Exception occurred: " + exception.getMessage());
    }
  }

  /**
   * Change/update description for Item.
   */
  public void changeItemDescription() {
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input Item ID Number for the item you wish to change description: ");
    String itemIdNumber = scanner.nextLine();
    String currentDescription = productRegister.searchForitemIdNumber(itemIdNumber).getDescription();
    System.out.println("Current description for item " + itemIdNumber + ": " + currentDescription);

    System.out.println("Input new description: ");
    try {
      String newDescription = scanner.nextLine();
      if (productRegister.updateItemDescription(itemIdNumber, newDescription)) {
        System.out.println("Description updated!");
      } else {
        System.out.println("Could not update description");
      }
    } catch (Exception exception) {
      System.out.println("Exception occurred: " + exception.getMessage());
    }
  }

  /**
   * Set item discount via changing Final Price. Final Price = Checkout price, selling price. See
   * {@link #discountPercent(int, String)} See {@link #discountNumerical(int, String)}
   */
  public void setItemDiscountPrice() {
    // Scanner scanner = new Scanner(System.in);

    System.out.println("Input Item ID Number for the item you wish to change price: ");
    String itemIdNumber = scanner.nextLine();
    int preDiscountPrice = productRegister.searchForitemIdNumber(itemIdNumber).getItemPrice();
    System.out.println("Current price for item " + itemIdNumber + ": " + preDiscountPrice);

    int menuOptionInput = -1;
    System.out.println("Apply discount by percentage or by numerical value: ");
    System.out.println("1: Percent");
    System.out.println("2: Numerical");
    System.out.println("0: Return to main menu.");
    try {
      menuOptionInput = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    }
    try {
      switch (menuOptionInput) {
        case 1 -> discountPercent(preDiscountPrice, itemIdNumber);
        case 2 -> discountNumerical(preDiscountPrice, itemIdNumber);
        case 0 -> System.out.println("Returning to main menu.");
        default -> System.out.println("Error. Please select a valid input option.");
      }
    } catch (NumberFormatException exception) {
      System.out.println("Please input a valid menu option.");
    }
  }

  /**
   * Set price discount by Percent. E.g. "20% off normal price".
   *
   * @param preDiscountPrice Price prior to being discounted
   * @param itemIdNumber     Item ID Number
   */
  public void discountPercent(int preDiscountPrice, String itemIdNumber) {
    int discountPercentInput;
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input discount percentage (whole number): ");
    discountPercentInput = Integer.parseInt(scanner.nextLine());
    int newDiscountedPricePct = (int) (preDiscountPrice * ((100.0 - discountPercentInput)
        / 100.0)); // Cast double inside () to int
    productRegister.setDiscountPrice(itemIdNumber, newDiscountedPricePct);

    System.out.println("Original price: " + preDiscountPrice);
    System.out.println("Reduced by: " + discountPercentInput + "%");
    System.out.println("New discounted price: " + newDiscountedPricePct);
  }

  /**
   * Set price discount by flat numerical/integer value. E.g. "100 NOK off normal price".
   *
   * @param preDiscountPrice Price prior to being discounted
   * @param itemIdNumber     Item ID Number
   */
  public void discountNumerical(int preDiscountPrice, String itemIdNumber) {
    int discountNumValue;
    // Scanner scanner = new Scanner(System.in);
    System.out.println("Input discount amount (whole number): ");
    discountNumValue = Integer.parseInt(scanner.nextLine());
    int newDiscountedPriceNumValue = (preDiscountPrice - discountNumValue);
    if (productRegister.setDiscountPrice(itemIdNumber, newDiscountedPriceNumValue)) {
      System.out.println("Price discounted!");
    } else {
      System.out.println("Could not discount price!");
      return;
    }

    System.out.println("Original price: " + preDiscountPrice);
    System.out.println("Reduced by: " + discountNumValue);
    System.out.println("New discounted price: " + newDiscountedPriceNumValue);
  }


  /**
   * Add test data to register/inventory.
   */
  public void addTestData() {
    // Cat 1: Floor laminate
    productRegister.addNewItem("FLLM-PERG-1001", "Floor laminate, click-system, Nordic Oak", 329, 0,
        "Pergo", 24.3, 2075, 57, 258, "Nordic Oak", 150, Category.Flooring.name());
    productRegister.addNewItem("FLLM-STAR-2001", "Floor laminate, Super-click-system, Smoked Pine",
        398, 0, "Star Floor", 26.8, 2075, 60, 260, "Nordic Oak", 130, Category.Flooring.name());
    // Cat 2: Window
    productRegister.addNewItem("DOOR-SWDR-5001", "Front Door, Fire-Stopper, white with window",
        6699, 0, "SweDoor", 40, 2030, 155, 940, "Egg White", 90, Category.Door.name());
    productRegister.addNewItem("DOOR-DADR-5005", "Front Door, Fire-Stopper, black, non-window",
        5399, 0, "DanDør", 46, 2030, 155, 940, "Slate Grey", 90, Category.Door.name());
    // Cat 3: Door
    productRegister.addNewItem("WIND-NODA-7001", "Window, non-opening, 3-layer, living room", 3699,
        0, "NorDan", 140.2, 95, 3100, 2100, "Egg White", 60, Category.Window.name());
    productRegister.addNewItem("WIND-PASS-7005", "Window, upward-opening, 2-layer, bedroom", 2499,
        0, "NorDan", 40.2, 95, 1188, 1088, "Egg White", 60, Category.Window.name());
    // Cat 4: Lumber
    productRegister.addNewItem("LMBR-MOEL-9001", "CU-Impregnated natural pine, patio, veranda", 19,
        0, "Moelven", 1.88, 1000, 2.8, 12, "Natural Pine CU-Imp", 290, Category.Lumber.name());
    productRegister.addNewItem("LMBR-MOEL-9005", "Royal slotted profile, Royal pine, veranda", 29,
        0, "Moelven", 1.88, 1000, 2.8, 12, "Royal-Imp Brown Pine", 240, Category.Lumber.name());
    // Simple items
    productRegister.addNewItem("111", "Description 1", 100, 0, "123", 123, 123, 123, 123, "123",
        123, Category.Flooring.name());
    productRegister.addNewItem("222", "Description 2", 200, 0, "123", 123, 123, 123, 123, "123",
        123, Category.Window.name());
    productRegister.addNewItem("333", "Description 3", 300, 0, "123", 123, 123, 123, 123, "123",
        123, Category.Door.name());
    productRegister.addNewItem("444", "Description 4", 400, 0, "123", 123, 123, 123, 123, "123",
        123, Category.Lumber.name());
  }

  // End of class.
}
