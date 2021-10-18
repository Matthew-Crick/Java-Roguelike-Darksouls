package game.weapons;

import game.enums.Abilities;

/**
 * Abstract for the bow weapon type.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public abstract class Bow extends GameWeaponItem {

    /**
     * Constructor.
     * @param name Name of the bow
     * @param displayChar Display character of the bow
     * @param damage Damage of the bow
     * @param verb Verb to use for this bow
     * @param hitRate The probability/chance to hit target
     */
    public Bow(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        // Bows have ranged critical passive
        this.addCapability(Abilities.RANGED_WEAPON);
        this.addCapability(Abilities.RANGED_CRITICAL_PASSIVE);
    }
}
