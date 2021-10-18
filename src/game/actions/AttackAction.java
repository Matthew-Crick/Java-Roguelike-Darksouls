package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.enums.Abilities;
import game.items.TokenOfSoul;

/**
 * Special Action for attacking other Actors.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param target The Actor to attack
	 * @param direction The direction of the attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// Get the weapon
		Weapon weapon = actor.getWeapon();

		// Use the hit rate to determine if the hit will be successful
		if (!(rand.nextInt(100) < weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		// Get the weapon damage
		int damage = weapon.damage();

		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
		target.hurt(damage);
		if (!target.isConscious()) {
			Actions dropActions = new Actions();
			// Drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);

			// For the mimic, drop between 1 and 3 tokens of souls each worth 100 souls at the location of
			// the mimic
			if (target.hasCapability(Abilities.DROPS_SOULS_ON_DEATH)) {
				int numberOfTokens = rand.nextInt(3) + 1;
				for (int i = 0; i < numberOfTokens; i++) {
					map.locationOf(target).addItem(new TokenOfSoul(100));
				}
			}

			// Transfer souls to the actor if the actor can gain souls
			if (actor.asSoul() != null && target.asSoul() != null && actor.hasCapability(Abilities.GAINS_SOULS)) {
				target.asSoul().transferSouls(actor.asSoul());
			}

			// Remove actor if the actor should be removed immediately upon death
			if (!target.hasCapability(Abilities.HAS_ACTION_AFTER_KILLED)) {
				map.removeActor(target);
				result += System.lineSeparator() + target + " is killed";
			}
		}
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}
}
