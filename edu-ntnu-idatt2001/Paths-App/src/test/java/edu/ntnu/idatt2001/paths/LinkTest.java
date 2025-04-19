package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.domain.Link;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    Link link = new Link("This is a Link Text", "This is a Link Reference");




    @Test
    void testGetText() {
        assertEquals("This is a Link Text", link.getText());
    }

    @Test
    void testGetReference() {
        assertEquals("This is a Link Reference", link.getReference());
    }

    @Test
    void testGetActions() {
        // TODO
    }

    @Test
    void testAddAction() {
        // TODO
    }

    @Test
    void testToString() {
        assertEquals("Link{text='This is a Link Text', " +
            "reference='This is a Link Reference', actions=null}", link.toString());
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void execute() {
    }
}
