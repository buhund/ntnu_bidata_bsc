package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Settings;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Class responsible for writing {@link Settings} files.
 */
public class SettingsWriter {

  /**
   * Writes the specified Settings object to the specified file.
   *
   * @param settings the Settings object to write to the file
   * @param file a String representing the file name of the file to write the Settings object to
   */
  public void write(Settings settings, File file) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
      oos.writeObject(settings);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
