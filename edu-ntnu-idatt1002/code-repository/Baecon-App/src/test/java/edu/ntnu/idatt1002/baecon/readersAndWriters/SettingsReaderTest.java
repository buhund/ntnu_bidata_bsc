package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * This class test the behaviour of the SettingsReader class.
 */
public class SettingsReaderTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");

  /**
   Tests the behavior of {@link SettingsReader#read(File)} when reading an existing file.
   It creates a test file, writes a set of test settings to it, reads the settings from the file
   using a SettingsReader object, compares the result to the expected settings, and then deletes
   the test file.
   */
  @Test
  void testReadExistingFile() {
    SettingsReader reader = new SettingsReader();
    File file = baeconFiles.getFile("test_settings.ser");

    // Create test settings
    Settings expectedSettings = new Settings("EUR");
    expectedSettings.setColorBlindMode(true);
    expectedSettings.setNegativeNumbersInRed(true);

    // Write test settings to file
    SettingsWriter writer = new SettingsWriter();
    writer.write(expectedSettings, file);

    // Read settings from file and compare with expected settings
    try {
      Settings actualSettings = reader.read(file);
      assertEquals(expectedSettings, actualSettings);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      fail();
    }

    // Delete test file
    assertTrue(file.delete());
  }

  /**
   Tests the behavior of the {@link SettingsReader#read(File)} method when attempting to read settings from
   a non-existing file.
   It creates a new {@link SettingsReader} and a {@link File} object with a non-existing file path. If the file exists,
   it will be deleted.
   Then it attempts to read settings from the non-existing file using the reader, and verifies that the returned
   value is null.
   */
  @Test
  void testReadNonExistingFile() {
    SettingsReader reader = new SettingsReader();
    File file = baeconFiles.getFile("non_existing_file.ser");

    try {
      // Try to read settings from non-existing file
      reader.read(file);
    } catch (IllegalArgumentException e) {
      // Verify that the correct exception is thrown
      assertEquals("File does not exist: " + file.getAbsolutePath(), e.getMessage());
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      fail();
    }
  }
}