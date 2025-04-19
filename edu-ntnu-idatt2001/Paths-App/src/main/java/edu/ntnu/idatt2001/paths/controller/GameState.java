/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.assets.PlayerSpecializationEnum;
import edu.ntnu.idatt2001.paths.domain.Passage;
import edu.ntnu.idatt2001.paths.domain.Player;
import javafx.scene.image.Image;
import java.io.File;


/**
 * Class to hold a game state.
 * Keep track of CharacterModel, Player, player stats (HP, gold, score), Items and Story.
 * Singleton instancing of GameState.
 */
public class GameState {

  /**
   * Static instance of GameState class, aka. Singleton
   */
  private static GameState INSTANCE;

  /**
   * Player Specialization (aka. "class").
   * Selected via Enum PlayerSpecializationEnum.
   */
  private PlayerSpecializationEnum playerSpecializationEnum;

  /**
   * Image associated with selected player specialization.
   */
  private Image playerCharacterImage;

  /**
   * Player name.
   * Input via NewGameView GUI name input field.
   */
  private String playerName;

  /**
   * Selected story file.
   * Input via NewGameView GUI combobox.
   */
  private File selectedStoryFile;

  /**
   * Player starting item.
   * Dependent upon selected player specialization, as
   * selected in NewGameView GUI combobox.
   * Defined in player specialization enum.
   */
  private String startingItem;

  /**
   * Player starting item icon.
   * Dependent upon selected player specialization, as
   * selected in NewGameView GUI combobox.
   * Defined in player specialization enum.
   */
  private String startingItemIcon;

  /**
   * Instantiating the Player object class.
   */
  private Player player;

  /**
   * Instantiating the Passage object class.
   */
  private Passage passage;

  /**
   * Player starting health points,
   * as set via NewGameView GUI input field.
   */
  public int startingHP;

  /**
   * Player starting score,
   * as set via NewGameView GUI input field.
   */
  public int startingScore;

  /**
   * Player starting gold,
   * as set via NewGameView GUI input field.
   */
  public int startingGold ;


  /**
   * Private constructor for GameState.
   * Prevents direct instantiation.
   */
  private GameState() {
  }

  /**
   * Getter for the Singleton instance.
   *
   * @return INSTANCE of GameState
   */
  public static GameState getInstance() {
    if (INSTANCE == null) {
      synchronized (GameState.class) {
        if (INSTANCE == null) {
          INSTANCE = new GameState();
        }
      }
    }
    return INSTANCE;
  }

  /**
   * Method for setting Player character as set up in NewGameView GUI.
   *
   * @param player
   */
  public void PlayerCharacter(Player player) {
    this.player = player;
  }

  /**
   * Getter for player object.
   *
   * @return
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Getter for Player Specialization.
   *
   * @return playerSpecializationEnum
   */
  public PlayerSpecializationEnum getPlayerSpecialization() {
    return playerSpecializationEnum;
  }

  /**
   * Setter for Player Specialization.
   *
   * @param playerSpecializationEnum
   */
  public void setPlayerSpecialization(PlayerSpecializationEnum playerSpecializationEnum) {
    this.playerSpecializationEnum = playerSpecializationEnum;
  }

  /**
   * Getter for selected story file.
   *
   * @return
   */
  public File getSelectedStoryFile() {
    return selectedStoryFile;
  }

  /**
   * Setter for selected story file.
   *
   * @param selectedStoryFile
   */
  public void setSelectedStoryFile(File selectedStoryFile) {
    this.selectedStoryFile = selectedStoryFile;
  }

  /**
   * Getter for player name.
   *
   * @return
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Setter for player name.
   *
   * @param playerName
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Getter for starting item.
   *
   * @return
   */
  public String getStartingItem() {
    return startingItem;
  }

  /**
   * Setter for starting item.
   *
   * @param startingItem
   */
  public void setStartingItem(String startingItem) {
    this.startingItem = startingItem;
  }

  /**
   * Getter for Starting Item icon.
   *
   * @return
   */
  public String getStartingItemIcon() {
    return startingItemIcon;
  }

  /**
   * Setter for Starting Item icon.
   *
   * @param startingItemIcon
   */
  public void setStartingItemIcon(String startingItemIcon) {
    this.startingItemIcon = startingItemIcon;
  }

  /**
   * Getter for Player Character image.
   *
   * @return
   */
  public Image getPlayerCharacterImage() {
    return playerCharacterImage;
  }

  /**
   * Setter for Player Character image.
   *
   * @param playerCharacterImage
   */
  public void setPlayerCharacterImage(Image playerCharacterImage) {
    this.playerCharacterImage = playerCharacterImage;
  }

  /**
   * Getter for starting score amount.
   *
   * @return
   */
  public int getStartingHP() {
    return startingHP;
  }

  /**
   * Setter for starting health amount.
   *
   * @param startingHP
   */
  public void setStartingHP(int startingHP) {
    this.startingHP = startingHP;
  }

  /**
   * Getter for starting score amount.
   * @return
   */
  public int getStartingScore() {
    return startingScore;
  }

  /**
   * Setter for starting score amount.
   *
   * @param startingScore
   */
  public void setStartingScore(int startingScore) {
    this.startingScore = startingScore;
  }

  /**
   * Getter for starting gold amount.
   *
   * @return
   */
  public int getStartingGold() {
    return startingGold;
  }

  /**
   * Setter for starting gold amount.
   *
   * @param startingGold
   */
  public void setStartingGold(int startingGold) {
    this.startingGold = startingGold;
  }

  /**
   * Getter for passage.
   *
   * @return
   */
  public Passage getPassage() {
    return passage;
  }

  /**
   * Setter for passage.
   *
   * @param passage
   */
  public void setPassage(Passage passage) {
    this.passage = passage;
  }

  /**
   * Setter for player.
   *
   * @param player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }
}
