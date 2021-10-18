package game.grounds;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.BonfireManager;
import game.actions.LightAction;
import game.actions.ResetAction;
import game.enums.Status;

/**
 * A class that represents a bonfire to rest at.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Bonfire extends Ground {
    /**
     * The location of the bonfire
     */
    private Location bonfireLocation;

    /**
     * The name of the bonfire
     */
    private String bonfireName;

    /**
     * A bonfire manager to keep track of the bonfires in the game
     */
    private BonfireManager bonfireManager;

    /**
     * Constructor.
     */
    public Bonfire(String bonfireName, Location bonfireLocation) {
        super('B');
        this.bonfireName = bonfireName;
        this.bonfireLocation = bonfireLocation;
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        // If the bonfire hasn't been lit yet, return a light action
        if (!this.hasCapability(Status.BONFIRE_LIT)) {
            return new Actions(new LightAction(this, this.bonfireManager));
        } else {
            // otherwise return a rest actions, as well as actions for teleporting to other lit bonfires
            Actions actions = new Actions(new ResetAction(this, this.bonfireManager));
            actions.add(bonfireManager.getBonfireMoveActions(this));
            return actions;
        }
    }

    /**
     * Setter for the bonfire manager
     * @param bonfireManager The bonfire manager to keep track of bonfires
     */
    public void setBonfireManager(BonfireManager bonfireManager) {
        this.bonfireManager = bonfireManager;
        this.bonfireManager.addBonfire(this);
    }

    /**
     * Getter for name of bonfire
     * @return a String representing the name of the bonfire
     */
    public String getBonfireName() {
        return bonfireName;
    }

    /**
     * Getter for the bonfire's location
     * @return a Location representing the location of the bonfire
     */
    public Location getBonfireLocation() {
        return bonfireLocation;
    }
}
