package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

/**
 * Action for resetting of an enemy's position.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class ResetPositionAction extends Action {

    /**
     * The initial location of the enemy to reset to upon death
     */
    private Location initialLocation;

    /**
     * Constructor.
     *
     * @param initialLocation The initial location of the enemy to reset to upon death
     */
    public ResetPositionAction(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        GameMap gameMap = initialLocation.map();
        // Check the initial location and see if there is an actor there
        if (gameMap.isAnActorAt(initialLocation)) {
            // If the enemy is not yet at the initial location, there is another actor
            // at the initial location that should be removed
            if (!gameMap.locationOf(actor).equals(initialLocation)) {
                gameMap.removeActor(gameMap.getActorAt(initialLocation));
            }
        }
        // Move the actor to the initial location
        gameMap.moveActor(actor, initialLocation);

        // Enemy does not need to be reset anymore
        actor.removeCapability(Status.ENEMY_POSITION_TO_BE_RESET);
        return actor + " has been reset to original location";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
