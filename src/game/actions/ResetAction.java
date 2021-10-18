package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.BonfireManager;
import game.ResetManager;
import game.enums.Status;
import game.grounds.Bonfire;
import game.items.TokenOfSoul;

/**
 * Action for resetting of the map. It covers two possibilities: the player resting at
 * the bonfire, and the player being killed.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class ResetAction extends Action {

    /**
     * The location of the player before their death, used for creating a Token of soul
     */
    private Location previousLocation = null;

    /**
     * The bonfire which gets lit.
     */
    private Bonfire bonfire;

    /**
     * A bonfire manager to keep track of the bonfires in the game
     */
    private BonfireManager bonfireManager;

    /**
     * Constructor. This is a constructor for resting at a bonfire, whereby the
     * player does not need to have their previous location recorded. The bonfire
     * and bonfire manager are passed in.
     */
    public ResetAction(Bonfire bonfire, BonfireManager bonfireManager) {
        this.bonfire = bonfire;
        this.bonfireManager = bonfireManager;
    }

    /**
     * Constructor. This is for when the player dies, in which case they need to have
     * their previous location indicated to create a Token of soul
     *
     * @param previousLocation The location of the player before their death
     */
    public ResetAction(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Run the reset manager to reset all registered Resettables
        ResetManager.getInstance().run();

        // If the player has died i.e. not resting at a bonfire, then a token of soul is
        // created at the location before the player's death
        if (actor.hasCapability(Status.PLAYER_KILLED)) {
            actor.removeCapability(Status.PLAYER_KILLED);
            if (previousLocation != null) {
                TokenOfSoul aTokenOfSoul = new TokenOfSoul();
                // Transfer the player's souls to the token
                if (actor.asSoul() != null && aTokenOfSoul.asSoul() != null) {
                    actor.asSoul().transferSouls(aTokenOfSoul.asSoul());
                }
                previousLocation.addItem(aTokenOfSoul);
            }
            // String to display upon player's death
            return "          _______             ______  _________ _______  ______  \n" +
                    "|\\     /|(  ___  )|\\     /|  (  __  \\ \\__   __/(  ____ \\(  __  \\ \n" +
                    "( \\   / )| (   ) || )   ( |  | (  \\  )   ) (   | (    \\/| (  \\  )\n" +
                    " \\ (_) / | |   | || |   | |  | |   ) |   | |   | (__    | |   ) |\n" +
                    "  \\   /  | |   | || |   | |  | |   | |   | |   |  __)   | |   | |\n" +
                    "   ) (   | |   | || |   | |  | |   ) |   | |   | (      | |   ) |\n" +
                    "   | |   | (___) || (___) |  | (__/  )___) (___| (____/\\| (__/  )\n" +
                    "   \\_/   (_______)(_______)  (______/ \\_______/(_______/(______/";
        }

        // If the player has not died, then they are resting at the bonfire to reset
        bonfireManager.setLastBonfire(bonfire);
        return "Rested at " + bonfire.getBonfireName();
    }

    @Override
    public String menuDescription(Actor actor) {
        // If the player has not died, then they can reset at the bonfire
        return "Rest at " + bonfire.getBonfireName();
    }
}
