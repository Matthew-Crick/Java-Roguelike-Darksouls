package game.enemies;

import edu.monash.fit2099.engine.*;
import game.behaviours.ResetPositionBehaviour;
import game.weapons.Broadsword;

/**
 * A skeleton enemy.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Skeleton extends Enemy {

    /**
     * Constructor.
     *
     * @param initialLocation The skeleton's initial location to reset to upon death
     */
    public Skeleton(Location initialLocation) {
        // Initialise properties
        super("Skeleton", 's', 100);
        this.initialLocation = initialLocation;
        this.soulCount = 250;

        // Initialise inventory
        this.addItemToInventory(new Broadsword());

        // Skeletons reset position on reset
        behaviours.add(0, new ResetPositionBehaviour(initialLocation));
    }
}
