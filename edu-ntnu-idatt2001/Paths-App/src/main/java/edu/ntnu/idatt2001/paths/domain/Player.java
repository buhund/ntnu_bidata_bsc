/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import edu.ntnu.idatt2001.paths.assets.ItemsWeaponsEnum;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Player represents the player character, their stats, inventory, score, class/specialization (and related image).
 */
public class Player {

    /**
     * Player name
     */
    private final String name;

    /**
     * Player health (aka. "Health Points", aka. "HP").
     */
    private final int health;

    /**
     * Player score.
     */
    private final int score;

    /**
     * Player gold.
     */
    private final int gold;

    /**
     * Player inventory.
     */
    private List<String> inventory;

    /**
     * Player "specialization", also known as "player class".
     */
    private String playerSpecialization;

    /**
     * Image, i.e. character model, for the selected player specialization/class.
     */
    private Image playerSpecializationImage;

    /**
     * Tracks the current Link for this Player.
     */
    private Link currentLink;

    /**
     * Path to save file.
     */
    private static final String FILE_PATH = "src/main/resources/autoSave/autoSave";


    /**
     * Constructor for Player.
     * @param name
     * @param health
     * @param score
     * @param gold
     */
    public Player(String name, int health, int score, int gold, List<String> inventory, String playerSpecialization, Image playerSpecializationImage) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = inventory;
        this.playerSpecialization = playerSpecialization;
        this.playerSpecializationImage = playerSpecializationImage;
    }

    /**
     * Getter for Player gold.
     * @return gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Getter for Player health.
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for Player score.
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for Player inventory.
     * @return inventory
     */
    public List<String> getInventory() {
        return inventory;
    }

    /**
     * Getter for Player name.
     * @return name
     */
    public String getName() {
        return name;
    }

    public Image getPlayerSpecializationImage() {
        return playerSpecializationImage;
    }

    public String getPlayerSpecialization() {
        return playerSpecialization;
    }

    /**
     * Add health to player health.
     * @param health
     * @return
     */
    public int addHealth(int health) {
        int newHealth = health + getHealth();
        if(newHealth > 0) {
            return newHealth;
        } else {
            throw new IllegalArgumentException("Health cannot be less than 1");
        }
    }

    /**
     * Add gold
     * @param gold
     * @return gold
     */
    public int addGold(int gold) {
        int newGold = gold + getGold();
        if (newGold >= 0) {
            return newGold;
        } else {
            throw new IllegalArgumentException("Gold cannot be less than 0");
        }
    }

    /**
     * Add score.
     * @param score
     * @return
     */
    public int addScore(int score) {
        int newScore = score + getScore();
        if (newScore >= 0) {
            return newScore;
        } else {
            throw new IllegalArgumentException("Score cannot be less than 0");
        }
    }

    /**
     * Add item to inventory.
     * @param item
     * @return
     */
    public void addToInventory(String item) {
        inventory.add(item);
    }

    public void removeFromInventory(String item) {
        inventory.remove(item);
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public boolean hasItem(String item) {
        return inventory.stream().anyMatch(i -> i.equals(item));
    }

    @Override
    public String toString() {
        return "Player{" +
            "inventory=" + inventory +
            "}";
    }

    /**
     * Gets the current Link for this Player.
     *
     * @return the current Link
     */
    public Object getCurrentLink() {
        return currentLink;
    }

    /**
     * Sets the current Link for this Player.
     *
     * @param link the current Link
     */
    public void setCurrentLink(Link link) {
        this.currentLink = link;
    }

    public void getCharacter() {
        //TODO Add method to get character
    }

    public void setCharacter() {
        //TODO Add method to set character
    }

    /**
     * Checks if the player has a specific item in the inventory.
     *
     * @param item the item to add
     */
    public boolean hasItemInInventory(String item) {
        try {
            return inventory.contains(item);
        } catch (Exception e) {
            throw new IllegalArgumentException("Item not found in inventory");
        }
    }

    /**
     * Removes an item from the inventory.
     *
     * @param item the item to remove
     */
    public void removeItemFromInventory(String item) {
        try {
            inventory.remove(item);
        } catch (Exception e) {
            throw new IllegalArgumentException("Item not found in inventory");
        }
    }

    /**
     * Lets the player attack.
     *
     * @param attackValue the attack value
     */
    public int attack(int attackValue) {
        if (attackValue > 0) {
            return attackValue;
        } else {
            throw new IllegalArgumentException("Attack value cannot be less than 0");
        }
    }

    /**
     * Uses a weapon from the inventory.
     *
     * @param item the item to use
     */
    public void useWeapon(String item) {
            if (hasItemInInventory(item)) {
                //Attack with item
                ItemsWeaponsEnum weaponsEnum = ItemsWeaponsEnum.valueOf(item.toUpperCase());
//                int attackValue = weaponsEnum.getBaseDamage() * weaponsEnum.getCriticalDamage(); // TODO Make roll/random for critical hit.
                int attackValue = weaponsEnum.getBaseDamage();
                attack(attackValue);

                //Check if item breaks
                if (weaponsEnum.breakChance > 0) {
                    int breakChance = (int) (Math.random() * 100);
                    if (breakChance <= weaponsEnum.breakChance) {
                        removeItemFromInventory(item);
                    }
                }
            } else {
                throw new IllegalArgumentException("Item not in inventory");
        }
    }
}

