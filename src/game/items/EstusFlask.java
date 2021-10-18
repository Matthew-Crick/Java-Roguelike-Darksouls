package game.items;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import game.actions.DrinkAction;
import game.interfaces.Consumable;
import game.interfaces.Resettable;

import java.util.List;

/**
 * Class for the Cinder of Yhorm The Giant item
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class EstusFlask extends Item implements Consumable, Resettable {

    /**
     * The number of charges the estus flask has
     */
    private int charges = 3;

    /**
     * The maximum number of charges the estus flask has
     */
    private int maxCharges = 3;

    /**
     * The percentage of max health the estus flask heals for
     */
    private int percentOfMaxHealth = 40;

    /**
     * Constructor.
     */
    public EstusFlask() {
        super("Estus Flask", 'e', false);
        // Add to reset manager
        this.registerInstance();
    }

    /**
     * Returns a DrinkAction
     *
     * @return a DrinkAction
     */
    @Override
    public List<Action> getAllowableActions() {
        this.allowableActions.clear();
        this.allowableActions.add(new DrinkAction(this, charges, percentOfMaxHealth));
        return super.getAllowableActions();
    }

    @Override
    public boolean reduceCharges() {
        charges -= 1;
        return true;
    }

    /**
     * To display the number of charges of the estus flask
     *
     * @return A string representing the number of charges of the estus flask
     */
    @Override
    public String toString() {
        return name + " (" + charges + "/" + maxCharges + ")";
    }

    @Override
    public void resetInstance() {
        // Reset charges to full
        charges = maxCharges;
    }

    @Override
    public boolean isExist() {
        // The estus flask is kept in the game when resetting
        return true;
    }
}
