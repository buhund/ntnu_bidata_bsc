/**
 * @author Kandidatnummer: XYZ
 * @project Mappevurdering, IDATT1001, BIDATA
 * @version v3.4
 */

import java.util.Objects;

/**
 * Override method for Clone interface. To enable proper Deep Copy of ProductRegister ArrayList.
 */
public class Item implements Cloneable {

  /**
   * Item ID number, consisting of letter and numbers: Format for testing: CCCC-BBBB-nnnn CCCC =
   * Four-letter shortened category. BBBB = Four-letter shortened brand name. nnnn = Four-digit
   * identifier. Otherwise, input as per supplier ID number.
   */
  private String itemIdNumber;

  // Class variables, attributes
  /**
   * Short description of the item/product.
   */
  private String description;
  /**
   * Item price in NOK. Initial base price for item. Basis for Final Price and Discount calculation
   * methods.
   */
  private int itemPrice;
  /**
   * Final Price. Actual price paid at checkout. When no discount is applied, it will be equal to
   * itemPrice. Will update to reflect any discounts, if discount method invoked.
   */
  private int finalPrice;
  /**
   * Brand name of item.
   */
  private String brandName;
  /**
   * Item weight in kilogram.
   */
  private double weight;
  /**
   * Item length in millimeters.
   */
  private double length;
  /**
   * Item heights in millimeters.
   */
  private double height;
  /**
   * Item width in millimeters. This dimension was not included in the assignment, but is quite
   * important when actually building things.
   */
  private double width;
  /**
   * Item color.
   */
  private String color;
  /**
   * Number of items "on shelf", i.e. available in warehouse.
   */
  private int itemsOnShelf;
  /**
   * Item/product category. 1 = Floor laminate 2 = Window 3 = Door 4 = Lumber
   */
  private Category itemCategory;

  /**
   * Constructor for Item class.
   *
   * @param itemIdNumber ID of item
   * @param description  Item description
   * @param itemPrice    Item price
   * @param finalPrice   Final item sales price
   * @param brandName    Item brand
   * @param weight       Item weight
   * @param length       Item length
   * @param height       Item height
   * @param width        Item width
   * @param color        Item color
   * @param itemsOnShelf Items on shelf
   * @param itemCategory Item category
   */
  public Item(String itemIdNumber, String description, int itemPrice, int finalPrice,
      String brandName, double weight, double length, double height, double width, String color,
      int itemsOnShelf, String itemCategory) {

    if (itemIdNumber == null || itemIdNumber.isBlank()) {
      throw new IllegalArgumentException("Item ID Number can not be empty.");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Item Description can not be empty.");
    }
    if (itemPrice < 0) {
      throw new IllegalArgumentException("Item price can not be less than zero!");
    }
    if (finalPrice < 0) {
      throw new IllegalArgumentException("Final price can not be less than zero!");
    }
    if (brandName == null || brandName.isBlank()) {
      throw new IllegalArgumentException("Brand name can not be empty.");
    }
    if (weight < 0) {
      throw new IllegalArgumentException("Weight can not be less than zero!");
    }
    if (length < 0) {
      throw new IllegalArgumentException("Length can not be less than zero!");
    }
    if (height < 0) {
      throw new IllegalArgumentException("Height can not be less than zero!");
    }
    if (width < 0) {
      throw new IllegalArgumentException("Width can not be less than zero!");
    }
    if (color == null || color.isBlank()) {
      throw new IllegalArgumentException("Color name can not be empty.");
    }
    if (itemsOnShelf < 0) {
      throw new IllegalArgumentException("Items on shelf can not be less than zero!");
    }
    if (Category.fromString(itemCategory) == null) {
      throw new IllegalArgumentException("That item category does not exist.");
    }

    this.itemIdNumber = itemIdNumber;
    this.description = description;
    this.itemPrice = itemPrice;
    this.finalPrice = finalPrice;
    this.brandName = brandName;
    this.weight = weight;
    this.length = length;
    this.height = height;
    this.width = width;
    this.color = color;
    this.itemsOnShelf = itemsOnShelf;
    this.itemCategory = Category.fromString(itemCategory);
  }

  // Constructors

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Item item = (Item) super.clone();
    item.itemIdNumber = this.itemIdNumber;
    item.description = this.description;
    item.itemPrice = this.itemPrice;
    item.finalPrice = this.finalPrice;
    item.brandName = this.brandName;
    item.weight = this.weight;
    item.length = this.length;
    item.height = this.height;
    item.width = this.width;
    item.color = this.color;
    item.itemsOnShelf = this.itemsOnShelf;
    item.itemCategory = this.itemCategory;
    return item;
  }

  // Getters

  /**
   * Getter for Item ID Number.
   *
   * @return itemIdNumber
   */
  public String getItemIdNumber() {
    return itemIdNumber;
  }

  /**
   * Getter for item Description.
   *
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter for item Description.
   *
   * @param description Item description
   */
  public void setDescription(String description) {
    if (description == null || description.length() == 0) {
      throw new IllegalArgumentException("Item Description can not be empty.");
    }
    this.description = description;
  }

  /**
   * Getter for Item price.
   *
   * @return itemPrice
   */
  public int getItemPrice() {
    return itemPrice;
  }

  /**
   * Setter for Item Price.
   *
   * @param itemPrice Item price
   */
  public void setItemPrice(int itemPrice) {
    if (itemPrice < 0) {
      throw new IllegalArgumentException("Items on shelf cannot be less than zero!");
    }
    this.itemPrice = itemPrice;
  }

  /**
   * Getter for Final Price. Item Price * Discount
   *
   * @return Final sales price
   */
  public int getFinalPrice() {
    return finalPrice;
  }

  /**
   * Setter for Final Price. I.e. price after discount.
   *
   * @param finalPrice Item final sales price
   */
  public void setFinalPrice(int finalPrice) {
    this.finalPrice = finalPrice;
  }

  /**
   * Getter for Item brand name.
   *
   * @return brandName
   */
  public String getBrandName() {
    return brandName;
  }

  /**
   * Getter for Weight.
   *
   * @return weight
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Getter for Length.
   *
   * @return length
   */
  public double getLength() {
    return length;
  }

  /**
   * Getter for Height.
   *
   * @return height
   */
  public double getHeight() {
    return height;
  }

  /**
   * Getter for Width.
   *
   * @return width
   */
  public double getWidth() {
    return width;
  }

  // Setters

  /**
   * Getter for Color.
   *
   * @return color
   */
  public String getColor() {
    return color;
  }

  /**
   * Getter for Items in shelf / in stock.
   *
   * @return itemsOnShelf
   */
  public int getItemsOnShelf() {
    return itemsOnShelf;
  }

  /**
   * Setter for "Items on shelf". I.e. the amount of items available in inventory/warehouse stock.
   * Colloquially know as being "on shelf".
   *
   * @param itemsOnShelf Items on shelf
   */
  public void setItemsOnShelf(int itemsOnShelf) {
    if (itemsOnShelf < 0) {
      throw new IllegalArgumentException("Items on shelf cannot be less than zero!");
    }
    this.itemsOnShelf = itemsOnShelf;
  }

  /**
   * Getter for Item category.
   *
   * @return itemCategory
   */
  public Category getItemCategory() {
    return itemCategory;
  }

  // To-String methods

  /**
   * toString for full Item information.
   *
   * @return String with full description of item
   */
  public String toString() {
    return "Item ID Number: " + getItemIdNumber() + "\n" + "Description: " + getDescription() + "\n"
        + "Price: " + getItemPrice() + "\n" + "Final price: " + getFinalPrice()
        + " (after discounts) \n" + "Brand: " + getBrandName() + "\n" + "Weight: " + getWeight()
        + " kg\n" + "Length: " + getLength() + " mm\n" + "Height: " + getHeight() + " mm\n"
        + "Width: " + getWidth() + " mm\n" + "Dimensions (HxLxW): " + getHeight() + " x "
        + getLength() + " x " + getWidth() + "  mm\n" + "Color: " + getColor() + "\n"
        + "Items on shelf: " + getItemsOnShelf() + "\n" + "Item category: "
        + getItemCategory().name() + "\n";
  }

  /**
   * toString for short and concise Item information. List-view friendly.
   *
   * @return String with short description of item
   */
  public String toShortStringItemId() {
    return getItemIdNumber() + " (" + getDescription() + "). On shelf: " + getItemsOnShelf() + "\n";
  }

  // Override methods

  /**
   * Override for equals method, to enable use on Objects, not just primitives.
   *
   * @param o Object to compare with
   * @return True if equal, false if not
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return itemPrice == item.itemPrice && finalPrice == item.finalPrice
        && Double.compare(item.weight, weight) == 0 && Double.compare(item.length, length) == 0
        && Double.compare(item.height, height) == 0 && Double.compare(item.width, width) == 0
        && itemsOnShelf == item.itemsOnShelf && itemCategory == item.itemCategory
        && itemIdNumber.equals(item.itemIdNumber) && description.equals(item.description)
        && brandName.equals(item.brandName) && color.equals(item.color);
  }

  /**
   * Override for hashCode method, to enable return of Object hash.
   *
   * @return Hash-code of item
   */
  @Override
  public int hashCode() {
    return Objects.hash(itemIdNumber, description, itemPrice, finalPrice, brandName, weight, length,
        height, width, color, itemsOnShelf, itemCategory);
  }

  // End of class.
}
