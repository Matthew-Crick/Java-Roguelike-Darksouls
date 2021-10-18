package game.grounds;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;

/**
 * A class that represents a gorge or endless gap that is dangerous for the Player.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Valley extends Ground {

	/**
	 * The damage to deal to an actor who steps into the valley
	 */
	private int damageDealt = 500;

	/**
	 * Constructor.
	 */
	public Valley() {
		super('+');
	}

	/**
	 * Deals damage to an actor that is on the valley and has the CAN_FALL capability
	 *
	 * @param actor The Actor acting
	 * @param location The current Location
	 * @param direction The direction of the Ground from the Actor
	 * @return an empty collection of Actions, as no actions are necessary to deal damage
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		if (location.containsAnActor()) {
			if (location.getActor().hasCapability(Abilities.CAN_FALL)) {
				actor.hurt(damageDealt);
			}
		}
		return super.allowableActions(actor, location, direction);
	}

	/**
	 * Allows actors with the CAN_FALL capability to enter
	 *
	 * @param actor the Actor to check
	 * @return true if the actor has the CAN_FALL capability, else false
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor.hasCapability(Abilities.CAN_FALL)) {
			return super.canActorEnter(actor);
		}
		return false;
	}

}
