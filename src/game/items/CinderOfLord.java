package game.items;

import edu.monash.fit2099.engine.Item;

/**
 * Class for the Cinder of Lord item
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class CinderOfLord extends Item {

    /**
     * Constructor.
     */
    public CinderOfLord(String name) {
        // The name given will differ for different bosses / Cinder of lords
        super(name, '%', true);
    }
}
