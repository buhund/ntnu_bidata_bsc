/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

import java.util.List;

/**
 * Game Goal: Gold.
 * Defines an amount needed to "win" or achieve certain goals
 * or milestones in-game.
 */
public class InventoryGoal implements Goal {

    /**
     * Minimum requirements for fulfilling the Goal.
     * List of items required.
     */
    private final List<String> requiredItemsList;

    /**
     * Constructor for Inventory Goal.
     *
     * @param requiredItemsList
     */
    public InventoryGoal(List<String> requiredItemsList) {
        this.requiredItemsList = requiredItemsList;
    }

    /**
     * Determine if the Goal has been fulfilled or not.
     *
     * @param player
     * @return isFulfilled true/false
     */
    @Override
    public boolean isFulfilled(Player player) {
        if (player.getInventory().size() >= requiredItemsList.size()) {
            for (String item : requiredItemsList) {
                if (!player.getInventory().contains(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * List of items required to fulfill the goal.
     *
     * @return List requiredItemsList
     */
    public List<String> getItems() {
        return requiredItemsList;
    }

    /**
     * toString for Inventory goal.
     *
     * @return String requiredItemsList
     */
    @Override
    public String toString() {
        return "Inventory Goal: " + String.join(", ", requiredItemsList);
    }

}
