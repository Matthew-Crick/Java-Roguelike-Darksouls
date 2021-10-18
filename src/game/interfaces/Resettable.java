package game.interfaces;

import game.ResetManager;

/**
 * An interface for resettable.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */

public interface Resettable {
    /**
     * Allows any classes that use this interface to reset abilities, attributes, and items.
     */
    void resetInstance();

    /**
     * A useful method to clean up the list of instances in the ResetManager class
     * @return the existence of the instance in the game.
     * True to keep it permanent, or false if instance needs to be removed from the reset list.
     */
    boolean isExist();

    /**
     * A default interface method that register current instance to the Singleton manager.
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}
