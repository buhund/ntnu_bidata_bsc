/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for parsing a story file and making it usable in-game.
 */
public class StoryParser {

  /**
   * Story Parser.
   * Read story from file. Identifies ::PassageTitle
   * Adds these to HashMap.
   * Story is required to be written on the exact format specified to work.
   *
   * Please see the inline comments for detailed walkthrough of functionality.
   *
   * @param filePath
   * @return Story, or null
   */
  public static Story parseStoryFromFile(String filePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      StringBuilder passageContent = new StringBuilder(1000);
      Map<Link, Passage> linkPassageMap = new HashMap<>();
      ArrayList<Passage> passages = new ArrayList<>();
      String line;
      String passageTitle = "";
      Passage currentPassage;


      // Read and build passages
      // Temporary list to hold links
      ArrayList<Link> linkList = new ArrayList<>();
      // Read the title
      String storyTitle = reader.readLine().trim();
      // Skip the next blank line, since it otherwise would bork the parsing
      reader.readLine();
      // Read and process line by line.
      // This way of reading requires a double line-break to symbolize end of file, as a single line ending symbolizes end of passage.
      // Strict adherence to the correct format is required.
      while ((line = reader.readLine()) != null) {
        // Trim excess whitespace
        line = line.trim();
        // If the line is blank, we are done processing the lines for this passage, and can create the passage object itself
        if(line.isBlank()) {
          // Create a new the passage, re-using the old variable for a new object
          currentPassage = new Passage(passageTitle, passageContent.toString());
          // Add the links to the passage.
          // (If we add a addLinks(ArrayList<Link> links){this.links.addAll(links);} function to the Passage-object,
          // we can add all the links in one go!)
          for (Link link : linkList)
            currentPassage.addLink(link);
          // Clear the temporary list of links and passage-text
          linkList.clear();
          passageContent.setLength(0);
          // Add the passage to the list of passages
          passages.add(currentPassage);
        }
        // If the line starts with ::, it is the start of a new passage, and we extract the title
        else if (line.startsWith("::")) {
          passageTitle = line.substring(2).trim();
        }
        // If the line starts with [, it is a link
        else if(line.startsWith("[")){
          // We create a link and it to the temporary list
          linkList.add(createLinkFromLine(line));
        }
        // If none of the other rules matches, it can only be passage contents!
        else {
          // So, we append the line to the passage-content
          passageContent.append(line);
        }
      }

      // Build sane passage-map: Link -> linked passage
      for (Passage passage: passages) {
        // Get links
        List<Link> links = passage.getLinks();
        // But skip this passage if it has no links
        if (links.isEmpty())
          continue;
        // And if we have links, we iterate over them...
        for (Link link : links) {
          // Find the passage the link references
          Optional<Passage> opt = passages.stream().filter(f -> f.getTitle().equals(link.getReference())).findFirst();
          // If it exists...
          if(opt.isPresent()) {
            // ...we get the passage...
            Passage referencedPassage = opt.get();
            // ...and add it to the map!
            linkPassageMap.put(link, referencedPassage);
          }
        }
      }
      // Find entrypoint, i.e. the only passage which is not linked to by any link
      for (Passage passage: passages) {
        if(!linkPassageMap.containsValue(passage))
          // And add it to the map
          linkPassageMap.put(null, passage);
      }

      return new Story(storyTitle, linkPassageMap);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Identifies [Links] and (Reference) in file.
   * Extracts the link text and reference text.
   *
   * @param line
   * @return linkText, refence
   */
  private static Link createLinkFromLine(String line) {
    // Find the indexes of where the different parts of the link is
    int linkStartIndex = line.indexOf('[');
    int linkEndIndex = line.indexOf(']');
    int referenceStartIndex = line.indexOf('(', linkEndIndex);
    int referenceEndIndex = line.indexOf(')', referenceStartIndex);

    // Extract the text and reference
    String linkText = line.substring(linkStartIndex + 1, linkEndIndex);
    String reference = line.substring(referenceStartIndex + 1, referenceEndIndex);

    // Return a new link
    return new Link(linkText, reference);
  }
}