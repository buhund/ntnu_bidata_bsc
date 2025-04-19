/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Goal represent a wanted result tied to the player's condition.
 * Goals enabled checking of the player achieved the desired result of an Action.
 */
public interface Goal {

    /**
     * Determine if a Goals has been fulfilled or not.
     *
     * @param player
     * @return isFulfilled true/false
     */
    boolean isFulfilled(Player player);
}