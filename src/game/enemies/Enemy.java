package game.enemies;

import edu.monash.fit2099.engine.*;
import game.behaviours.FollowBehaviour;
import game.actions.DieAction;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.ResetPositionBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Resettable;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class representing enemies in the game
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public abstract class Enemy extends Actor implements Soul, Resettable {

    /**
     * The initial location of the enemy to reset to upon death
     */
    Location initialLocation;

    /**
     * List of behaviours for enemy to go through during their turn
     */
    ArrayList<Behaviour> behaviours = new ArrayList<>();

    /**
     * A follow behaviour for potentially following the player
     */
    private FollowBehaviour followBehaviour = null;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * The soul count of the enemy
     */
    int soulCount;

    /**
     * Constructor.
     *
     * @param name        Name of the enemy
     * @param displayChar The character that will represent the enemy in the display
     * @param hitPoints   The enemy's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        // Initialise properties
        super(name, displayChar, hitPoints);

        // Initialise capabilities
        this.addCapability(Abilities.BLOCKED_BY_FLOORS);
        this.addCapability(Status.NOT_HOSTILE_TO_PLAYER);

        // Initialise behaviours
        behaviours.add(new AttackBehaviour());
        behaviours.add(new WanderBehaviour());

        // Add to reset manager
        this.registerInstance();
    }

    /**
     * To display the enemy's health and weapon
     *
     * @return A string representing the enemy's health and weapon
     */
    @Override
    public String toString() {
        return name + "(" + hitPoints + "/" + maxHitPoints + ")(" + this.getWeapon() + ")";
    }

    /**
     * Returns a collection of the Actions that the otherActor can do to the current Actor.
     * It returns Actions for when the other actor is hostile to an enemy, i.e. the player
     *
     * @param otherActor The Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        Current GameMap
     * @return The collection of Actions
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();

        // It can be attacked only by a hostile actor
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            // Start following the other actor if it is not already being followed
            if (followBehaviour == null) {
                followBehaviour = new FollowBehaviour(otherActor);
                behaviours.add(followBehaviour);
            }

            // If the other actor is not disarmed, create attack actions
            if (!otherActor.hasCapability(Status.DISARMED)) {
                // If the other actor has an active skill, create the action for it
                Weapon weapon = otherActor.getWeapon();
                WeaponAction weaponAction = weapon.getActiveSkill(this, direction);
                if (weaponAction != null) {
                    actions.add(weaponAction);
                }
                // Create an attack action for the other actor
                actions.add(new AttackAction(this, direction));
            }
        }
        return actions;
    }

    /**
     * The play turn for the enemy. It is handled using behaviours which have their own particular logic.
     *
     * @param actions    collection of possible Actions for the enemy
     * @param lastAction The Action this enemy took last turn
     * @param map        the map containing the player
     * @param display    the I/O object to which messages may be written
     * @return The Action to execute, as determined by the enemy's behaviours
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // If the enemy has run out of health at the start of the turn, then it dies
        if (!this.isConscious()) {
            return new DieAction();
        }

        // For the undead, if it is not attacking/following the player, it may randomly die
        if (this.hasCapability(Status.DIES_RANDOMLY) && this.hasCapability(Status.NOT_HOSTILE_TO_PLAYER)) {
            if (rand.nextInt(100) < 10) {
                return new DieAction();
            }
        }

        // For Yhorm, when stunned, unable to do an action so return a DoNothingAction
        if (this.hasCapability(Status.STUNNED)) {
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }

        // For enemies that have a ranged attack and not yet following a target
        if (this.hasCapability(Abilities.RANGED_WEAPON) && followBehaviour == null) {
            Location here = map.locationOf(this);
            // Create a box that goes 3 squares in each direction from the enemy's location
            NumberRange xs, ys;
            xs = new NumberRange(here.x() - 3, 7);
            ys = new NumberRange(here.y() - 3, 7);
            // Check to see if there is a hostile actor in this box, and if so, start following
            for (int x : xs) {
                for (int y : ys) {
                    if (map.at(x, y).containsAnActor()) {
                        Actor target = map.at(x, y).getActor();
                        if (target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                            followBehaviour = new FollowBehaviour(target);
                            behaviours.add(followBehaviour);
                        }
                    }
                }
            }
        }

        // Loop through all behaviours
        for (Behaviour Behaviour : behaviours) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }

        // Otherwise, do nothing
        return new DoNothingAction();
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulCount);
    }

    @Override
    public void resetInstance() {
        // Restore to full health
        hitPoints = maxHitPoints;

        // Reset statuses
        for (Status status : Status.values()) {
            this.removeCapability(status);
        }

        // Initialise statuses for enemies
        this.addCapability(Status.NOT_HOSTILE_TO_PLAYER);
        this.addCapability(Status.ENEMY_POSITION_TO_BE_RESET);

        // Initialise behaviours for enemies
        followBehaviour = null;
        behaviours.clear();
        behaviours.add(new ResetPositionBehaviour(initialLocation));
        behaviours.add(new AttackBehaviour());
        behaviours.add(new WanderBehaviour());
    }

    @Override
    public boolean isExist() {
        return true;
    }
}
