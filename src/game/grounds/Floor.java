package game.grounds;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.enums.Abilities;

/**
 * A class that represents the floor inside a building.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Floor extends Ground {

	/**
	 * Constructor.
	 */
	public Floor() {
		super('_');
	}

	/**
	 * Prevents actors with the BLOCKED_BY_FLOORS capability from entering
	 *
	 * @param actor the Actor to check
	 * @return false if the actor has the BLOCKED_BY_FLOORS capability, else true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor.hasCapability(Abilities.BLOCKED_BY_FLOORS)) {
			return false;
		}
		return super.canActorEnter(actor);
	}
}
