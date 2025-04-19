package edu.ntnu.idatt1002.baecon.components;

import javafx.scene.control.TextField;

/**
 * Custom TextField component for Numbers.
 */
public class NumberAndPunctuationTextField extends TextField {

  /**
   * Overrides the replaceText method to restrict the input to only numbers
   * and commas with up to two digits after the comma.
   *
   * @param start The starting index in the text to replace
   * @param end The ending index in the text to replace
   * @param text The new text to replace the specified range
   */
  @Override
  public void replaceText(int start, int end, String text) {
    if (validate(text)) {
      if (text.matches("[,.]") && getText().contains(".")) {
        return;
      }
      // Restrict the number of decimal places to two
      String newText = getText().substring(0, start) + text + getText().substring(end);
      int decimalIndex = newText.indexOf('.');
      // "123,900"
      if (decimalIndex != -1 && newText.length() - decimalIndex > 3) {
        return;
      }
      super.replaceText(start, end, text.replace(",", "."));
    }
  }

  /**
   * Overrides the replaceSelection method to restrict the input to only numbers
   * and commas with up to two digits after the comma.
   *
   * @param text The new text to replace the current selection
   */
  @Override
  public void replaceSelection(String text) {
    if (validate(text)) {
      System.out.println(text);
      // Restrict the number of decimal places to two
      String newText = getText() + text;
      int decimalIndex = newText.indexOf(',');
      if (decimalIndex != -1 && newText.length() - decimalIndex > 3) {
        return;
      }
      super.replaceSelection(text.replace(",", "."));
    }
  }

  /**
   * Validates the input text based on a regular expression. Allows only numbers
   * and commas with up to two digits after the comma.
   *
   * @param text The text to be validated
   * @return true if the text is valid, false otherwise
   */
  private boolean validate(String text) {
    return text.isEmpty() || text.matches("[0-9,.]*");
  }
}
