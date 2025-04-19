/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.actions;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Action for Health.
 * Updates Health for player.
 */
public class HealthAction implements Action {

    /**
     * Health points to add/subtract.
     */
    private final int health;

    /**
     * Hash Code.
     */
    private final int hashCode;

    /**
     * Constructor for health action on player.
     *
     * @param health
     */
    public HealthAction(int health) {
        this.health = health;
        this.hashCode = this.getClass().hashCode() * health < 0 ? health : health + 1;
    }

    /**
     * Execute health action on player.
     *
     * @param player
     */
    @Override
    public void execute(Player player) {
        player.addHealth(health);
    }

    /**
     * Hash Code method for HealthAction.
     *
     * @return
     */
    public int hashCode() {
        return hashCode;
    }
}

