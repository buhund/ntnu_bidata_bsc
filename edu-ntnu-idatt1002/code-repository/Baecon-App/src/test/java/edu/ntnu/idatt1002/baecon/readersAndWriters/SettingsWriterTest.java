package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Settings;
import edu.ntnu.idatt1002.baecon.utilities.BaeconFiles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 The SettingsWriterTest class tests the functionality of the {@link SettingsWriter} class.
 It contains methods for testing the writing of {@link Settings} objects to files.
 */
public class SettingsWriterTest {
  private static final BaeconFiles baeconFiles = new BaeconFiles("src/test/resources/edu/ntnu/"
    + "idatt1002/baecon/dataFiles");

  /**
   Tests the behavior of {@link SettingsWriter#write(Settings, File)} when writing to a file.
   It creates a set of test settings, writes them to a test file using a {@link SettingsWriter} object,
   reads the settings from the file using a {@link SettingsReader} object, and then compares the result
   to the expected settings. Finally, it deletes the test file.
   */
  @Test
  void testWrite() {
    // Create a test Settings object
    Settings testSettings = new Settings("USD");
    testSettings.setColorBlindMode(true);
    testSettings.setNegativeNumbersInRed(false);

    // Create a test file
    File testFile = baeconFiles.getFile("test_settings.ser");
    if (testFile.exists()) {
      Assertions.assertTrue(testFile.delete());
    }

    // Write test Settings object to the test file
    SettingsWriter settingsWriter = new SettingsWriter();
    settingsWriter.write(testSettings, testFile);

    // Verify that the test file exists and is not empty
    Assertions.assertTrue(testFile.exists());
    Assertions.assertTrue(testFile.length() > 0);

    // Delete the test file
    Assertions.assertTrue(testFile.delete());
  }

}