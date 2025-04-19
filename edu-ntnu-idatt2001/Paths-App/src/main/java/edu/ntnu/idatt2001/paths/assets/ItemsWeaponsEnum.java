/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for Items category: Weapons
 */
public enum ItemsWeaponsEnum {

  AXE_01("Good Axe", 10, 25, 1, "file:src/main/resources/items/128x128/axe1.png"),
  AXE_02("Better Axe", 10, 25, 1, "file:src/main/resources/items/128x128/axe2.png"),
  AXE_DOUBLE_01("Double-Headed Axe", 10, 25, 1, "file:src/main/resources/items/128x128/axe_double1.png"),
  AXE_DOUBLE_02("Super Double-Headed Axe", 10, 25, 1, "file:src/main/resources/items/128x128/axe_double2.png"),
  BOW_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/bow1.png"),
  BOW_02("Better Bow", 10, 25, 1, "file:src/main/resources/items/128x128/bow2.png"),
  DAGGER_01("Sneaky Dagger", 10, 25, 1, "file:src/main/resources/items/128x128/dagger1.png"),
  DAGGER_02("Fancy Dagger", 10, 25, 1, "file:src/main/resources/items/128x128/dagger2.png"),
  HAMMER_01("Hammer", 10, 25, 1, "file:src/main/resources/items/128x128/hammer1.png"),
  HAMMER_02("Smashing Hammer", 10, 25, 1, "file:src/main/resources/items/128x128/hammer2.png"),
  SWORD_01("Sword", 10, 25, 1, "file:src/main/resources/items/128x128/sword1.png"),
  SWORD_02("Pointy Sword", 10, 25, 1, "file:src/main/resources/items/128x128/sword2.png"),
  SWORD_WOOD("Wooden Sword", 10, 25, 1, "file:src/main/resources/items/128x128/sword_wood.png"),
  WAND_01("Wand", 10, 25, 1, "file:src/main/resources/items/128x128/wand1.png"),
  WAND_02("Knobbly Wand", 10, 25, 1, "file:src/main/resources/items/128x128/wand2.png");

  private final String itemName;
  private final int baseDamage;
  private final int criticalDamage;
  public final int breakChance;
  private final String filePath;


  /**
   * Constructor for enum.
   *
   * @param itemName
   * @param baseDamage
   * @param criticalDamage
   * @param breakChance
   * @param filePath
   */
  ItemsWeaponsEnum(String itemName, int baseDamage, int criticalDamage, int breakChance, String filePath) {
    this.itemName = itemName;
    this.filePath = filePath;
    this.baseDamage = baseDamage;
    this.breakChance = breakChance;
    this.criticalDamage = criticalDamage;
  }

  /**
   * Getter for item display name.
   *
   * @return displayName
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * getter for item image file path.
   * @return FILE_PATH
   */

  public String getFilePath() {
    return filePath;
  }

  /**
   * Getter for base damage of selected weapon.
   *
   * @return baseDamage
   */
  public int getBaseDamage() {
    return baseDamage;
  }

  /**
   * Getter for critical damage of selected weapon.
   *
   * @return criticalDamage
   */
  public int getCriticalDamage() {
    return criticalDamage;
  }

  /**
   * ToString override for returning displayName instead of enum name.
   *
   * @return itemName
   */
  @Override
  public String toString() {
    return itemName;
  }
}
