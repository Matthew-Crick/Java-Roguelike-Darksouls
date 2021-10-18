package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.BonfireManager;
import game.enums.Status;
import game.grounds.Bonfire;

/**
 * Action for lighting a bonfire
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 11/10/2021
 */
public class LightAction extends Action {
    /**
     * The bonfire which gets lit.
     */
    private Bonfire bonfire;

    /**
     * A bonfire manager to keep track of the bonfires in the game
     */
    private BonfireManager bonfireManager;

    /**
     * Constructor.
     * @param bonfire The bonfire which gets lit.
     * @param bonfireManager A bonfire manager to keep track of the bonfires in the game
     */
    public LightAction(Bonfire bonfire, BonfireManager bonfireManager) {
        this.bonfire = bonfire;
        this.bonfireManager = bonfireManager;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Indicate that the bonfire has been lit, and set it as being the last interacted
        // with bonfire
        bonfire.addCapability(Status.BONFIRE_LIT);
        bonfireManager.setLastBonfire(bonfire);
        return actor + " lit " + bonfire.getBonfireName();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " lights " + bonfire.getBonfireName();
    }
}
