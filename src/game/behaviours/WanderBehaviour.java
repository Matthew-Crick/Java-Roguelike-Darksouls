package game.behaviours;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;

/**
 * A class that figures out a MoveActorAction for wandering enemies
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class WanderBehaviour extends Action implements Behaviour {

	/**
	 * Random number generator
	 */
	private final Random random = new Random();

	/**
	 * Returns a MoveAction to wander to a random location, if possible.  
	 * If no movement is possible, returns null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		ArrayList<Action> actions = new ArrayList<Action>();

		// If the enemy is either being hostile, or does not naturally wander, then return null
		if (!actor.hasCapability(Status.NOT_HOSTILE_TO_PLAYER) || actor.hasCapability(Abilities.DOES_NOT_WANDER)) {
			return null;
		}

		// Get the possible locations to move to
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
            	actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

		// Return a random MoveActorAction if there is at least one available, otherwise return null
		if (!actions.isEmpty()) {
			return actions.get(random.nextInt(actions.size()));
		}
		else {
			return null;
		}

	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Raagrh...";
	}
}
