package game;

import edu.monash.fit2099.engine.*;
import game.actions.ResetAction;
import game.enums.Abilities;
import game.enums.Status;
import game.grounds.Bonfire;
import game.interfaces.Consumable;
import game.interfaces.Resettable;
import game.interfaces.Soul;
import game.items.EstusFlask;
import game.weapons.Broadsword;

/**
 * Class representing the Player.
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class Player extends Actor implements Soul, Resettable, Consumable {
	/**
	 * The menu/UI the player will use to choose actions
	 */
	private final Menu menu = new Menu();

	/**
	 * A bonfire manager to keep track of the bonfires in the game
	 */
	private BonfireManager bonfireManager;

	/**
	 * The location of the player before executing their turn; used for keeping track of the player's
	 * position just before a death
	 */
	private Location previousLocation;

	/**
	 * The soul count of the player
	 */
	private int soulCount = 0;

	/**
	 * Constructor.
	 *
	 * @param name        		Name to call the player in the UI
	 * @param displayChar 		Character to represent the player in the UI
	 * @param hitPoints   		Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints, BonfireManager bonfireManager) {
		// Initialise properties
		super(name, displayChar, hitPoints);
		this.bonfireManager = bonfireManager;

		// Initialise capabilities
		this.addCapability(Abilities.CAN_FALL);
		this.addCapability(Abilities.GAINS_SOULS);
		this.addCapability(Abilities.HAS_ACTION_AFTER_KILLED);
		this.addCapability(Abilities.CAN_OPEN_CHEST);
		this.addCapability(Status.HOSTILE_TO_ENEMY);

		// Initialise inventory
		this.addItemToInventory(new Broadsword());
		this.addItemToInventory(new EstusFlask());

		// Add to reset manager
		this.registerInstance();
	}

	/**
	 * Returns a collection of the Actions that the otherActor can do to the current Actor.
	 * For the player, the player makes enemies become hostile once adjacent.
	 *
	 * @param otherActor The Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        Current GameMap
	 * @return The collection of Actions
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		otherActor.removeCapability(Status.NOT_HOSTILE_TO_PLAYER);
		return super.getAllowableActions(otherActor, direction, map);
	}

	/**
	 * The play turn for the player. It uses a menu to allow the user to choose from available actions
	 *
	 * @param actions    Collection of possible Actions for the player
	 * @param lastAction The Action the player took last turn
	 * @param map        The map containing the player
	 * @param display    The I/O object to which messages may be written
	 * @return The Action to execute, as determined by the player or automatically by the game
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Check if the player has run out of HP, and if so reset the map
		if (!this.isConscious()) {
			this.addCapability(Status.PLAYER_KILLED);
			return new ResetAction(previousLocation);
		}

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// Display player information
		display.println(
				name + " (" + hitPoints + "/" + maxHitPoints
						+ "), holding " + this.getWeapon()
						+ ", " + soulCount + " souls"
		);

		// Set the previous location attribute
		previousLocation = map.locationOf(this);

		// Return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public boolean addHealth(int percentOfMaxHealth) {
		// For example if percentOfMaxHealth = 40, maxHitPoints = 200, then
		// the player will be healed for 40 * 200 / 100 = 0.4 * 200 = 80
		this.heal(Math.floorDiv(percentOfMaxHealth * maxHitPoints, 100));
		return true;
	}

	@Override
	public void transferSouls(Soul soulObject) {
		// Transfer souls to soulObject and set own soul count to 0
		soulObject.addSouls(soulCount);
		soulCount = 0;
	}

	@Override
	public boolean addSouls(int souls) {
		soulCount += souls;
		return true;
	}

	@Override
	public void resetInstance() {
		// Restore to full health
		hitPoints = maxHitPoints;

		// If the player was killed (and not resting at the bonfire)
		if (this.hasCapability(Status.PLAYER_KILLED)) {

			// Get the last interacted with bonfire
			Bonfire lastBonfire = bonfireManager.getLastBonfire();
			Location bonfireLocation = lastBonfire.getBonfireLocation();

			GameMap gameMap = bonfireLocation.map();
			// Check the bonfire location and see if there is an actor there
			if (gameMap.isAnActorAt(bonfireLocation)) {
				// If the player is not at the bonfire location, there is another actor at
				// the bonfire location, so remove them so we can move the player there
				if (!gameMap.locationOf(this).equals(bonfireLocation)) {
					gameMap.removeActor(gameMap.getActorAt(bonfireLocation));
				}
			}
			// Move the player to last interacted with bonfire
			gameMap.moveActor(this, bonfireLocation);
		}
	}

	@Override
	public boolean isExist() {
		// The player is kept in the game when resetting
		return true;
	}
}
