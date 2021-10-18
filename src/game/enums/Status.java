package game.enums;

/**
 * An enum class that represents `buffs` or `debuffs`.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public enum Status {
    HOSTILE_TO_ENEMY,           // For actors when hostile to enemies
    NOT_HOSTILE_TO_PLAYER,      // For actors when not hostile to player
    DIES_RANDOMLY,              // For actors that may die randomly during a turn
    ENEMY_POSITION_TO_BE_RESET, // For enemies when their position is to be reset, i.e. not undead
    PLAYER_KILLED,              // For when the player has been killed
    // States to indicate the charging status of Storm Ruler
    UNCHARGED, START_CHARGING, CHARGING, FULLY_CHARGED,
    PICKED_UP,                  // For items to indicate if in an inventory
    DISARMED,                   // For actors that are unable to attack during a turn (due to charging)
    STUNNED,                    // For actors that are unable to attack during a turn (due to being stunned)
    DULLNESS,                   // For weapons with the dullness/only dealing half damage passive
    WEAK_TO_STORMRULER,         // For actors that are weak to the Storm Ruler
    EMBER_FORM,                 // For weapons that have been activated with the Ember Form boost
    BONFIRE_LIT,                // For bonfires that are lit
    LAST_BONFIRE                // For the bonfire that the player has most recently interacted with
}
