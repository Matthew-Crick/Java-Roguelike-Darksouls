package game.items;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

/**
 * Class for the Fog Door which the Player uses to change maps.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class FogDoor extends Item {
    /**
     * Constructor.
     */
    public FogDoor() {
        super("Fog door", '=', false);
    }

    /**
     * Adds actions to the list of allowable actions the fog door provides
     * @param action The action fog door is to provide
     */
    public void addAction(Action action) {
        this.allowableActions.add(action);
    }
}
