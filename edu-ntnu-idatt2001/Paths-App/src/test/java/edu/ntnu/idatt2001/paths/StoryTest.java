package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.domain.Link;
import edu.ntnu.idatt2001.paths.domain.Passage;
import edu.ntnu.idatt2001.paths.domain.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {

    private Story story;
    private Passage passage1;
    private Passage passage2;
    private Passage passage3;
    private Link link1;
    private Link link2;

    @BeforeEach
    public void setUp() {
        passage1 = new Passage("Passage 1", "This is the first passage.");
        passage2 = new Passage("Passage 2", "This is the second passage.");
        passage3 = new Passage("Passage 3", "This is the third passage.");

        link1 = new Link("Go to Passage 2", "Passage 2");
        link2 = new Link("Go to Passage 3", "Passage 3");

        passage1.addLink(link1);
        passage2.addLink(link2);

        Map<Link, Passage> passages = new HashMap<>();
        passages.put(link1, passage1);
        passages.put(link2, passage2);
        passages.put(new Link("Go to Passage 1", "Passage 1"), passage3);

        story = new Story("Test Story", passages);
    }

    @Test
    public void removePassage_noLinksToRemove_passageRemoved() {
        Passage passage4 = new Passage("Passage 4", "This is the fourth passage.");
        Link linkToPassage4 = new Link("Go to Passage 4", "Passage 4");
        story.addPassage(passage4);

        story.removePassage(linkToPassage4);
        assertFalse(story.getPassages().containsValue(passage4));
    }


    @Test
    public void removePassage_linksPresent_throwsException() {
        assertThrows(IllegalStateException.class, () -> story.removePassage(link1));
    }

    @Test
    public void getBrokenLinks_noBrokenLinks_emptyList() {
        List<Link> brokenLinks = story.getBrokenLinks();
        assertTrue(brokenLinks.isEmpty());
    }

    @Test
    public void getBrokenLinks_brokenLinksExist_brokenLinksReturned() {
        Link brokenLink = new Link("Go to Non-existent Passage", "Non-existent Passage");
        passage3.addLink(brokenLink);
        story.addPassage(passage3);

        List<Link> brokenLinks = story.getBrokenLinks();

        assertFalse(brokenLinks.isEmpty());
        assertEquals(1, brokenLinks.size());
        assertTrue(brokenLinks.contains(brokenLink));
    }
}
