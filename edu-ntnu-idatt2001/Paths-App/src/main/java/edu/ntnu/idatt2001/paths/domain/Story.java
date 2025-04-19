/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Story holds a title and a map with Links and Passages.
 * When there are multiple Links for each passage, the story becomes non-linear.
 */
public class Story {

    /**
     * The Title of the Story.
     */
    private final String title;

    /**a
     * A map containing the passages of the story.
     * Each Passage have a Link as a key.
     */
    private final Map<Link, Passage> passages;

    /**
     * The first passage in the story.
     * Objects are to be added to passages.
     */
    private Passage openingPassage;

    /**
     * Story constructor.
     * Identifies opening passage by looking for "Beginnings" in file.
     *
     * @param title
     * @param passages
     */
    public Story(String title, Map<Link, Passage> passages) {
        this.title = title;
        this.passages = passages;
        //Look for the passage in the map named "Beginnings"
        this.openingPassage = passages.values().stream().filter(entry -> entry.getTitle().equals("Beginnings")).findFirst().get();
        // Since only the opening-passage will be the only one without a link pointing to it, we look for the one entry with key=null
        //this.openingPassage = passages.get(null);
        int breakpoint = 42;
    }

    /**
     * Getter for story title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for passages.
     *
     * @return passages
     */
    public Map<Link, Passage> getPassages() {
        return passages;
    }

    public Passage getPassage(Link link){
        return passages.get(link);
    }

    /**
     * Getter for opening passage.
     *
     * @return openingPassage
     */
    public Passage getOpeningPassage() {
        return openingPassage;
    }

    /**
     * Add passage to passages.
     *
     * @param passage
     * @return true/false
     */
    public boolean addPassage(Passage passage){
        if (passages.containsValue(passage)){
            return false;
        }
        passages.put(passage.getLinks().get(0), passage);
        return true;
    }

    /**
     * Removes a given passage from passages if no other passages link to it.
     * The link associated with the passage to be removed.
     *
     * @param link
     */
    public void removePassage(Link link) {
        // Check if there are any passages with links pointing to the passage associated with the provided link
        boolean isLinkedTo = passages.values().stream()
            .flatMap(passage -> passage.getLinks().stream())
            .anyMatch(linkInPassage -> linkInPassage.getReference().equals(link.getReference()));

        // If no links are found pointing to the passage, remove it from the passages map
        if (!isLinkedTo) {
            passages.remove(link);
        } else {
            throw new IllegalStateException("Cannot remove passage as it is linked to by other passages.");
        }
    }

    /**
     * Finds and returns a list of broken links.
     * A link is considered broken if it refers to a passage that doesn't exist in the passages map.
     *
     * @return brokenLinks List
     */
    public List<Link> getBrokenLinks() {
        List<Link> brokenLinks = new ArrayList<>();

        for (Passage passage : passages.values()) {
            for (Link link : passage.getLinks()) {
                String targetPassageTitle = link.getReference();
                boolean targetExists = passages.values().stream()
                    .anyMatch(p -> passage.getTitle().equals(targetPassageTitle));

                if (!targetExists) {
                    brokenLinks.add(link);
                }
            }
        }
        return brokenLinks;
    }

} // End of class Story
