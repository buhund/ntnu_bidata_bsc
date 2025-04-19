package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2001.paths.domain.Link;
import edu.ntnu.idatt2001.paths.domain.Passage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassageTest {


    @Test
    @DisplayName("Test for getting passage Content")
    void testGetContent() {
        Passage passage = new Passage("Passage title", "Passage CONTENT");

        assertEquals("Passage CONTENT", passage.getContent());
        assertFalse(passage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Test for getting passage Title")
        // TODO Make @DisplayName for all tests.
    void testGetTitle() {
        Passage passage = new Passage("Passage TITLE", "Passage content");

        assertEquals("Passage TITLE", passage.getTitle());
        assertFalse(passage.getTitle().isEmpty());
    }

    @Test
    @DisplayName("Testing getting links")
    void testGetLinks() {
        // Arrange
        Link link1 = new Link("Link text 1", "Link reference 1");
        Link link2 = new Link("Link text 2", "Link reference 2");
        Link link3 = new Link("Link text 3", "Link reference 3");

        Link linkNotAdd1 = new Link("Link text 4", "Link reference 4");
        Link linkNotAdd2= new Link("Link text 5", "Link reference 5");

        Passage passage = new Passage("Passage title", "Passage content");

        passage.addLink(link1);
        passage.addLink(link2);
        passage.addLink(link3);

        // Act
        List<Link> linksTestList = passage.getLinks();

        // Assert
        assertNotNull(linksTestList);
        assertTrue(linksTestList.contains(link1));
        assertTrue(linksTestList.contains(link2));
        assertTrue(linksTestList.contains(link3));
        assertFalse(linksTestList.contains(linkNotAdd1));
        assertFalse(linksTestList.contains(linkNotAdd2));
    }


    @Test
    @DisplayName("Testing adding links to Passage")
    void testAddLink() {
        // Arrange
        Link link1 = new Link("Link text 1", "Link reference 1");
        Link link2 = new Link("Link text 2", "Link reference 2");
        Link link3 = new Link("Link text 3", "Link reference 3");

        Link linkNotAdd1 = new Link("Link text 4", "Link reference 4");
        Link linkNotAdd2= new Link("Link text 5", "Link reference 5");

        Passage passage = new Passage("Passage title", "Passage content");

        passage.addLink(link1);
        passage.addLink(link2);
        passage.addLink(link3);

        // Act
        List<Link> linksTestList = passage.getLinks();

        // Assert
        assertNotNull(linksTestList);
        assertTrue(linksTestList.contains(link1));
        assertTrue(linksTestList.contains(link2));
        assertTrue(linksTestList.contains(link3));
        assertFalse(linksTestList.contains(linkNotAdd1));
        assertFalse(linksTestList.contains(linkNotAdd2));
    }


    @Test
    @DisplayName("Test hasLinks on Passage, true or false")
    void testHasLinks() {
        // Arrange
        Link link1 = new Link("Link text 1", "Link reference 1");
        Link link2 = new Link("Link text 2", "Link reference 2");
        Link link3 = new Link("Link text 3", "Link reference 3");

        Link linkNotAdd1 = new Link("Link text 4", "Link reference 4");
        Link linkNotAdd2= new Link("Link text 5", "Link reference 5");

        Passage passageWithLink = new Passage("Passage title", "Passage content");

        passageWithLink.addLink(link1);
        passageWithLink.addLink(link2);
        passageWithLink.addLink(link3);

        Passage emptyPassage = new Passage("Empty Title", "Empty Content");

        // Act
        boolean listOfLinks = passageWithLink.hasLinks();
        boolean listOfEmpty = emptyPassage.hasLinks();

        // Assert
        assertFalse(listOfEmpty);
        assertTrue(listOfLinks);


    }

    @Test
    @DisplayName("testToString")
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}