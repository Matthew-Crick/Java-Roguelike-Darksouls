package game.weapons;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.actions.SwapWeaponAction;
import game.enums.Abilities;
import game.enums.Status;

import java.util.Random;

/**
 * Abstract class representing weapon items for the game.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public abstract class GameWeaponItem extends WeaponItem {

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Constructor.
     *
     * @param name        Name of the weapon
     * @param displayChar Character to use for display when weapon is on the ground
     * @param damage      Amount of damage this weapon does
     * @param verb        Verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     The probability/chance to hit the target
     */
    public GameWeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
    }

    @Override
    public int damage() {
        int overallDamage = super.damage();

        // Chance for double damage if the weapon has the critical passive
        if (this.hasCapability(Abilities.CRITICAL_PASSIVE)) {
            if (rand.nextInt(100) < 20) {
                overallDamage *= 2;
            }
        }

        // Chance for double damage if the weapon has the ranged critical passive
        if (this.hasCapability(Abilities.RANGED_CRITICAL_PASSIVE)) {
            if (rand.nextInt(100) < 15) {
                overallDamage *= 2;
            }
        }

        // Half damage if weapon has the dullness passive
        if (this.hasCapability(Status.DULLNESS)) {
            overallDamage = Math.floorDiv(overallDamage, 2);
        }

        return overallDamage;
    }

    @Override
    public int chanceToHit() {
        int overallChanceToHit = super.chanceToHit();

        // If the weapon has the Ember Form status, then the hit rate increases by 30%
        if (this.hasCapability(Status.EMBER_FORM)) {
            overallChanceToHit += 30;
        }
        return overallChanceToHit;
    }

    /**
     * Override the getDropAction method with null so that weapons can't be dropped in this game
     *
     * @param actor An actor that will interact with this item
     * @return null because weapons can't be dropped in this game
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }

    /**
     * Override the getPickUpAction with SwapWeaponAction as weapons are swapped in this game
     *
     * @param actor An actor that will interact with this item
     * @return A SwapWeaponAction as weapons are swapped in this game
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return new SwapWeaponAction(this);
    }
}
