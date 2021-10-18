package game;

import edu.monash.fit2099.engine.*;
import game.actions.OpenChestAction;
import game.enums.Abilities;

/**
 * Class for chest.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class Chest extends Actor {
    /**
     * The location of the chest.
     */
    private Location chestLocation;

    /**
     * Constructor.
     */
    public Chest(Location chestLocation) {
        super("Chest", '?', 1000);
        this.chestLocation = chestLocation;
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if (otherActor.hasCapability(Abilities.CAN_OPEN_CHEST)) {
            actions.add(new OpenChestAction(this));
        }
        return actions;
    }

    // The chest does not do any actions itself
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Getter for the chest's location
     * @return a Location representing the chest's location
     */
    public Location getChestLocation() {
        return chestLocation;
    }
}
