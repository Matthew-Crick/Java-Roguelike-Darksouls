package game.interfaces;

/**
 * A contract for consumable items and users of consumable items
 * The instance can be an Actor or Item.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public interface Consumable {
    /**
     * Increase health for current instance.
     * By default, it cannot increase health.
     *
     * @param percentOfMaxHealth The percentage of max health to increase health by
     * @return Transaction status. True if addition successful, otherwise False.
     */
    default boolean addHealth(int percentOfMaxHealth) {
        return false;
    }

    /**
     * Reduce charges of consumable
     * By default, an instance cannot have its charges reduced
     *
     * @return Transaction status. True if subtraction successful, otherwise False.
     */
    default boolean reduceCharges() {
        return false;
    }
}
