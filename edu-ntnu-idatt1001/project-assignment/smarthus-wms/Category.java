/**
 * @author Kandidatnummer: XYZ
 * @project Mappevurdering, IDATT1001, BIDATA
 * @version v3.4
 */

/**
 * Enum class, Category.
 */
public enum Category {
  Flooring, Window, Door, Lumber, Doorknobs;

  /**
   * Check for category.
   *
   * @param category to check
   * @return true
   */
  public static boolean hasCategory(String category) {
    for (Category cat : Category.values()) {
      if (cat.name().equals(category)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set catyegory based on int value.
   *
   * @param value of int input.
   * @return category corresponding to int input.
   */
  public static Category fromInt(int value) {
    if (value < 0 || value > Category.values().length) {
      throw new IllegalArgumentException("Value out of range");
    }
    return Category.values()[value];
  }

  /**
   * Category from string input.
   *
   * @param category string input.
   * @return category, if matching and present.
   */
  public static Category fromString(String category) {
    if (category.isBlank()) {
      throw new IllegalArgumentException("Argument cannot be null or empty");
    }
    if (hasCategory(category)) {
      return Category.valueOf(category);
    } else {
      int enumId = Integer.parseInt(category);
      return fromInt(enumId);
    }
  }

  /**
   * String-builder to append category.
   *
   * @return string with category.
   */
  public static String toLongString() {
    StringBuilder builder = new StringBuilder();
    for (Category category : Category.values()) {
      builder.append(category.ordinal()).append(": ").append(category).append("\n");
    }
    return builder.toString();
  }


}
