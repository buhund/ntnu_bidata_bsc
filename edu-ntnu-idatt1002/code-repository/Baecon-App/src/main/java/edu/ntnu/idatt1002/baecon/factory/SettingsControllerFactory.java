package edu.ntnu.idatt1002.baecon.factory;

import edu.ntnu.idatt1002.baecon.controller.SettingsController;

/**
 * Class responsible for creating a single instance of the {@link SettingsController}.
 */
public class SettingsControllerFactory {
  private static SettingsController settingsController;

  /**
   * Returns a single instance of the {@link SettingsController}. If no instance exists,
   * a new instance is created.
   * Synchronized to ensure that only one thread at a time can access the controller instance.
   *
   * @return The single instance of the {@link SettingsController}.
   */
  public static synchronized SettingsController getSettingsController() {
    if (settingsController == null) {
      settingsController = new SettingsController();
    }

    return settingsController;
  }
}
