package edu.ntnu.idatt1002.baecon.listeners;

import edu.ntnu.idatt1002.baecon.data.Settings;

/**
 * A functional interface that defines the contract for a settings listener.
 * Any class that implements this interface can listen to changes in the application's settings.
 * When a change in the settings is made, this interface is used to notify all registered listeners.
 * The method {@code updateSettings} is called to inform the listener about the new settings.
 *
 * @see Settings
 */
public interface SettingsListener {

  /**
   * This method is called when a change in the application's settings occurs.
   * The new settings are passed as a parameter.
   *
   * @param newSettings the new settings of the application
   */
  void updateSettings(Settings newSettings);
}
