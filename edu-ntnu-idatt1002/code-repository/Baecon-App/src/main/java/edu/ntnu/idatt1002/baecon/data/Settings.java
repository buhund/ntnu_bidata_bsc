
package edu.ntnu.idatt1002.baecon.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Settings class represents the settings for the Beacon finance program.
 */
public class Settings implements Serializable {

  private boolean colorBlindMode;
  private boolean negativeNumbersInRed;
  private String currency;

  /**
   * Creates a new Settings object with the specified values for colorBlindMode,
   * negativeNumbersInRed, and currency.
   *
   * @param colorBlindMode a boolean value indicating whether color-blind mode is enabled
   * @param negativeNumbersInRed a boolean value indicating whether negative numbers should
   *                             be displayed in red
   * @param currency a String representing the currency code to use for displaying financial data
   */
  public Settings(boolean colorBlindMode, boolean negativeNumbersInRed, String currency) {
    this.colorBlindMode = colorBlindMode;
    this.negativeNumbersInRed = negativeNumbersInRed;
    this.currency = currency;
  }

  /**
   * Creates a new Settings object with default values for colorBlindMode and negativeNumbersInRed,
   * and the specified currency code.
   *
   * @param currency a String representing the currency code to use for displaying financial data
   */
  public Settings(String currency) {
    this.colorBlindMode = false;
    this.negativeNumbersInRed = false;
    this.currency = currency;
  }

  /**
   * Toggles the colorBlindMode setting.
   */
  public void toggleColorBlindMode() {
    colorBlindMode = !colorBlindMode;
  }

  /**
   * Toggles the negativeNumbersInRed setting.
   */
  public void toggleNegativeNumbersInRed() {
    negativeNumbersInRed = !negativeNumbersInRed;
  }

  /**
   * Returns the value of the colorBlindMode setting.
   *
   * @return a boolean value indicating whether color-blind mode is enabled
   */
  public boolean getColorBlindMode() {
    return colorBlindMode;
  }

  /**
   * Sets the value of the colorBlindMode setting.
   *
   * @param colorBlindMode a boolean value indicating whether color-blind mode is enabled
   */
  public void setColorBlindMode(boolean colorBlindMode) {
    this.colorBlindMode = colorBlindMode;
  }

  /**
   * Returns the value of the negativeNumbersInRed setting.
   *
   * @return a boolean value indicating whether negative numbers should be displayed in red
   */
  public boolean getNegativeNumbersInRed() {
    return negativeNumbersInRed;
  }

  /**
   * Sets the value of the negativeNumbersInRed setting.
   *
   * @param negativeNumbersInRed a boolean value indicating whether negative numbers should
   *                             be displayed in red
   */
  public void setNegativeNumbersInRed(boolean negativeNumbersInRed) {
    this.negativeNumbersInRed = negativeNumbersInRed;
  }

  /**
   * Returns the currency code used for displaying financial data.
   *
   * @return a String representing the currency code
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Sets the currency code used for displaying financial data.
   *
   * @param currency a String representing the currency code
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Settings settings = (Settings) o;
    return colorBlindMode == settings.colorBlindMode && negativeNumbersInRed == settings
      .negativeNumbersInRed
      && Objects.equals(currency, settings.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(colorBlindMode, negativeNumbersInRed, currency);
  }
}
