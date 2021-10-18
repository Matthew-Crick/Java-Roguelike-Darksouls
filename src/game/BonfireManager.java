package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.MoveActorAction;
import game.enums.Status;
import game.grounds.Bonfire;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for bonfire manager to keep track of bonfires in the game
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 11/10/2021
 */
public class BonfireManager {
    /**
     * a HashMap to store bonfires, using the names of the bonfires as keys
     */
    private HashMap<String, Bonfire> bonfireHashMap;

    /**
     * Constructor.
     */
    public BonfireManager() {
        bonfireHashMap = new HashMap<String, Bonfire>();
    }

    /**
     * Adds a bonfire to the hash map of bonfires
     * @param bonfire The bonfire to add
     */
    public void addBonfire(Bonfire bonfire) {
        bonfireHashMap.put(bonfire.getBonfireName(), bonfire);
    }

    /**
     * Goes through the bonfire hash map and sets a bonfire with the LAST_BONFIRE capability
     * @param bonfire The bonfire to indicate as being the last interacted with bonfire
     */
    public void setLastBonfire(Bonfire bonfire) {
        String bonfireName = bonfire.getBonfireName();
        for (Map.Entry<String, Bonfire> entry : bonfireHashMap.entrySet()) {
            if (bonfireName.equals(entry.getKey())) {
                entry.getValue().addCapability(Status.LAST_BONFIRE);
            } else {
                entry.getValue().removeCapability(Status.LAST_BONFIRE);
            }
        }
    }

    /**
     * Goes through the bonfire hash map and returns the bonfire with the LAST_BONFIRE capability
     * @return The bonfire which has been indicated as being the last interacted with bonfire
     */
    public Bonfire getLastBonfire() {
        for (Map.Entry<String, Bonfire> entry : bonfireHashMap.entrySet()) {
            if (entry.getValue().hasCapability(Status.LAST_BONFIRE)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Goes through the bonfire hash map and returns a list of teleport actions to lit bonfires, other than
     * the bonfire passed in as a parameter
     * @param bonfire The bonfire to teleport from, so we don't need a teleport action to this bonfire
     * @return A list of MoveActorAction actions
     */
    public Actions getBonfireMoveActions(Bonfire bonfire) {
        Actions actions = new Actions();
        for (Map.Entry<String, Bonfire> entry : bonfireHashMap.entrySet()) {
            if (!entry.getValue().equals(bonfire) && entry.getValue().hasCapability(Status.BONFIRE_LIT)) {
               actions.add(new MoveActorAction(entry.getValue().getBonfireLocation(), "to " + entry.getKey()));
            }
        }
        return actions;
    }
}
