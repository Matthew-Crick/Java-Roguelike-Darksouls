package game.enemies;

import edu.monash.fit2099.engine.*;
import game.behaviours.ResetPositionBehaviour;
import game.enums.Abilities;
import game.enums.Status;
import game.items.CinderOfLord;

import game.weapons.YhormsGreatMachete;

/**
 * The Yhorm The Giant boss.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class YhormTheGiant extends LordOfCinder {

    /**
     * A display specifically for events related to Yhorm
     */
    private Display display = new Display();

    /**
     * Constructor.
     *
     * @param initialLocation Yhorm's initial location to reset to upon death
     */
    public YhormTheGiant(Location initialLocation) {
        // Initialise properties
        super("Yhorm the Giant", 'Y', 500);
        this.initialLocation = initialLocation;
        this.soulCount = 5000;

        // Initialise capabilities
        this.addCapability(Abilities.HAS_ACTION_AFTER_KILLED);
        this.addCapability(Abilities.DOES_NOT_WANDER);
        this.addCapability(Status.WEAK_TO_STORMRULER);

        // Initialise inventory
        this.addItemToInventory(new YhormsGreatMachete());
        this.addItemToInventory(new CinderOfLord("Cinder of Yhorm the Giant"));

        // Yhorm resets position on reset
        behaviours.add(0, new ResetPositionBehaviour(initialLocation));
    }

    /**
     * The play turn for Yhorm. Yhorm has his special extra logic compared to regular enemies
     *
     * @param actions    collection of possible Actions for Yhorm
     * @param lastAction The Action Yhorm took last turn
     * @param map        the map containing Yhorm
     * @param display    the I/O object to which messages may be written
     * @return The Action to execute, as determined by the Yhorm's behaviours
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // When Yhorm reaches below half health for the first time, activate Ember Form on his weapon
        // and display a message
        if (!this.hasCapability(Status.EMBER_FORM) && this.hitPoints < Math.floorDiv(this.maxHitPoints, 2)) {
            for (Item item : inventory) {
                if (item.asWeapon() != null) {
                    item.addCapability(Status.EMBER_FORM);
                }
            }
            display.println("Raargh~");
            display.println(this + " enters Ember Form!");
        }

        // A message to display when Yhorm is defeated
        if (!this.isConscious()) {
            String victoryString = " _        _______  _______  ______     _______  _______   \n" +
                    "( \\      (  ___  )(  ____ )(  __  \\   (  ___  )(  ____ \\  \n" +
                    "| (      | (   ) || (    )|| (  \\  )  | (   ) || (    \\/  \n" +
                    "| |      | |   | || (____)|| |   ) |  | |   | || (__      \n" +
                    "| |      | |   | ||     __)| |   | |  | |   | ||  __)     \n" +
                    "| |      | |   | || (\\ (   | |   ) |  | |   | || (        \n" +
                    "| (____/\\| (___) || ) \\ \\__| (__/  )  | (___) || )        \n" +
                    "(_______/(_______)|/   \\__/(______/   (_______)|/         \n" +
                    "                                                          \n" +
                    " _______ _________ _        ______   _______  _______     \n" +
                    "(  ____ \\\\__   __/( (    /|(  __  \\ (  ____ \\(  ____ )    \n" +
                    "| (    \\/   ) (   |  \\  ( || (  \\  )| (    \\/| (    )|    \n" +
                    "| |         | |   |   \\ | || |   ) || (__    | (____)|    \n" +
                    "| |         | |   | (\\ \\) || |   | ||  __)   |     __)    \n" +
                    "| |         | |   | | \\   || |   ) || (      | (\\ (       \n" +
                    "| (____/\\___) (___| )  \\  || (__/  )| (____/\\| ) \\ \\__    \n" +
                    "(_______/\\_______/|/    )_)(______/ (_______/|/   \\__/    \n" +
                    "                                                          \n" +
                    " _______  _______  _        _        _______  _           \n" +
                    "(  ____ \\(  ___  )( \\      ( \\      (  ____ \\( (    /|    \n" +
                    "| (    \\/| (   ) || (      | (      | (    \\/|  \\  ( |    \n" +
                    "| (__    | (___) || |      | |      | (__    |   \\ | |    \n" +
                    "|  __)   |  ___  || |      | |      |  __)   | (\\ \\) |    \n" +
                    "| (      | (   ) || |      | |      | (      | | \\   |    \n" +
                    "| )      | )   ( || (____/\\| (____/\\| (____/\\| )  \\  |    \n" +
                    "|/       |/     \\|(_______/(_______/(_______/|/    )_)";
            display.println(victoryString);
        }
        // Run the same play turn logic as for all enemies
        return super.playTurn(actions, lastAction, map, display);
    }

    @Override
    public void resetInstance() {
        // Reset as with regular enemies, but Yhorm also has a unique capability WEAK_TO_STORMRULER
        super.resetInstance();
        this.addCapability(Status.WEAK_TO_STORMRULER);
    }
}
