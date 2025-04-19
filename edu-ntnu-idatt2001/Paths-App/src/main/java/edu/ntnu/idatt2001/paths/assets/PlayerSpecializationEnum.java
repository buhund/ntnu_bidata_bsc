/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.assets;

/**
 * Enum for loading player character models ("specialization", "profession", or "class"),
 * display name and starting item (name and image).
 *
 * NOTE: Avoiding using the term "class" in methods, classes
 * or enums due to it being easily confused with the Java term "class".
 * Thus, it is named "specialization" in the code.
 */
public enum PlayerSpecializationEnum {
    CHAR_WIZARD("Wizard", "src/main/resources/characterModels/wizard-ji-01.png", "Knobby Staff", "src/main/resources/64x64/wand2.png"),
    CHAR_ROGUE("Rogue", "src/main/resources/characterModels/rogue-ji-01.png", "Short-Bow", "src/main/resources/64x64/bow.png"),
    CHAR_BERSERK("Berserk", "src/main/resources/characterModels/berserk-ji-01.png", "War Axe", "src/main/resources/64x64/axeDouble2.png"),
    CHAR_HERO_LONK("Hero", "src/main/resources/characterModels/lonk-ji-01.png", "The Sword", "src/main/resources/64x64/sword2.png"),
    SPACE_MASTER("Space Master", "src/main/resources/characterModels/space_master-ji-01.png", "The Blaster", "src/main/resources/64x64/sword2.png");

    private final String displayName;
    private final String imagePath;
    private final String startingItem;
    private final String startingItemIcon;

    /**
     * Constructor for enum.
     *
     * @param displayName
     * @param imagePath
     * @param startingItem
     * @param startingItemIcon
     */
    PlayerSpecializationEnum(String displayName, String imagePath, String startingItem, String startingItemIcon) {
        this.displayName = displayName;
        this.imagePath = imagePath;
        this.startingItem = startingItem;
        this.startingItemIcon = startingItemIcon;
    }

    /**
     * Getter for enum display name, i.e. player specialization name.
     *
     * @return displayName for selected enum
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Getter for player specialization image path.
     *
     * @return imagePath for selected enum
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Getter for player specialization starting item.
     *
     * @return startingItem for selected enum
     */
    public String getStartingItem() {
        return startingItem;
    }

    /**
     * Getter for player specialization starting item icon.
     *
     * @return getStartingItemIcon
     */
    public String getStartingItemIcon() {
        return startingItemIcon;
    }

    /**
     * toString override for returning displayName instead of enum name.
     *
     * @return displayName
     */
    @Override
    public String toString() {
        return displayName;
    }
}
