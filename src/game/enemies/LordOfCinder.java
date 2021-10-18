package game.enemies;

/**
 * Abstract class representing bosses in the game
 *
 * @author Joshua Nung, Matthew Crick
 * @version 1.0.0
 * @since 26/09/2021
 */
public abstract class LordOfCinder extends Enemy {

    /**
     * Constructor.
     *
     * @param name        The name of the Lord of Cinder
     * @param displayChar The character that will represent the Lord of Cinder in the display
     * @param hitPoints   The Lord of Cinder's starting hit points
     */
    public LordOfCinder(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }
}
