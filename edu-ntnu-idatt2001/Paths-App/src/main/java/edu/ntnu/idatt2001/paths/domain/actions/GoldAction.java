/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.actions;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Action for Gold.
 * Updates Gold for player.
 */
public class GoldAction implements Action {

    /**
     * Gold, as in a player's money.
     * Amount to add/subtract.
     */
    private final int gold;

    /**
     * Hash Code.
     */
    private final int hashCode;

    /**
     * Constructor for gold action.
     *
     * @param gold
     */
    public GoldAction(int gold) {
        this.gold = gold;
        this.hashCode = this.getClass().hashCode() * gold < 0 ? gold : gold + 1;
    }

    /**
     * Execute gold action on player.
     *
     * @param player
     */
    @Override
    public void execute(Player player) {
        player.addGold(gold);
    }

    public int hashCode() {
        return hashCode;
    }
}
