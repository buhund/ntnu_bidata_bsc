package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.idatt2001.paths.domain.*;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;


import static edu.ntnu.idatt2001.paths.testingResources.testingHero01;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {


  List<String> inventoryList = new ArrayList<>(); // Must be added to Player constructor. Empty, //TODO plz fix
  String specialization = "Unit Testing Hero";
  Image specImagePath = new Image(testingHero01);

  Player playerOne = new Player("Hemiil", 90, 90, 10, inventoryList, specialization, specImagePath);

  Link link1 = new Link("A Link Text", "A Link Reference");
  Passage passage1 = new Passage("A Title, like The warm summer day", "Content, like When " + playerOne.getName() + " went to buy an ice cream.");



  @Test
  void testGetPlayer() {
    String name = "Hemiil";
    assertSame(playerOne.getName(), name);
    System.out.println("getName: " + playerOne.getName());
    System.out.println("Local name: " + name);
  }

  @Test
  void testGetStory() {
    Map<Link, Passage> passageMap = new HashMap<>();
    passageMap.put(link1, passage1);

    Story testStory = new Story("The Banana and the Bear", passageMap);
  }

  @Test
  void testGetGoals() {
  }

  @Test
  void testBegin() {
  }

  @Test
  void testGo() {
  }

  @Test
  void testIsFulfilled() {
  }
}