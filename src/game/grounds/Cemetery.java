package game.grounds;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enemies.Undead;

import java.util.Random;

/**
 * A class that represents a cemetery to spawn undead.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Cemetery extends Ground {

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Constructor.
     */
    public Cemetery() {
        super('c');
    }

    /**
     * Inform the cemetery of the passage of time to spawn undead.
     *
     * @param location The location of the cemetery
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        // If there is no current actor on top of the cemetery, spawn an undead with a 25% chance
        if ((!location.containsAnActor()) && (rand.nextInt(100) < 25)) {
            location.addActor(new Undead());
        }
    }
}
