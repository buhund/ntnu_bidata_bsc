/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.goals;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Game Goal: Score.
 * Defines an amount needed to "win" or achieve certain goals
 * or milestones in-game.
 */
public class ScoreGoal implements Goal {

  /**
   * Minimum requirements for fulfilling the Goal.
   * Minimum required Score.
   */
  private final int minimumPoints;

  /**
   * Constructor for Health Goal.
   * @param minimumPoints
   */
  public ScoreGoal(int minimumPoints) {
      this.minimumPoints = minimumPoints;
  }

  /**
   * Determine if the Goal has been fulfilled or not.
   * @param player
   * @return isFulfilled true/false
   */
  @Override
  public boolean isFulfilled(Player player) {
      return player.getScore() >= minimumPoints;
  }

  /**
   * Getter for minimum requirement for goal.
   *
   * @return int minimumPoints
   */
  public int getValue() {
    return minimumPoints;
  }

  /**
   * toString for required minimum amount of score points.
   *
   * @return String minimumPoints
   */
    @Override
    public String toString() {
        return "Score Goal: " + minimumPoints;
    }
}
