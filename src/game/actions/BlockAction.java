package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action for when an attack is blocked
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class BlockAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " attack is blocked";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
