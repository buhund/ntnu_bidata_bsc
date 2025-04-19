package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsControllerTest {
  private SettingsController settingsController;
  private final BaeconFiles baeconFiles = new BaeconFiles(FileSystems.getDefault().getPath("src",
    "test", "resources", "edu", "ntnu", "idatt1002", "baecon", "dataFiles").toString());

  @BeforeEach
  void setUp() {
    settingsController = new SettingsController(baeconFiles);
  }

  @Test
  void testCurrency() {
    String initialCurrency = settingsController.getCurrency();
    settingsController.setCurrency("USD");
    assertEquals("USD", settingsController.getCurrency());
    settingsController.setCurrency(initialCurrency);
  }

  @Test
  void testColorBlindMode() {
    boolean initialMode = settingsController.getColorBlindMode();
    settingsController.setColorBlindMode(!initialMode);
    assertEquals(!initialMode, settingsController.getColorBlindMode());
    settingsController.setColorBlindMode(initialMode);
  }

  @Test
  void testNegativeNumbersInRed() {
    boolean initialSetting = settingsController.getNegativeNumbersInRed();
    settingsController.setNegativeNumbersInRed(!initialSetting);
    assertEquals(!initialSetting, settingsController.getNegativeNumbersInRed());
    settingsController.setNegativeNumbersInRed(initialSetting);
  }

  @Test
  void testListener() {
    TestSettingsListener testListener = new TestSettingsListener();
    settingsController.addSubscriber(testListener);

    settingsController.setCurrency("EUR");
    assertTrue(testListener.isUpdated());

    settingsController.removeListener(testListener);
    testListener.setUpdated(false);
    settingsController.setCurrency("USD");
    assertFalse(testListener.isUpdated());
  }

  static class TestSettingsListener implements SettingsListener {
    private boolean updated;

    public TestSettingsListener() {
      this.updated = false;
    }

    public boolean isUpdated() {
      return updated;
    }

    public void setUpdated(boolean updated) {
      this.updated = updated;
    }

    @Override
    public void updateSettings(Settings settings) {
      updated = true;
    }
  }
}