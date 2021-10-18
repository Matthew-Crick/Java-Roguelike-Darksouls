package game.enemies;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

/**
 * An undead enemy.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Undead extends Enemy {

	/**
	 * Constructor.
	 */
	public Undead() {
		// Initialise properties
		super("Undead", 'u', 50);
		this.soulCount = 50;

		// Initialise capabilities
		this.addCapability(Status.DIES_RANDOMLY);
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		// Undead has no weapon, but an intrinsic weapon
		return new IntrinsicWeapon(20, "thwacks");
	}

	@Override
	public String toString() {
		// As the undead has no weapon, display "no weapon" for its information in the console
		return name + "(" + hitPoints + "/" + maxHitPoints + ")(no weapon)";
	}

	@Override
	public void resetInstance() {
		// Set health to zero so that it will die and be removed on its next turn
		hitPoints = 0;
	}

	@Override
	public boolean isExist() {
		// Undead are cleared and so not kept in the game when resetting
		return false;
	}
}
