/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.domain.actions;

import edu.ntnu.idatt2001.paths.domain.Player;

/**
 * Action for Inventory.
 * Updates Inventory for player.
 */
public class InventoryAction implements Action {

    /**
     * Item to be used with player inventory.
     * Item to add/remove: true/false
     */
    private final String item;
    private boolean add;
    private int hashCode;

    /**
     * Constructor for inventory action.
     * Input what items to add/remove/use.
     *
     * @param item
     */
    public InventoryAction(String item) {
        this.item = item;
    }

    /**
     * Constructor for inventory action.
     * Input what items to add/remove/use.
     *
     * @param item
     * @param add
     */
    public InventoryAction(String item, boolean add) {
        this.item = item;
        this.add = add;
        this.hashCode = this.getClass().hashCode() * item.hashCode() * (add ? 1 : -1);
    }

    /**
     * Execute inventory action on player.
     *
     * @param player
     */
    @Override
    public void execute(Player player) {
        if (add) {
            player.addToInventory(item);
        } else {
            player.removeFromInventory(item);
        }
    }
}
