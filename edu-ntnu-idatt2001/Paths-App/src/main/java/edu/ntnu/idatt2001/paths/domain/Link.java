/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;
import edu.ntnu.idatt2001.paths.domain.actions.Action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Link links passages together.
 */
public class Link implements Action, Serializable {

  /**
   * Text indicating a choice or action.
   * For the player, Text is the visible part of the Link.
   */
  private final String text;

  /**
   * A string uniquely identifying a Passage (a part of the story).
   * Reference will also act as the title of the passage referred to.
   * By referencing a Passage indirectly through a String, we can
   * create the link before the Passage object is ready, making it easier to
   * implement file handling.
   */
  private final String reference;

  /**
   * A list of special objects, enabling manipulation of a player's abilities.
   * List of available actions is listed in the Action package.
   */
  private final List<Action> actions;

  /**
   * Constructor for Link
   *
   * @param text      the text
   * @param reference the reference
   */
  public Link(String text, String reference) {
    if (text == null || reference == null) {
      throw new IllegalArgumentException("Text and reference cannot be null");
    }
    this.text = text;
    this.reference = reference;
    actions = new ArrayList<>();
  }

  /**
   * Getter for text
   *
   * @return text text
   */
  public String getText() {
    return text;
  }

  /**
   * Getter for reference
   *
   * @return reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * Getter for actions
   *
   * @return actions actions
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Add action to ...
   *
   * @param actions the actions
   */
  public void addAction(List<Action> actions) {
    try {
      this.actions.addAll(actions);
    } catch (Exception e) {
      System.out.println("Could not add action: " + e.getMessage());
    }
  }


  // Overrides

  /**
   * toString method representing the Link.
   *
   * @return String text, reference, actions
   */
  @Override
  public String toString() {
    return "Link{" +
        "text='" + text + '\'' +
        ", reference='" + reference + '\'' +
        ", actions=" + actions +
        '}';
  }

  /**
   * HashCode override.
   *
   * @return super.hashCode
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Equals override.
   *
   * @param obj
   * @return super.equals(obj)
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  /**
   * Execute actions in link on player.
   *
   * @param player
   */
  @Override
  public void execute(Player player) {
    try {
      for (Action action : actions) {
        action.execute(player);
      }
    } catch (Exception e) {
      System.out.println("An error occurred while executing actions: " + e.getMessage());
    }
  }

}

