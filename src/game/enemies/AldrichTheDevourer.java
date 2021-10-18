package game.enemies;

import edu.monash.fit2099.engine.*;
import game.behaviours.FollowBehaviour;
import game.behaviours.ResetPositionBehaviour;
import game.enums.Abilities;
import game.enums.Status;
import game.items.CinderOfLord;
import game.weapons.DarkmoonLongbow;
import game.weapons.YhormsGreatMachete;

/**
 * Class for the Aldrich the Devourer boss.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class AldrichTheDevourer extends LordOfCinder {

    public AldrichTheDevourer(Location initialLocation) {
        // Initialise properties
        super("Aldrich the Devourer", 'A', 350);
        this.initialLocation = initialLocation;
        this.soulCount = 5000;

        // Initialise capabilities
        this.addCapability(Abilities.HAS_ACTION_AFTER_KILLED);
        this.addCapability(Abilities.DOES_NOT_WANDER);
        // Aldrich starts off hostile to detect the player from a distance
        this.removeCapability(Status.NOT_HOSTILE_TO_PLAYER);

        // Initialise inventory
        this.addItemToInventory(new DarkmoonLongbow());
        this.addItemToInventory(new CinderOfLord("Cinder of Aldrich the Devourer"));

        // Aldrich resets position on reset
        behaviours.add(0, new ResetPositionBehaviour(initialLocation));
    }

    @Override
    public void resetInstance() {
        // Reset as with regular enemies, but Aldrich starts off hostile to detect the player from a distance
        super.resetInstance();
        this.removeCapability(Status.NOT_HOSTILE_TO_PLAYER);
    }
}
