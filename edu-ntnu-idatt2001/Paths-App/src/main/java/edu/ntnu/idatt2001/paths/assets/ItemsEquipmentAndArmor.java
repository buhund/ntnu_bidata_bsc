/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for Items category: Equipment and Armor
 */
public enum ItemsEquipmentAndArmor {

  // Armor
  ARMOR_01_PLATE_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/armor1_plate1.png"),
  ARMOR_02_PLATE_02("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/armor2_plate2.png"),
  ARMOR_03_LEATHER_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/armor3_leather1.png"),
  ARMOR_04_LEATHER_02("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/armor4_leather2.png"),
  BACKPACK("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/backpack.png"),
  BOOTS("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/boots.png"),
  HELMET_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/helmet1.png"),
  HELMET_02("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/helmet2.png"),
  NECKLACE("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/necklace.png"),

  // Shields
  SHIELD_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield1.png"),
  SHIELD_02("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield2.png"),
  SHIELD_SEMI_WOOD("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield_semi_wood.png"),
  SHIELD_SMALL_01("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield_small1.png"),
  SHIELD_SMALL_02("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield_small2.png"),
  SHIELD_WOOD("Bow", 10, 25, 1, "file:src/main/resources/items/128x128/shield_wood.png");

  private final String itemName;
  private final String filePath;
  private final int baseDefence;
  private final int bonusMitigationChance;
  private final int magicDefence;


  /**
   * Constructor for enum.
   *
   * @param displayName
   * @param baseDefence
   * @param bonusMitigationChance
   * @param magicDefence
   * @param filePath
   */
  ItemsEquipmentAndArmor(String displayName, int baseDefence, int bonusMitigationChance, int magicDefence, String filePath) {
    this.itemName = displayName;
    this.baseDefence = baseDefence;
    this.bonusMitigationChance = bonusMitigationChance;
    this.magicDefence = magicDefence;
    this.filePath = filePath;
  }

  /**
   * Getter for item display name.
   *
   * @return
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * Getter for item image file path.
   *
   * @return filePath
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Getter for base defence stat.
   *
   * @return filePath
   */
  public int getBaseDefence() {
    return baseDefence;
  }

  /**
   * Getter for bonus damage mitigation defence stat.
   *
   * @return filePath
   */
  public int getBonusMitigationChance() {
    return bonusMitigationChance;
  }

  /**
   * Getter for magic defence stat.
   *
   * @return filePath
   */
  public int getMagicDefence() {
    return magicDefence;
  }

  /**
   * toString for item display name.
   * @return
   */
  @Override
  public String toString() {
    return itemName;
  }
}

