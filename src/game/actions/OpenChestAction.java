package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Chest;
import game.enemies.Mimic;
import game.items.TokenOfSoul;

import java.util.Random;

/**
 * Action for opening a chest
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 13/10/2021
 */
public class OpenChestAction extends Action {

    /**
     * The chest which gets opened
     */
    private Chest chest;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Constructor.
     * @param chest The chest which gets opened.
     */
    public OpenChestAction(Chest chest) {
        this.chest = chest;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Get the location of the chest
        Location chestLocation = chest.getChestLocation();
        // Remove the chest
        map.removeActor(chest);

        // There is a 50% chance that the chest drops tokens
        if (rand.nextInt(100) < 50) {
            // Between 1 and 3 tokens will be dropped
            int numberOfTokens = rand.nextInt(3) + 1;
            for (int i = 0; i < numberOfTokens; i++) {
                chestLocation.addItem(new TokenOfSoul(100));
            }
            return actor + " opens Chest";
        } else {
            // There is a 50% chance that a mimic is spawned at the chest's location
            chestLocation.addActor(new Mimic(chestLocation));
            return "The Chest was a Mimic!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Open the Chest";
    }
}
