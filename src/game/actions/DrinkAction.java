package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.interfaces.Consumable;

/**
 * Action for drinking a consumable
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class DrinkAction extends Action {

    /**
     * The item to drink from
     */
    private Item item;

    /**
     * The charges the item has
     */
    private int charges;

    /**
     * The percentage of max health the item heals for
     */
    private int percentOfMaxHealth;

    /**
     * Constructor.
     *
     * @param item The item to drink from
     * @param charges The charges the item has
     * @param percentOfMaxHealth The percentage of max health the item heals for
     */
    public DrinkAction(Item item, int charges, int percentOfMaxHealth) {
        this.item = item;
        this.charges = charges;
        this.percentOfMaxHealth = percentOfMaxHealth;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Check if there are charges available
        if (charges == 0) {
            return "No " + item + " charges available";
        }
        // Check that the actor and items are consumables, and if so add the appropriate
        // amount of health of the actor and reduce the charge of the item
        if (item instanceof Consumable && actor instanceof Consumable) {
            ((Consumable) actor).addHealth(percentOfMaxHealth);
            ((Consumable) item).reduceCharges();
        }
        return actor + " drinks " + item;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Drink " + item;
    }
}
