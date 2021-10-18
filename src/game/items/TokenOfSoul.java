package game.items;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;
import game.actions.RetrieveSoulsAction;
import game.interfaces.Soul;

/**
 * Class for the Token of soul item
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public class TokenOfSoul extends Item implements Soul {

    /**
     * The soul count of the token
     */
    private int soulCount = 0;

    /**
     * Constructor.
     */
    public TokenOfSoul() {
        super("Token of souls", '$', true);
    }

    /**
     * Constructor for a particular number of souls the token should contain.
     */
    public TokenOfSoul(int soulCount) {
        super("Token of souls", '$', true);
        this.soulCount = soulCount;
    }

    /**
     * Override the getDropAction method with null so that token can't be dropped
     *
     * @param actor An actor that will interact with this item
     * @return null because tokens can't be dropped in this game
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }

    /**
     * Override the getPickUpAction with RetrieveSoulsAction
     *
     * @param actor An actor that will interact with this item
     * @return a RetrieveSoulsAction
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return new RetrieveSoulsAction(this);
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulCount);
    }

    @Override
    public boolean addSouls(int souls) {
        soulCount += souls;
        return true;
    }

    /**
     * To display the amount of souls the token has
     *
     * @return A string representing the amount of souls the token has
     */
    @Override
    public String toString() {
        return "lost souls(" + soulCount + " souls)";
    }
}

