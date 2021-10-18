package game.actions;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.List;

/**
 * An action to swap weapon (new weapon replaces old weapon).
 * It loops through all items in the Actor inventory.
 * The old weapon will be gone.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class SwapWeaponAction extends PickUpItemAction {

    /**
     * Constructor
     *
     * @param weapon the new item that will replace the weapon in the Actor's inventory.
     */
    public SwapWeaponAction(Item weapon) {
        super(weapon);
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Indicate that the weapon is in the Actor's inventory
        item.addCapability(Status.PICKED_UP);

        Weapon currentWeapon = actor.getWeapon();
        List<Item> items = actor.getInventory();

        // Loop through all inventory
        for(Item item : items){
            if(item.asWeapon() != null){
                actor.removeItemFromInventory(item);
                break; // After it removes that weapon, break the loop.
            }
        }

        // If the ground has item, remove that item.
        // Additionally, add new weapon to the inventory (equip).
        super.execute(actor, map);
        return actor + " swaps " + currentWeapon + " with " + item;
    }

}
