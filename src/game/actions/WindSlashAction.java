package game.actions;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * Special Attack Action for Storm Ruler
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class WindSlashAction extends WeaponAction {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * The direction of incoming attack.
     */
    protected String direction;

    /**
     * Constructor.
     *
     * @param weaponItem The weapon to attack with
     * @param target The Actor to attack
     * @param direction The direction of the attack
     */
    public WindSlashAction(WeaponItem weaponItem, Actor target, String direction) {
        super(weaponItem);
        this.target = target;
        this.direction = direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        // For Storm Ruler, the critical passive does not apply when using the Wind Slash Action
        weapon.removeCapability(Abilities.CRITICAL_PASSIVE);
        // Wind Slash deals double damage
        int damage = 2 * weapon.damage();
        weapon.addCapability(Abilities.CRITICAL_PASSIVE);

        String result = actor + " stuns " + target + " with Wind Slash";
        target.hurt(damage);
        // Stun the target
        target.addCapability(Status.STUNNED);

        if (!target.isConscious()) {
            Actions dropActions = new Actions();
            // Drop all items
            for (Item item : target.getInventory())
                dropActions.add(item.getDropAction(actor));
            for (Action drop : dropActions)
                drop.execute(target, map);

            // Transfer souls to the actor if the actor can gain souls
            if (actor.asSoul() != null && target.asSoul() != null && actor.hasCapability(Abilities.GAINS_SOULS)) {
                target.asSoul().transferSouls(actor.asSoul());
            }

            // Remove actor if the actor should be removed immediately upon death
            if (!target.hasCapability(Abilities.HAS_ACTION_AFTER_KILLED)) {
                map.removeActor(target);
                result += System.lineSeparator() + target + " is killed";
            }
        }

        // Set the charging status of the weapon
        weapon.removeCapability(Status.FULLY_CHARGED);
        weapon.addCapability(Status.UNCHARGED);

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " stuns " + target + " at " + direction;
    }
}
