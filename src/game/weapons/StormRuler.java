package game.weapons;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.WeaponAction;
import game.actions.ChargeAction;
import game.actions.WindSlashAction;
import game.enums.Status;
import game.interfaces.Resettable;

import java.util.List;

/**
 * Class for the Storm Ruler weapon
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class StormRuler extends Sword implements Resettable {

    /**
     * The number of charges the Storm Ruler has
     */
    private int charges = 0;

    /**
     * The number of charges required for Storm Ruler to be fully charged
     */
    private int maxCharges = 3;

    /**
     * Constructor.
     */
    public StormRuler() {
        super("Storm Ruler", '7', 70, "attacks", 60);
        // Initialise the Storm Ruler as being uncharged
        this.addCapability(Status.UNCHARGED);
        // Add to reset manager
        this.registerInstance();
    }

    /**
     * Returns a ChargeAction if Storm Ruler is uncharged and in the player's inventory
     *
     * @return A ChargeAction if Storm Ruler is uncharged and in the player's inventory
     */
    @Override
    public List<Action> getAllowableActions() {
        // Remove ChargeActions from previous turns
        this.allowableActions.clear();
        // Only allow a ChargeAction if the Storm Ruler is uncharged and is in the player's inventory
        if (this.hasCapability(Status.UNCHARGED) && this.hasCapability(Status.PICKED_UP)) {
            this.allowableActions.add(new ChargeAction(this));
        }
        return super.getAllowableActions();
    }

    /**
     * Inform Storm Ruler of the passage of time. Has its own particular logic for charging.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        // If uncharged, Storm Ruler has 0 charges
        if (this.hasCapability(Status.UNCHARGED)) {
            charges = 0;
        }
        // If charging, then increase the number of charges by 1
        if (this.hasCapability(Status.CHARGING)) {
            if (charges < maxCharges) {
                charges++;
                // If reached maximum number of charges, then fully charged
                if (charges == maxCharges) {
                    this.removeCapability(Status.CHARGING);
                    this.addCapability(Status.FULLY_CHARGED);
                }
            }
        }
        // Once charging has been initiated by ChargeAction, set up CHARGING status for next turn
        if (this.hasCapability(Status.START_CHARGING)) {
            this.removeCapability(Status.START_CHARGING);
            this.addCapability(Status.CHARGING);
        }
        // If Storm Ruler is not uncharged or fully charged, then the user cannot attack; otherwise they can
        if (!(this.hasCapability(Status.UNCHARGED) || this.hasCapability(Status.FULLY_CHARGED))) {
            actor.addCapability(Status.DISARMED);
        } else {
            actor.removeCapability(Status.DISARMED);
        }
        super.tick(currentLocation, actor);
    }

    /**
     * Used to potentially return a WindSlashAction for the user
     *
     * @param target The target actor to be attacked
     * @param direction The direction of target, e.g. "north"
     * @return A WindSlashAction if appropriate, otherwise null
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        // This weapon has dullness by default
        this.addCapability(Status.DULLNESS);
        // When attacking an enemy weak to Storm Ruler i.e. Yhorm The Giant
        if (target.hasCapability(Status.WEAK_TO_STORMRULER)) {
            // Remove dullness for enemy weak to Storm Ruler i.e. Yhorm The Giant
            this.removeCapability(Status.DULLNESS);
            // Allow Wind Slash if fully charged and attacking enemy weak to Storm Ruler i.e. Yhorm The Giant
            if (this.hasCapability(Status.FULLY_CHARGED)) {
                return new WindSlashAction(this, target, direction);
            }
        }
        return null;
    }

    /**
     * To display the current charging state of Storm Ruler
     *
     * @return A string representing the current charging state of Storm Ruler
     */
    @Override
    public String toString() {
        String chargeState = "";
        if (this.hasCapability(Status.START_CHARGING) || this.hasCapability(Status.CHARGING)) {
            chargeState = "(CHARGING)";
        }
        if (this.hasCapability(Status.FULLY_CHARGED)) {
            chargeState = "(FULLY CHARGED)";
        }

        return name + chargeState + " (" + charges + "/" + maxCharges + ")";
    }

    @Override
    public void resetInstance() {
        // Reset charges
        charges = 0;
        // Keep track of if in the player's inventory
        boolean stillInInventory = this.hasCapability(Status.PICKED_UP);
        // Reset statuses
        for (Status status : Status.values()) {
            this.removeCapability(status);
		}
        // Initialise as uncharged
        this.addCapability(Status.UNCHARGED);
        // If in the player's inventory, add the PICKED_UP status
        if (stillInInventory) {
            this.addCapability(Status.PICKED_UP);
        }
    }

    @Override
    public boolean isExist() {
        // The weapon is kept in the game when resetting
        return true;
    }
}
