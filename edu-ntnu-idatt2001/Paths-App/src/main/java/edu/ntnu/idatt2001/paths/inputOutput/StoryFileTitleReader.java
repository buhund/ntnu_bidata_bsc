/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.inputOutput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for parsing and setting file title and description in the NewGameView GUI.
 */
public class StoryFileTitleReader {

  /**
   * File to process.
   */
  private final File file;

  /**
   * Title to be extracted.
   */
  private final StringProperty title;

  /**
   * Description to be extracted.
   */
  private final StringProperty description;

  /**
   * Identify Title and Description by RegEx pattern.
   * Output these so that Title can replace the displayed file name in NewGameView story selector combobox,
   * and Description can be passed to TextArea in NewGameView.
   *
   * @param file
   */
  public StoryFileTitleReader(File file) {
    this.file = file;
    this.title = new SimpleStringProperty();
    this.description = new SimpleStringProperty();

    Pattern patternTitle = Pattern.compile("title=\"([^\"]*)\"");
    Pattern patternDescription = Pattern.compile("description=\"([^\"]*)\"");

    try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
      String line;
      boolean foundTitle = false;
      boolean foundDescription = false;
      while ((line = reader.readLine()) != null) {
        Matcher titleMatcher = patternTitle.matcher(line);
        if (titleMatcher.find()) {
          title.set(titleMatcher.group(1));
          foundTitle = true;
        }
        Matcher descriptionMatcher = patternDescription.matcher(line);
        if (descriptionMatcher.find()) {
          description.set(descriptionMatcher.group(1));
          foundDescription = true;
        }
      }
      if (!foundTitle) {
        title.set(file.getAbsoluteFile().getName());
      }
      if (!foundDescription) {
        description.set("No description available");
      }
    } catch(IOException e) {
        title.set("Error reading file");
        description.set("Error reading file");
    }
  }

  /**
   * Stores results from StoryFileTitleReader to a list and returns it.
   *
   * @param directoryPath
   * @return List storyFileTitleReaders
   */
  public static List<StoryFileTitleReader> getFilesFromDirectory(String directoryPath) {
    File directory = new File(directoryPath);
    File[] files = directory.listFiles();
    List<StoryFileTitleReader> storyFileTitleReaders = new ArrayList<>();
    if (files != null) {
      for (File file : files) {
        storyFileTitleReaders.add(new StoryFileTitleReader(file));
      }
    }
    return storyFileTitleReaders;
  }

  /**
   * Getter for file.
   *
   * @return file
   */
  public File getFile() {
    return file;
  }

  /**
   * Getter for title.
   *
   * @return title.get()
   */
  public String getTitle() {
    return title.get();
  }

  /**
   * StringProperty method for title.
   *
   * @return title
   */
  public StringProperty titleProperty() {
    return title;
  }

  /**
   * Getter for description.
   *
   * @return description.get()
   */
  public String getDescription() {
    return description.get();
  }

  /**
   * StringProperty method for description.
   *
   * @return description
   */
  public StringProperty descriptionProperty() {
    return description;
  }


} // End of Class
