/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for Items category: Potions
 */
public enum ItemsPotionsEnum {

  POTION_01("Potion 1", 25, 0, 10, "file:src/main/resources/items/128x128/potion1.png"),
  POTION_02("Potion 2", 0, 26, 0, "file:src/main/resources/items/128x128/potion2.png"),
  POTION_03("Potion 3", 5, 0, 25, "file:src/main/resources/items/128x128/potion3.png"),
  POTION_04("Potion 4", 25, 25, 25, "file:src/main/resources/items/128x128/potion4.png");

  private final String itemName;
  private final String filePath;
  private final int healthAdded;
  private final int manaAdded;
  private final int protectionAdded;


  /**
   * Constructor for enum.
   *
   * @param displayName
   * @param healthAdded
   * @param manaAdded
   * @param protectionAdded
   * @param filePath
   */
  ItemsPotionsEnum(String displayName, int healthAdded, int manaAdded, int protectionAdded, String filePath) {
    this.itemName = displayName;
    this.filePath = filePath;
    this.healthAdded = healthAdded;
    this.manaAdded = manaAdded;
    this.protectionAdded = protectionAdded;
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
   * Getter for amount health added.
   *
   * @return healthAdded
   */
  public int getHealthAdded() {
    return healthAdded;
  }

  /**
   * Getter for amount mana added.
   *
   * @return manaAdded
   */
  public int getManaAdded() {
    return manaAdded;
  }

  /**
   * Getter for amount protection added.
   *
   * @return protectionAdded
   */
  public int getProtectionAdded() {
    return protectionAdded;
  }

  /**
   * toString for item display name.
   * @return
   */
  @Override
  public String toString() {
    return getItemName();
  }
}
