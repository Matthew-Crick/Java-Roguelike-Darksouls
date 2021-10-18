package game.actions;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

/**
 * Action for charging a weapon.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class ChargeAction extends WeaponAction {

    /**
     * Constructor
     *
     * @param weaponItem The weapon to charge
     */
    public ChargeAction(WeaponItem weaponItem) {
        super(weaponItem);
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Set charging status of weapon
        weapon.removeCapability(Status.UNCHARGED);
        weapon.addCapability(Status.START_CHARGING);
        return actor + " begins charging " + weapon;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " charges " + weapon;
    }
}
