package game.weapons;

import game.enums.Status;
import game.interfaces.Resettable;

/**
 * Class for the Yhorm's Great Machete weapon
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class YhormsGreatMachete extends GameWeaponItem implements Resettable {

    /**
     * Constructor.
     */
    public YhormsGreatMachete() {
        super("Yhorm's Great Machete", 'x', 95, "attacks", 60);
        // Add to reset manager
        this.registerInstance();
    }

    @Override
    public void resetInstance() {
        // Reset statuses
        for (Status status : Status.values()) {
            this.removeCapability(status);
        }
    }

    @Override
    public boolean isExist() {
        // The weapon is kept in the game when resetting
        return true;
    }
}
