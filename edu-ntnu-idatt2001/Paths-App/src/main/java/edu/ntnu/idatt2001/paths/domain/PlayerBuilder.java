/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain;

import java.util.List;

/**
 * Builder class for player.
 */
public class PlayerBuilder{

  // Class Attributes

  /**
   * Player name
   */
  private final String name;

  /**
   * Player health
   */
  private final int health;

  /**
   * Player score
   */
  private final int score;

  /**
   * Player gold
   */
  private final int gold;

  /**
   * Player inventory
   */
  private final List<String> inventory;


  /**
   * Constructor for Builder.
   * @param builder
   */
  private PlayerBuilder(Builder builder) {
    name = builder.name;
    health = builder.health;
    score = builder.score;
    gold = builder.gold;
    inventory = builder.inventory;
  }

  /**
   * Getter for player name.
   *
   * @param name
   * @return name
   */
  public String getName(String name) {
    return name;
  }

  /**
   * Getter for player health.
   *
   * @param health
   * @return health
   */
  public int getHealth(int health) {
    return health;
  }

  /**
   * Getter for player score.
   *
   * @param score
   * @return score
   */
  public int getScore(int score) {
    return score;
  }

  /**
   * Getter for player gold.
   *
   * @param gold
   * @return gold
   */
  public int getGold(int gold) {
    return gold;
  }

  /**
   * Getter for inventory.
   *
   * @param inventory
   * @return inventory
   */
  public List<String> getInventory(List<String> inventory) {
    return inventory;
  }

  /**
   * Builder Class
   */
  public static class Builder {

    /**
     * Required parameter name
      */
    private final String name;

    /**
     * Required parameter health
     */
    private final int health;

    /**
     * Optional parameter score
     */
    private int score = 0;

    /**
     * Optional parameter gold
     */
    private int gold = 0;

    /**
     * Optional parameter inventory
     */
    private List<String> inventory = null;


    /**
     * Builder for Required Parameters
     * @param name
     * @param health
     */
    public Builder(String name, int health) {
      this.name = name;
      this.health = health;
    }

    /**
     * Builder for Optional Parameter
     * @param score
     * @return this.score
     */
    public Builder score(int score) {
      this.score = score;
      return this;
    }

    /**
     * Builder for Optional Parameter
     * @param gold
     * @return this.gold
     */
    public Builder gold(int gold) {
      this.gold = gold;
      return this;
    }

    /**
     * Builder for Optional Parameter
     * @param inventory
     * @return this.inventory
     */
    public Builder inventory(List<String> inventory) {
      this.inventory = inventory;
      return this;
    }

  } // End of sub-class Builder

} // End of Class PlayerBuilder
