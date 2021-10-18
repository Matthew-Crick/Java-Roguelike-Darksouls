package game.actions;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Action for obtaining souls from an item.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class RetrieveSoulsAction extends PickUpItemAction {

    /**
     * Constructor.
     *
     * @param item the item to pick up and retrieve souls from
     */
    public RetrieveSoulsAction(Item item) {
        super(item);
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Check that actor and item implement the Soul interface, in which case
        // transfer the souls from the item to the actor
        if (actor.asSoul() != null && item.asSoul() != null) {
            item.asSoul().transferSouls(actor.asSoul());
        }
        // This action extends PickUpItemAction, so call the execute for PickUpItemAction
        super.execute(actor, map);
        return "Retrieved " + item;
    }
}
