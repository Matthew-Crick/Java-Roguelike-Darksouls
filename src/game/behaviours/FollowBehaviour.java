package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;

/**
 * A class that figures out a MoveAction that will move the enemy one step
 * closer to a target Actor.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class FollowBehaviour extends Action implements Behaviour {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor target;

	/**
	 * Constructor.
	 * 
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * Potentially creates a MoveActorAction for the enemy
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return a MoveActorAction if appropriate, else null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Return null if either the target or the actor is not present on the map
		if (!map.contains(target) || !map.contains(actor))
			return null;

		// If the enemy is not currently hostile, then it will no following required
		if (actor.hasCapability(Status.NOT_HOSTILE_TO_PLAYER)) {
			return null;
		}

		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);
		// Determine a MoveActorAction that gets the actor closer to the target
		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}

		return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		return null;
	}
}