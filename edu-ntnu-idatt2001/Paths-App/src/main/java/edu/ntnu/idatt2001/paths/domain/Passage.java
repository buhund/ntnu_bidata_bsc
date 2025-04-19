/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Passage class hold the title, content (story) and links (to other passages).
 */
public class Passage {

    /**
     * High-level description of the Passage.
     * Identifies the Passage.
     */
    private final String title;

    /**
     * "In-game" text, representing a section, paragraph or part of a dialogue.
     */
    private final String content;

    /**
     * A list of links that connects "this" passage to other passages.
     * A passage with two or more links makes for a non-linear story.
     */
    private final List<Link> links;

    /**
     * Constructor for Passage.
     *
     * @param title
     * @param content
     */
    public Passage(String title,String content) {
        this.title = title;
        this.content = content;
        this.links = new ArrayList<Link>();
    }

    /**
     * Getter for content
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Getter for title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for links
     *
     * @return links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Adds a Link to the list of links.
     *
     * @param link Link to be added.
     * @return true if the link is successfully added, otherwise false.
     * @throws IllegalArgumentException if the link is null.
     */
    public boolean addLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link cannot be null.");
        }
        return links.add(link);
    }


    /**
     * Check if Links are present.
     *
     * @return boolean
     */
    public boolean hasLinks(){
        return !links.isEmpty();
    }

    /**
     * toString method.
     *
     * @return String title, content, links
     */
    @Override
    public String toString() {
        return "Passage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", links=" + links +
                '}';
    }

    /**
     * Equals override.
     *
     * @param o
     * @return object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passage passage = (Passage) o;
        return Objects.equals(title, title) && Objects.equals(content, passage.content)
                && Objects.equals(links, passage.links);
    }

    /**
     * hashCode override.
     *
     * @return Objects.hash(title, content, links)
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, content, links);
    }
}
