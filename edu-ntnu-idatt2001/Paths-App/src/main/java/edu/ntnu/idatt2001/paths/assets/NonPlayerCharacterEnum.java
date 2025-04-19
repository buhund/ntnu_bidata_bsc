/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for dynamic NPC inserts.
 * NPC = "Non-Player Character"
 */
public enum NonPlayerCharacterEnum {
  BOSS_VAPORDORF_01("Vapordorf", "/boss/vapordorf-01.png", "Inhaler"),
  BOSS_VAPORDORF_01_NOBG("Vapordorf", "/src/main/resources/boss/vapordorf-01-nobg.png", "Inhaler"),
  CHAR_SKELLINGTHOR("Skelling-Thor", "/src/main/resources/boss/skelling-thor-01", "Bones"),
  CHAR_GANONDALF("Gaondalf", "/src/main/resources/boss/ganondalf-01.png", "Moth");

  private final String displayName;
  private final String filePath;
  private final String itemLoot;

  /**
   * Constructor for enum.
   *
   * @param displayName
   * @param filePath
   * @param itemLoot
   */
  NonPlayerCharacterEnum(String displayName, String filePath, String itemLoot) {
    this.displayName = displayName;
    this.filePath = filePath;
    this.itemLoot = itemLoot;
  }

  /**
   * Getter for enum display name, i.e. NPC name.
   *
   * @return displayName for selected enum
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Getter for NPC image path.
   *
   * @return imagePath for selected enum
   */

  public String getFilePath() {
    return filePath;
  }

  /**
   * Getter for NPC item loot drop.
   *
   * @return startingItem for selected enum
   */
  public String getItemLoot() {
    return itemLoot;
  }

  /**
   * ToString override for returning displayName instead of enum name.
   *
   * @return displayName
   */
  @Override
  public String toString() {
    return displayName;
  }

}
