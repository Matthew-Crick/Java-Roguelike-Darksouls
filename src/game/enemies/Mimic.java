package game.enemies;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Location;
import game.Chest;
import game.enums.Abilities;

import java.util.Random;

/**
 * Class for the mimic enemy.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class Mimic extends Enemy {
    /**
     * The location of the chest the mimic spawned from.
     */
    private Location chestLocation;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Constructor.
     */
    public Mimic(Location chestLocation) {
        // Initialise properties
        super("Mimic", 'M', 100);
        this.chestLocation = chestLocation;
        this.soulCount = 200;
        this.addCapability(Abilities.DROPS_SOULS_ON_DEATH);
    }

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        // Mimic has no weapon, but kicks
        return new IntrinsicWeapon(55, "kicks");
    }

    @Override
    public String toString() {
        // As the mimic has no weapon, display "no weapon" for its information in the console
        return name + "(" + hitPoints + "/" + maxHitPoints + ")(no weapon)";
    }

    @Override
    public void resetInstance() {
        // If the mimic is still alive, on reset, place a chest at the location it spawned at
        if (this.isConscious()) {
            // Get rid of any actors already at the chest location
            if (chestLocation.containsAnActor()) {
                Actor actor = chestLocation.getActor();
                chestLocation.map().removeActor(actor);
            }
            chestLocation.addActor(new Chest(chestLocation));
        }
        // Mimic is not needed to still be on map on reset
        hitPoints = 0;
    }

    @Override
    public boolean isExist() {
        return false;
    }
}
