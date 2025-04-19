/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import edu.ntnu.idatt2001.paths.domain.goals.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Class for holding game data.
 * Shares functionality with GameState class, which acts as an extended version of the Game class.
 */
public class Game {

    /**
     * Player object.
     */
    private final Player player;

    /**
     * Story object.
     */
    private Story story;

    /**
     * List of game goals.
     */
    private final List<Goal> goals;

    /**
     * File path to autosave file.
     */
    private static final String FILE_PATH = "src/main/resources/autoSave/autoSave";

    /**
     * Passage object.
     */
    private Passage passage;

    /**
     * Instantiates a new Game.
     *
     * @param player the player
     * @param story  the story
     * @param goals  the goals
     */
    public Game(Player player, Story story, List<Goal> goals) {
        this.player = player;
        this.story = story;
        this.goals = goals;
    }


    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets story.
     *
     * @return the story
     */
    public Story getStory() {
        return story;
    }

    /**
     * Gets goals.
     *
     * @return the goals
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Begin passage.
     *
     * @return the passage
     */
    public Passage begin() {
        return story.getOpeningPassage();
    }

    /**
     * Go passage.
     *
     * @param link the link
     * @return the passage
     */
    public Passage go(Link link) {
        return story.getPassage(link);
    }

    /**
     * Checks if game goals are achieved.
     *
     * @return a map of the goals and their status
     */
    public Map<String, Boolean> checkGameGoals() {
        Map<String, Boolean> goalStatus = new HashMap<>();
        goalStatus.put("healthGoalAchieved", true);
        goalStatus.put("goldGoalAchieved", true);
        goalStatus.put("scoreGoalAchieved", true);
        goalStatus.put("inventoryGoalAchieved", true);

        if (goals == null || player == null) {
            throw new IllegalStateException("Goals or player is not initialized.");
        }

        for (Goal g : goals) {
            if (g == null) {
                continue;
            }

            try {
                if (g instanceof HealthGoal && ((HealthGoal) g).getValue() > player.getHealth()) {
                    goalStatus.put("healthGoalAchieved", false);
                } else if (g instanceof GoldGoal && ((GoldGoal) g).getValue() > player.getGold()) {
                    goalStatus.put("goldGoalAchieved", false);
                } else if (g instanceof ScoreGoal && ((ScoreGoal) g).getValue() > player.getScore()) {
                    goalStatus.put("scoreGoalAchieved", false);
                } else if (g instanceof InventoryGoal) {
                    List<String> playerInventory = new ArrayList<>(player.getInventory());
                    if (!playerInventory.containsAll(((InventoryGoal) g).getItems())) {
                        goalStatus.put("inventoryGoalAchieved", false);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error checking goal: " + e.getMessage());
            }
        }

        return goalStatus;
    }

    /**
     * Checks if game is lost.
     *
     * @return true if game is lost, false otherwise
     */
    public boolean gameLost() {
        if (player == null) {
            throw new IllegalStateException("Player is not initialized.");
        }

        try {
            return player.getHealth() <= 0;
        } catch (Exception e) {
            System.out.println("Error checking if game is lost: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if game is won.
     *
     * @return true if game is won, false otherwise
     */
    public boolean gameWon() {
        try {
            return !checkGameGoals().containsValue(false);
        } catch (Exception e) {
            System.out.println("Error checking if game is won: " + e.getMessage());
            return false;
        }
    }



    /**
     * Save game.
     *
     * @throws IOException the io exception
     */
    public void saveGame() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write the story name
            writer.write(getStory().getTitle());
            writer.newLine();

            // Write the current passage name
            writer.write("::" + passage.getTitle());
        }
    }

    /**
     * Resume game.
     *
     * @throws IOException the io exception
     */
    public void resumeGame() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            // Read the story name
            String storyName = reader.readLine();

            // Read the current passage name
            String passageName = reader.readLine();

            // Find the corresponding story based on the story name
            Story savedStory = null;
            for (Goal goal : getGoals()) {
                if (goal instanceof Story && ((Story) goal).getTitle().equals(storyName)) {
                    savedStory = (Story) goal;
                    break;
                }
            }

            // Find the corresponding passage based on the passage name in the saved story
            Passage savedPassage = null;
            if (savedStory != null) {
                for (Passage passage : savedStory.getPassages().values()) {
                    if (passage.getTitle().equals(passageName)) {
                        savedPassage = passage;
                        break;
                    }
                }
            }

            // Set the current passage and story
            if (savedPassage != null) {
                getPlayer().setCurrentLink(new Link(passage.getTitle(), savedPassage.getContent()));
                setStory(savedStory);
            }
        }
    }

    /**
     * Sets story.
     *
     * @param story the story
     */
    public void setStory(Story story) {
        this.story = story;
    }
}
