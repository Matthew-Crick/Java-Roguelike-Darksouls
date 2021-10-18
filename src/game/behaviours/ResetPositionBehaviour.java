package game.behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.actions.ResetPositionAction;
import game.enums.Status;
import game.interfaces.Behaviour;

/**
 * A class that figures out a ResetPositionAction for enemies
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class ResetPositionBehaviour implements Behaviour {

    /**
     * The initial location of the enemy to reset to
     */
    private Location initialLocation;

    /**
     * Constructor.
     *
     * @param initialLocation The initial location of the enemy to reset to
     */
    public ResetPositionBehaviour(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    /**
     * Potentially creates a ResetPositionAction for the enemy
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an ResetPositionAction if appropriate, else null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // If the enemy is not to be reset, then don't return a ResetPositionAction
        if (!actor.hasCapability(Status.ENEMY_POSITION_TO_BE_RESET)) {
            return null;
        }
        // Else return a ResetPositionAction with the initial location to reset to
        return new ResetPositionAction(initialLocation);
    }
}
