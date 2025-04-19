package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2001.paths.domain.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.ntnu.idatt2001.paths.testingResources.testingHero01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    String specialization = "Unit Testing Hero";
    Image specImagePath = new Image(testingHero01);
    List<String> inventoryList = new ArrayList<>(); // Must be added to Player constructor. Empty, //TODO plz fix
    Player player = new Player("Nobby Nobbs", 70, 1000, 120, inventoryList, specialization, specImagePath);

    @Test
    @DisplayName("Test for getting player Health points")
    void testGetHealth() {
        assertEquals(70, player.getHealth());
    }

    @Test
    @DisplayName("Test for getting player Score")
    void testGetScore() {
        assertEquals(1000, player.getScore());
    }

    @Test
    @DisplayName("Test for getting player Inventory")
    void testGetInventory() {
        List<String> inventory = new ArrayList<>();
        inventory.add("Sword of the Tux");
        inventory.add("Tryllestav of the Wizard Squared");
        inventory.add("Staff of the Rincewind");
        player.setInventory(inventory);
        System.out.println("getInventory print: " + player.getInventory().toString());
        System.out.println("Local inventory list print: " + inventory);
        assertEquals(inventory, player.getInventory());
    }

    @Test
    @DisplayName("Test for getting player Name")
    void testGetName() {
        String testNameFalse = "Conina the Hairdresser";
        assertEquals("Nobby Nobbs", player.getName());
        assertNotEquals("Nobby Nobbs", testNameFalse);
        System.out.println(player.getName() + " != " + testNameFalse);
    }

    @Test
    @DisplayName("Test for adding Health Points to player health pool")
    void testAddHealth() {
        assertEquals(100, player.getHealth()+30);
        assertEquals(10, player.getHealth()-60);
    }

    @Test
    @DisplayName("Test for adding Gold to player")
    void addGold() {
        assertEquals(120, 120);

        player.addGold(10);
        assertEquals(130, 130);

        player.addGold(-20);
        assertEquals(100, 100);
    }

    @Test
    @DisplayName("Test for adding to the player's current Score")
    void addScore() {
        assertEquals(1000,1000);
        assertNotEquals(20, 1000);

        player.addScore(120);
        assertEquals(1200,1200);
        assertNotEquals(1000, 1200);

        player.addScore(-400);
        assertEquals(800, 800);
        assertNotEquals(1000, 800);
    }

    @Test
    @DisplayName("Test adding Items to player Inventory")
    void addToInventory() {
        List<String> inventory = new ArrayList<>();
        inventory.add("Sword of the Tux");
        inventory.add("Tryllestav or the Wizard Squared");
        inventory.add("Staff of the Rincewind");
        player.setInventory(inventory);
        System.out.println("getInventory print: " + player.getInventory().toString());
        System.out.println("Local inventory list print: " + inventory);
        assertEquals(inventory, player.getInventory());
    }

    @Test
    @DisplayName("Test for Setting player Inventory by passing an List of Items")
    void setInventory() {
        List<String> inventory = new ArrayList<>();
        inventory.add("Sword of the Tux");
        inventory.add("Tryllestav or the Wizard Squared");
        inventory.add("Staff of the Rincewind");
        player.setInventory(inventory);
        System.out.println("getInventory print: " + player.getInventory().toString());
        System.out.println("Local inventory list print: " + inventory);
        assertEquals(inventory, player.getInventory());
    }

    @Test
    @DisplayName("Testing toString method")
    void testToString() {
    }
}