package edu.ntnu.idatt1002.baecon.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {
  @Test
  void testToggleColorBlindMode() {
    Settings settings = new Settings("EUR");
    assertFalse(settings.getColorBlindMode());
    settings.toggleColorBlindMode();
    assertTrue(settings.getColorBlindMode());
    settings.toggleColorBlindMode();
    assertFalse(settings.getColorBlindMode());
  }

  @Test
  void testToggleNegativeNumbersInRed() {
    Settings settings = new Settings("EUR");
    assertFalse(settings.getNegativeNumbersInRed());
    settings.toggleNegativeNumbersInRed();
    assertTrue(settings.getNegativeNumbersInRed());
    settings.toggleNegativeNumbersInRed();
    assertFalse(settings.getNegativeNumbersInRed());
  }

  @Test
  void testEqualsAndHashCode() {
    Settings settings1 = new Settings(false, true, "USD");
    Settings settings2 = new Settings(false, true, "USD");
    Settings settings3 = new Settings(false, true, "EUR");
    assertEquals(settings1, settings2);
    assertEquals(settings1.hashCode(), settings2.hashCode());
    assertNotEquals(settings1, settings3);
    assertNotEquals(settings1.hashCode(), settings3.hashCode());
  }

  @Test
  void testGettersAndSetters() {
    Settings settings = new Settings("EUR");
    assertFalse(settings.getColorBlindMode());
    assertFalse(settings.getNegativeNumbersInRed());
    assertEquals("EUR", settings.getCurrency());

    settings.setColorBlindMode(true);
    settings.setNegativeNumbersInRed(true);
    settings.setCurrency("USD");

    assertTrue(settings.getColorBlindMode());
    assertTrue(settings.getNegativeNumbersInRed());
    assertEquals("USD", settings.getCurrency());
  }
}