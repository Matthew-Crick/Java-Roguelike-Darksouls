package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action for when an actor dies
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class DieAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        // Remove the actor
        map.removeActor(actor);
        return actor + " is killed";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
