/**
 * @author Kandidatnummer: XYZ
 * @project Mappevurdering, IDATT1001, BIDATA
 * @version v3.4
 */

import java.util.ArrayList;

/**
 * Class ProductRegister.
 */
public class ProductRegister {

  /**
   * Create ArrayList, holding datatype Item. Using ArrayList because it's easy to expand with new
   * elements/objects. Downside is that it allows duplicates A register/inventory of Items.
   */
  private final ArrayList<Item> inventoryOfItems = new ArrayList<>();


  /**
   * Getter for inventoryOfItems ArrayList. Return a deep copy of the list.
   *
   * @return inventoryOfItems deep copy
   */
  @SuppressWarnings("unchecked") // We know the type returned from clone()
  public ArrayList<Item> getInventoryOfItems() {
    return (ArrayList<Item>) inventoryOfItems.clone();
  }

  // Methods for testing Clone method in Item class for getInventoryOfItems.

  /**
   * Testing to confirm shallow copy.
   *
   * @return shallow copy of inventory.
   */
  @SuppressWarnings("all")
  // Yes, we know we can innline the variable. Yes, we know the tes will always be true...
  public boolean testIsShallow() {
    ArrayList<Item> shallowInventory = inventoryOfItems;
    return (inventoryOfItems == shallowInventory);
  }

  /**
   * Testing to check if method return a deep or shallow copy.
   *
   * @return deep copy of inventory.
   */
  public boolean testIsDeep() {
    return ((inventoryOfItems == getInventoryOfItems()));
  }

  // Methods for manipulating and viewing inventory.

  /**
   * Add new item to Product Inventory. Checking for duplicates via
   * {@link #checkForDuplicate(String)}
   *
   * @param itemIdNumber Item ID Number
   * @param description  Item description
   * @param itemPrice    Initial item price
   * @param finalPrice   Price at checkout (with discounts, if applicable)
   * @param brandName    Item Brand name
   * @param weight       Item weight
   * @param length       Item length
   * @param height       Item height
   * @param width        Item width
   * @param color        Item color
   * @param itemsOnShelf Amount of items available in inventory ("on shelf")
   * @param itemCategory Item category
   * @return true if successfully added new Item (i.e. item did not exist).
   *      false if unable to add new Item (i.e. item did exist already).
   */
  public boolean addNewItem(String itemIdNumber, String description, int itemPrice, int finalPrice,
      String brandName, double weight, double length, double height, double width, String color,
      int itemsOnShelf, String itemCategory) {

    if (!checkForDuplicate(itemIdNumber)) { // if NOT true
      Item newItem = new Item(itemIdNumber, description, itemPrice, finalPrice, brandName, weight,
          length, height, width, color, itemsOnShelf, itemCategory);
      inventoryOfItems.add(newItem); // then add
      return true; // No duplicate found -> Add to register
    }
    return false; // Duplicate found -> Do not add to register.
  }


  /**
   * Check if Item object already exists in (deep copy of) register ArrayList. Checking by Item ID
   * Number.
   *
   * @param checkItemId Item ID to check
   * @return true = does exist = Add to register, false = does not exist = Do not add to register
   */
  public boolean checkForDuplicate(String checkItemId) {
    int checkItemHash = checkItemId.hashCode();
    for (Item item : getInventoryOfItems()) {
      return item.getItemIdNumber().hashCode() == checkItemHash; // True if duplicate
    }
    return false; // False if NO duplicate
  }


  /**
   * Search for Item by partial Description and/or Item ID Number Compare input to both itemIdNumber
   * og ItemDescription. If either is a match, then return matching item.
   *
   * @param searchString Partial Item ID or description.
   * @return ArrayList containing items found to match
   */
  public ArrayList<Item> searchForItemsPartialMatch(String searchString) {
    searchString = searchString.toUpperCase();
    ArrayList<Item> foundItems = new ArrayList<>();
    for (Item item : getInventoryOfItems()) {
      boolean itemIdMatch = item.getItemIdNumber().toUpperCase().contains(searchString);
      boolean descriptionMatch = item.getDescription().toUpperCase().contains(searchString);
      if (itemIdMatch || descriptionMatch) {
        foundItems.add(item);
      }
    }
    return foundItems;
  }


  /**
   * Search by Item ID Number.
   *
   * @param searchString Item ID Number to search for.
   * @return placeholderItem = the Item matching the search string, null if no item match found
   */
  public Item searchForitemIdNumber(String searchString) {
    searchString = searchString.toUpperCase();
    for (Item item : getInventoryOfItems()) {
      if (item.getItemIdNumber().toUpperCase().matches(searchString)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Create ArrayList with items matching a category. Return the AL.
   *
   * @param category item category
   * @return foundItemsInCategory ArrayList
   */
  public ArrayList<Item> searchByCategory(Category category) {
    ArrayList<Item> foundItemsInCategory = new ArrayList<>();
    for (Item item : getInventoryOfItems()) {
      if (category != null && item.getItemCategory().name().equals(category.name())) {
        foundItemsInCategory.add(item);
      }
    }
    return foundItemsInCategory;
  }

  /**
   * Add or subtract from current number of items on shelf for a given product.
   *
   * @param itemIdNumber     Item ID Number
   * @param updateWithNumber New updated inventory
   */
  public boolean updateAmountItemsOnShelf(String itemIdNumber, int updateWithNumber) {
    Item placeholderItem = searchForitemIdNumber(itemIdNumber);
    if (placeholderItem == null) {
      return false; // No item found
    }
    placeholderItem.setItemsOnShelf(updateWithNumber);
    return true; // Successfully updated Item
  }

  /**
   * Delete item from stock, via search function.
   *
   * @param itemIdNumber Item ID to find and delete.
   */
  public boolean deleteItemFromInventory(String itemIdNumber) {
    Item placeholderItem = searchForitemIdNumber(itemIdNumber);
    if (placeholderItem == null) {
      return false; // No item found
    }
    inventoryOfItems.remove(placeholderItem);
    return true; // Successfully updated Item
  }

  /**
   * Change price for product/item.
   *
   * @param itemIdNumber Item ID to change price for
   * @param newPrice     New Item price
   */
  public boolean updateItemPrice(String itemIdNumber, int newPrice) {
    try {
      Item placeholderItem = searchForitemIdNumber(itemIdNumber);
      if (placeholderItem == null) {
        return false; // No item found
      }
      placeholderItem.setItemPrice(newPrice);
      placeholderItem.setFinalPrice(newPrice);
      return true; // Successfully updated Item
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }
    return false; // Exception occurred
  }

  /**
   * Set discount price, by setting Final Price. Discount is made in the following methods:
   * {@link Main#setItemDiscountPrice()} Discount menu {@link Main#discountPercent(int, String)}
   * Discount by percent {@link Main#discountNumerical(int, String)} Discount by int currency value
   * off price
   *
   * @param itemIdNumber  Item to discount
   * @param newFinalPrice Final sales price
   */
  public boolean setDiscountPrice(String itemIdNumber, int newFinalPrice) {
    try {
      Item placeholderItem = searchForitemIdNumber(itemIdNumber);
      if (placeholderItem == null) {
        return false; // No item found
      }
      placeholderItem.setFinalPrice(newFinalPrice);
      return true; // Successfully updated Item
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }
    return false; // Exception occurred
  }

  /**
   * Change/update description of item/product.
   *
   * @param itemIdNumber   Item ID to change description of
   * @param newDescription New description to replace old
   */
  public boolean updateItemDescription(String itemIdNumber, String newDescription) {
    try {
      Item placeholderItem = searchForitemIdNumber(itemIdNumber);
      if (placeholderItem == null) {
        return false; // No item found
      }
      placeholderItem.setDescription(newDescription);
      return true; // Successfully updated Item
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }
    return false; // Exception occurred
  }

  // End of class ProductRegister.
}
