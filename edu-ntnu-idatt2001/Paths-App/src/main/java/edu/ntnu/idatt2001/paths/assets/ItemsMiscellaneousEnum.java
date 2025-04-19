/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for Items category: Miscellaneous items
 */
public enum ItemsMiscellaneousEnum {
  BACKPACK("Bow", "Some item description", "file:src/main/resources/items/128x128/backpack.png"),
  COIN("Bow", "Some item description", "file:src/main/resources/items/128x128/coin.png"),
  DOCUMENT("Bow", "Some item description", "file:src/main/resources/items/128x128/document.png"),
  ENVELOPE("Bow", "Some item description", "file:src/main/resources/items/128x128/envelope.png"),
  GEM_GREEN("Bow", "Some item description", "file:src/main/resources/items/128x128/gem_green.png"),
  GEM("Bow", "Some item description","file:src/main/resources/items/128x128/gem.png"),
  GEM_RED("Bow", "Some item description", "file:src/main/resources/items/128x128/gem_red.png"),
  HEART("Bow", "Some item description", "file:src/main/resources/items/128x128/heart.png"),
  NECKLACE("Bow", "Some item description", "file:src/main/resources/items/128x128/necklace.png"),
  SCROLL("Bow", "Some item description", "file:src/main/resources/items/128x128/scroll.png"),
  STAR("Bow", "Some item description", "file:src/main/resources/items/128x128/star.png"),
  TEXT("Bow", "Some item description", "file:src/main/resources/items/128x128/text.png"),
  TOME("Bow", "Some item description", "file:src/main/resources/items/128x128/tome.png"),
  TOOLS("Bow", "Some item description", "file:src/main/resources/items/128x128/tools.png"),
  CROSS_X("Bow", "Some item description", "file:src/main/resources/items/128x128/cross_x.png");

  private final String itemName;
  private final String filePath;
  private final String itemDescription;


  /**
   * Constructor for enum.
   *
   * @param displayName
   * @param itemDescription
   * @param filePath
   */
  ItemsMiscellaneousEnum(String displayName, String itemDescription, String filePath) {
    this.itemName = displayName;
    this.filePath = filePath;
    this.itemDescription = itemDescription;
  }

  /**
   * Getter for item display name.
   *
   * @return itemName
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * Getter for item image file path.
   *
   * @return FILE_PATH
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Getter for item description.
   *
   * @return itemDescription
   */
  public String getItemDescription() {
    return itemDescription;
  }

  /**
   * toString for item display name and description.
   *
   * @return itemName, itemDescription
   */
  @Override
  public String toString() {
    return "ItemsMiscellaneousEnum{" +
        "itemName='" + itemName + '\'' +
        ", itemDescription='" + itemDescription + '\'' +
        '}';
  }
}
