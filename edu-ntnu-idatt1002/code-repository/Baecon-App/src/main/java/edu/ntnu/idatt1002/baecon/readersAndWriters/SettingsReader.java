package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class responsible for reading {@link Settings} files.
 */
public class SettingsReader {

  /**
   * Reads a Settings object from the specified file.
   *
   * @param file a String representing the file name of the file to read the Settings object from
   * @return a Settings object read from the file, or null if an error occurs
   * @throws IOException if an I/O error occurs while creating the file
   * @throws ClassNotFoundException if the class of a serialized object cannot be found
   */
  public Settings read(File file) throws IOException, ClassNotFoundException {
    Settings settings;
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
    }
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      settings = (Settings) ois.readObject();
    }
    return settings;
  }
}
