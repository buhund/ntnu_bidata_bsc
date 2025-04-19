/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Game Goal: Gold.
 * Defines an amount needed to "win" or achieve certain goals
 * or milestones ingame.
 */
public class GoldGoal implements Goal {

    /**
     * Minimum requirements for fulfilling the Gold Goal.
     */
    private final int minimumGold;

    /**
     * Constructor for Gold Goal.
     *
     * @param minimumGold
     */
    public GoldGoal(int minimumGold) {
        this.minimumGold = minimumGold;
    }

    /**
     * Getter for minimum required gold to achieve goal.
     *
     * @return int minimumGold
     */
    public int getValue() {
        return minimumGold;
    }

    /**
     * Determine if the Goal has been fulfilled or not.
     * @param player
     * @return isFulfilled true/false
     */
    @Override
    public boolean isFulfilled(Player player) {
        return player.getGold() >= minimumGold;
    }

    /**
     * toString for gold goal.
     * @return
     */
    @Override
    public String toString() {
        return "Gold Goal: " + minimumGold;
    }

}
