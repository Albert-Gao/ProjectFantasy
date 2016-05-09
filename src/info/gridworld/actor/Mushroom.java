package info.gridworld.actor;

import java.awt.Color;

/**
 * A <code>Mushroom</code> is an actor that does nothing. It is commonly used to
 * block other actors from moving. <br />
 * The API of this class is testable on the AP CS A and AB exams.
 */

public class Mushroom extends Actor
{
    
    
    private static final Color DEFAULT_COLOR = Color.BLACK;

    /**
     * Constructs a black rock.
     */
    public Mushroom()
    {
        setColor(DEFAULT_COLOR);
    }

    /**
     * Constructs a rock of a given color.
     * @param rockColor the color of this rock
     */
    public Mushroom(Color rockColor)
    {
        setColor(rockColor);
    }

    /**
     * Overrides the <code>act</code> method in the <code>Actor</code> class
     * to do nothing.
     */
    public void act()
    {
    }
}
