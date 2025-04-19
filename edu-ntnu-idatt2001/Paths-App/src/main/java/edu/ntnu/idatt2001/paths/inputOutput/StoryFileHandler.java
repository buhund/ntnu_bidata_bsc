/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.inputOutput;

import edu.ntnu.idatt2001.paths.domain.Link;
import edu.ntnu.idatt2001.paths.domain.Passage;
import edu.ntnu.idatt2001.paths.domain.Story;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to process story file.
 */
public class StoryFileHandler {

  /**
   * File extension.
   */
  private static final String FILE_EXTENSION = ".paths";

  /**
   * RegEx Pattern for identifying a ::Passage title.
   */
  private static final Pattern PASSAGE_PATTERN = Pattern.compile("^::(.*)$");

  /**
   * RegEx Pattern for identifying a [Link text] and (Reference)
   */
  private static final Pattern LINK_PATTERN = Pattern.compile("^\\[(.*)\\]\\((.*)\\)$");


  /**
   * Save the current story progress to fil.
   *
   * @param story
   * @param filePath
   * @throws IOException
   */
  public static void saveStory(Story story, String filePath) throws IOException {
//    File file = new File(filePath + FILE_EXTENSION);
    File file = new File(filePath); // File path contains the full path including filename.extension
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(story.getTitle());
      writer.newLine();
      writer.newLine();

      for (Passage passage : story.getPassages().values()) {
        writer.write("::" + passage.getTitle());
        writer.newLine();
        writer.write(passage.getContent());
        writer.newLine();

        for (Link link : passage.getLinks()) {
          writer.write("[" + link.getText() + "](" + link.getReference() + ")");
          writer.newLine();
        }
        writer.newLine();
      }
    }
  }

  /**
   * Load story progress from file.
   *
   * @param filePath
   * @return
   * @throws IOException
   */
  public static Story loadStory(String filePath) throws IOException {
    File file = new File(filePath);

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String title = reader.readLine();
      reader.readLine();

      Map<Link, Passage> passages = new HashMap<>();
      Passage currentPassage = null;

      String line;
      while ((line = reader.readLine()) != null) {
        Matcher passageMatcher = PASSAGE_PATTERN.matcher(line);
        Matcher linkMatcher = LINK_PATTERN.matcher(line);

        if (passageMatcher.matches()) {
          if (currentPassage != null) {
//            TODO Can't have Passage (class), must use passage (instance)
//            passages.put(new Link("Go to " + Passage.getTitle(), Passage.getTitle()), currentPassage);
          }

          String passageTitle = passageMatcher.group(1);
          String content = reader.readLine();
          currentPassage = new Passage(passageTitle, content);
        } else if (linkMatcher.matches()) {
          String linkText = linkMatcher.group(1);
          String targetPassageTitle = linkMatcher.group(2);
          Link link = new Link(linkText, targetPassageTitle);
          currentPassage.addLink(link);
        }
      }

      if (currentPassage != null) {
//            TODO Cant have Passage (class), must use passage (instance)
//        passages.put(new Link("Go to " + Passage.getTitle(), Passage.getTitle()), currentPassage);
      }

      return new Story(title, passages);
    }
  }
}
