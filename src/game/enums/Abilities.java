package game.enums;

/**
 * An enum class that represents an ability/permanent trait of an Actor, Item, or Ground.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public enum Abilities {
    CRITICAL_PASSIVE,           // For weapons with the critical/chance to deal double damage passive
    GAINS_SOULS,                // For actors that can gain souls
    BLOCKED_BY_FLOORS,          // For actors that cannot step onto floors
    CAN_FALL,                   // For actors that can fall into valleys
    HAS_ACTION_AFTER_KILLED,    // For actors that are not immediately removed from the game on a turn where they die
    DOES_NOT_WANDER,            // For actors that do not have a wander behaviour
    RANGED_WEAPON,              // For weapons that are ranged
    RANGED_CRITICAL_PASSIVE,    // For ranged weapons that have the critical passive
    CAN_OPEN_CHEST,             // For actors that can open chests
    DROPS_SOULS_ON_DEATH        // For actors that drop tokens of souls on death
}
