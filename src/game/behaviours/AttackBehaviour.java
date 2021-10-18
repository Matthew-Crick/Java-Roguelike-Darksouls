package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.BlockAction;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;

/**
 * A class that figures out an AttackAction for the attack behaviour of enemies
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Potentially creates an AttackAction for an adjacent hostile actor
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an AttackAction if appropriate, else null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // If the enemy is currently not hostile, then no AttackAction returned
        if (actor.hasCapability(Status.NOT_HOSTILE_TO_PLAYER)) {
            return null;
        }

        Location here = map.locationOf(actor);
        // Check each location surrounding the actor
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            // If there is an adjacent actor that is hostile to the enemy, then return an
            // AttackAction to attack that actor; the direction is irrelevant since enemies
            // do not have a menu
            if (destination.containsAnActor()) {
                if (destination.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    return new AttackAction(destination.getActor(), "N/A");
                }
            }
        }

        // For enemies that attack with a ranged weapon
        if (actor.hasCapability(Abilities.RANGED_WEAPON)) {
            // Create a box that goes 3 squares in each direction from the enemy's location
            NumberRange xs, ys;
            xs = new NumberRange(here.x() - 3, 7);
            ys = new NumberRange(here.y() - 3, 7);
            // Check to see if there is a hostile actor in this box
            for (int x : xs) {
                for (int y : ys) {
                    if (map.at(x, y).containsAnActor()) {
                        Location there = map.at(x, y);
                        Actor target = there.getActor();
                        if (target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                            // If there is a hostile actor, create a rectangle where the enemy is at one corner and the
                            // target is at the opposite corner. If there are any grounds that block thrown objects in
                            // this rectangle, then return a block action.
                            NumberRange x2s, y2s;
                            x2s = new NumberRange(Math.min(here.x(), there.x()), Math.abs(here.x() - there.x()) + 1);
                            y2s = new NumberRange(Math.min(here.y(), there.y()), Math.abs(here.y() - there.y()) + 1);
                            for (int x2 : x2s) {
                                for (int y2 : y2s) {
                                    if (map.at(x2, y2).getGround().blocksThrownObjects())
                                        return new BlockAction();
                                }
                            }
                            // Otherwise nothing blocking the attack, return an attack.
                            return new AttackAction(target, "N/A");
                        }
                    }
                }
            }
        }

        // If no hostile adjacent actor was found, return null
        return null;
    }
}
