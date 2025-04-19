package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.listeners.SettingsListener;
import edu.ntnu.idatt1002.baecon.readersAndWriters.SettingsReader;
import edu.ntnu.idatt1002.baecon.readersAndWriters.SettingsWriter;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import java.util.ArrayList;
import java.util.List;

/**
 * The SettingsController class is responsible for handling user settings and notifying
 * listeners when these settings are updated.
 */
public class SettingsController {
  private final BaeconFiles baeconFiles;
  private final Settings settings;
  private final List<SettingsListener> listeners;
  private final SettingsReader settingsReader;
  private final SettingsWriter settingsWriter;

  /**
   * Constructs a new SettingsController with the default file and initializes it with the
   * saved settings.
   * If there are no saved settings, handle the exception.
   */
  public SettingsController() {
    this.baeconFiles = new BaeconFiles();
    this.settingsReader = new SettingsReader();
    this.settingsWriter = new SettingsWriter();
    try {
      this.settings = settingsReader.read(baeconFiles.getFile("settings.ser"));
    } catch (Exception e) {
      throw new IllegalArgumentException("Error: " + e.getMessage());
    }
    this.listeners = new ArrayList<>();
  }

  /**
   * Constructs a new SettingsController with the default file and initializes it with the saved
   * settings.
   * If there are no saved settings, handle the exception.
   */
  public SettingsController(BaeconFiles baeconFiles) {
    this.baeconFiles = baeconFiles;
    this.settingsReader = new SettingsReader();
    this.settingsWriter = new SettingsWriter();
    try {
      this.settings = settingsReader.read(baeconFiles.getFile("settings.ser"));
    } catch (Exception e) {
      throw new IllegalArgumentException("Error: " + e.getMessage());
    }
    this.listeners = new ArrayList<>();
  }

  /**
   * Gets the currency currently set in the user's settings.
   *
   * @return the currency currently set in the user's settings
   */
  public String getCurrency() {
    return this.settings.getCurrency();
  }

  /**
   * Sets the currency of the user's settings and notifies listeners of the change.
   *
   * @param currency the new currency to set
   */
  public void setCurrency(String currency) {
    this.settings.setCurrency(currency);
    settingsWriter.write(settings, baeconFiles.getFile("settings.ser"));
    notifyListeners();
  }


  public boolean getColorBlindMode() {
    return this.settings.getColorBlindMode();
  }

  /**
   * Sets whether the colorblind mode of the user's settings is enabled and notifies listeners of
   * the change.
   *
   * @param colorBlindMode whether colorblind mode should be enabled
   */
  public void setColorBlindMode(boolean colorBlindMode) {
    this.settings.setColorBlindMode(colorBlindMode);
    settingsWriter.write(settings, baeconFiles.getFile("settings.ser"));
    notifyListeners();
  }

  /**
   * Returns whether the colorblind mode is currently enabled in the user's settings.
   *
   * @return true if colorblind mode is enabled, false otherwise
   */
  public boolean getNegativeNumbersInRed() {
    return this.settings.getNegativeNumbersInRed();
  }

  /**
   * Sets whether negative numbers in the user's settings should be displayed in red and notifies
   * listeners of the change.
   *
   * @param negativeNumbersInRed whether negative numbers should be displayed in red
   */
  public void setNegativeNumbersInRed(boolean negativeNumbersInRed) {
    this.settings.setNegativeNumbersInRed(negativeNumbersInRed);
    settingsWriter.write(settings, baeconFiles.getFile("settings.ser"));
    notifyListeners();
  }

  /**
   * Gets the current user settings.
   *
   * @return the current user settings
   */
  public Settings getSettings() {
    return settings;
  }

  /**
   * Adds a listener to be notified when the user settings are updated.
   *
   * @param listener the listener to add
   */
  public void addSubscriber(SettingsListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes a listener so that it will no longer be notified when the user settings are updated.
   *
   * @param listener the listener to remove
   */
  public void removeListener(SettingsListener listener) {
    listeners.remove(listener);
  }

  /**
   Notifies all registered listeners that the user settings have been updated.
   */
  private void notifyListeners() {
    for (SettingsListener listener : listeners) {
      listener.updateSettings(settings);
    }
  }
}