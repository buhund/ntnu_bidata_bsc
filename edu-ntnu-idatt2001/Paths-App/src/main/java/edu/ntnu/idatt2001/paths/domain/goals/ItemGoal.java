/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Game Goal: Item.
 * Defines an item needed to "win" or achieve certain goals
 * or milestones in-game.
 */
public class ItemGoal implements Goal {

  /**
   * Minimum requirements for fulfilling the Gold Goal.
   */
  private final String theRequiredItem;

  /**
   * Constructor for Gold Goal.
   *
   * @param theRequiredItem
   */
  public ItemGoal(String theRequiredItem) {
    this.theRequiredItem = theRequiredItem;
  }

  /**
   * Determine if the Goal has been fulfilled or not.
   *
   * @param player
   * @return isFulfilled true/false
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getInventory().contains(theRequiredItem);
  }

  /**
   * toString for required item.
   *
   * @return String theRequiredItem
   */
  @Override
  public String toString() {
    return "Item Goal: " + theRequiredItem;
  }

}
