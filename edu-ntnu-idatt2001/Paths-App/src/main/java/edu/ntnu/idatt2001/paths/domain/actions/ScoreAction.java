/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.actions;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Action for Score.
 * Updates Score for player.
 */
public class ScoreAction implements Action {

    /**
     * Score points to add/subtract from player total.
     * Amount to add/subtract.
     */
    private final int points;

    /**
     * Hash Code.
     */
    private final int hashCode;

    /**
     * Constructor for player Score points.
     *
     * @param points
     */
    public ScoreAction(int points) {
        this.points = points;
        this.hashCode = this.getClass().hashCode() * points < 0 ? points : points + 1;
    }

    /**
     * Execute Score action on player.
     *
     * @param player
     */
    @Override
    public void execute(Player player) {
        String operation = points > 0 ? "added" : "subtracted";

        try {
            player.addScore(points);
            System.out.println("Score " + operation + " " + points + " points.");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * HashCode method for ScoreAction.
     * @return
     */
    public int hashCode() {
        return hashCode;
    }
}
