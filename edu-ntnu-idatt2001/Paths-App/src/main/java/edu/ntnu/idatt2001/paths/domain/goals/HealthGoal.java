/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Game Goal: Health.
 * Defines an amount needed to "win" or achieve certain goals
 * or milestones in-game.
 */
public class HealthGoal implements Goal {

    /**
     * Minimum requirements for fulfilling the Goal.
     * Minimum required Health.
     */
    private final int minimumHealth;

    /**
     * Constructor for Health Goal.
     *
     * @param minimumHealth
     */
    public HealthGoal(int minimumHealth) {
        this.minimumHealth = minimumHealth;
    }

    /**
     * Determine if the Goal has been fulfilled or not.
     *
     * @param player
     * @return isFulfilled true/false
     */
    @Override
    public boolean isFulfilled(Player player) {
        return player.getHealth() >= minimumHealth;
    }

    /**
     * Getter for minimum requirement for goal.
     *
     * @return int minimumHealth
     */
    public int getValue() {
        return minimumHealth;
    }

    /**
     * toString for required minimum amount of health points.
     *
     * @return String minimumHealth
     */
    @Override
    public String toString() {
        return "Health Goal: " + minimumHealth;
    }
}
