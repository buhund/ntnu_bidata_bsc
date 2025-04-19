/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.assets.PlayerSpecializationEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Output for saving GameState file.
 * GameState contains selected Character Class, Starting Item, starting Health, Gold and Score, Story path.
 * Ingame will also write Current Passage.
 */
public class GameStateWriter {

  /**
   * File name for Game State file.
   */
  private static final String FILE_NAME = "gameState.txt";

  /**
   * Path to save game state file + FILE_NAME.
   * FILE_NAME is required!
   */
  private static final String FILE_PATH = "src/main/resources/gameState/" + FILE_NAME;


  /**
   * Write starting parameters to file. Set in NewGameView GUI inputs.
   *
   * @param playerSpecializationEnum
   * @param selectedStoryFile
   * @param characterName
   * @param startingHP
   * @param startingGold
   * @param startingScore
   */
  public static void writeGameStateToFile(PlayerSpecializationEnum playerSpecializationEnum, File selectedStoryFile, String characterName, int startingHP, int startingGold, int startingScore) {
    try(FileWriter writer = new FileWriter(FILE_PATH)) {
      writer.write("class=" + playerSpecializationEnum.getImagePath());
      writer.write(System.lineSeparator());
      writer.write("startingItem=" + playerSpecializationEnum.getStartingItem());
      writer.write(System.lineSeparator());
      writer.write("charName=" + characterName);
      writer.write(System.lineSeparator());
      writer.write("startHP=" + startingHP);
      writer.write(System.lineSeparator());
      writer.write("startScore=" + startingGold);
      writer.write(System.lineSeparator());
      writer.write("startGold=" + startingScore);
      writer.write(System.lineSeparator());
      writer.write("story=" + selectedStoryFile.getPath());
      writer.write(System.lineSeparator());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writer for writing CurrentPassage while ingame.
   *
   * @param currentPassage
   */
  public static void writeCurrentPassage(String currentPassage) {
    try(FileWriter writer = new FileWriter(FILE_PATH, true)) {
      writer.write("passage=" + currentPassage);
//      writer.write(System.lineSeparator());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

} // End of class GameStateWriter