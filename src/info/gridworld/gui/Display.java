package info.gridworld.gui;

import java.awt.*;

/**
 * The <code>Display</code> interface contains the method needed to display a
 * grid object. <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */
public interface Display
{
    /**
     * Method invoked to draw an object.
     * @param obj object we want to draw
     * @param comp component on which to draw
     * @param g2 drawing surface
     * @param rect rectangle in which to draw
     */
    void draw(Object obj, Component c, Graphics2D g2, Rectangle rect);
}
