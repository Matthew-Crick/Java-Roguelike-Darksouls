package game;

import game.interfaces.Resettable;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private List<Resettable> resettableList;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if (instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }

    /**
     * Reset the game by traversing through all the list
     */
    public void run() {
        // Reset each instance in resettableList
        for (int i = 0; i < resettableList.size(); i++) {
            resettableList.get(i).resetInstance();
        }
        // Clean up by removing the Resettables that do not need to be kept
        cleanUp();
    }

    /**
     * Add the Resettable instance to the list
     *
     * @param resettable The interface instance
     */
    public void appendResetInstance(Resettable resettable){
        resettableList.add(resettable);
    }

    /**
     * Clean up instances (actor, item, or ground) that don't exist anymore in the map
     */
    private void cleanUp() {
        // resettablesToBeRemoved will hold the resettables to be removed
        List<Resettable> resettablesToBeRemoved = new ArrayList<Resettable>();
        // Iterate through resettableList, and for each Resettable that has isExist() returning false,
        // add it to resettablesToBeRemoved
        for (int i = 0; i < resettableList.size(); i++) {
            if (!resettableList.get(i).isExist()) {
                resettablesToBeRemoved.add(resettableList.get(i));
            }
        }
        // Remove all Resettables inside resettablesToBeRemoved from resettableList
        resettableList.removeAll(resettablesToBeRemoved);
    }
}
