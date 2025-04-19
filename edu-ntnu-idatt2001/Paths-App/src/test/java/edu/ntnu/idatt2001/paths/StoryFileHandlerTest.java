package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.domain.Link;
import edu.ntnu.idatt2001.paths.domain.Passage;
import edu.ntnu.idatt2001.paths.domain.Story;
import edu.ntnu.idatt2001.paths.inputOutput.StoryFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoryFileHandlerTest {

  private Story story;
  private Passage passage1;
  private Passage passage2;
  private Passage passage3;
  private Link link1;
  private Link link2;
  private Link link3;

  @BeforeEach
  public void setUp() {
    passage1 = new Passage("Passage 1", "This is the first passage.");
    passage2 = new Passage("Passage 2", "This is the second passage.");
    passage3 = new Passage("Passage 3", "This is the third passage.");

    link1 = new Link("Go to Passage 1", "Passage 1");
    link2 = new Link("Go to Passage 2", "Passage 2");
    link3 = new Link("Go to Passage 3", "Passage 3");

    passage1.addLink(link1);
    passage2.addLink(link2);
    passage3.addLink(link3);

    Map<Link, Passage> passages = new HashMap<>();
    passages.put(link1, passage1);
    passages.put(link2, passage2);
    passages.put(link3, passage3);

    story = new Story("Test Story", passages);
  }

  @Test
  public void saveAndLoadStory(@TempDir Path tempDir) throws IOException, InterruptedException {
    String fileName = "testStory";

    File tempFile = tempDir.resolve(fileName).toFile();

    StoryFileHandler.saveStory(story, tempFile.getAbsolutePath());

    assertTrue(tempFile.exists());

    Story loadedStory = StoryFileHandler.loadStory(tempFile.getAbsolutePath());

    assertEquals(story, loadedStory);
  }
}
