/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.actions;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Action represent a future change in the player's condition.
 * E.g. change in player score, health points, gold or inventory.
 */
public interface Action {


    /**
     * Execute method for actions on player.
     *
     * @param player
     */
    void execute(Player player);
}