package game.weapons;

import game.enums.Abilities;

/**
 * Abstract class representing swords for the game.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public abstract class Sword extends GameWeaponItem {

    /**
     * Constructor.
     *
     * @param name        Name of the sword
     * @param displayChar Character to use for display when sword is on the ground
     * @param damage      Amount of damage this sword does
     * @param verb        Verb to use for this sword, e.g. "hits", "zaps"
     * @param hitRate     The probability/chance to hit the target
     */
    public Sword(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        // Swords have the critical passive
        this.addCapability(Abilities.CRITICAL_PASSIVE);
    }
}
