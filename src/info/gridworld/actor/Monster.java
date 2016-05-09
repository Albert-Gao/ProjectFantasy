package info.gridworld.actor;

import java.util.ArrayList;

/**
 * A <code>Monster</code> is an actor that moves through its world, processing
 * other actors in some way and then moving to a new location. Define your own
 * critters by extending this class and overriding any methods of this class
 * except for <code>act</code>. When you override these methods, be sure to
 * preserve the postconditions. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class Monster extends Actor {
    /**
     * A critter acts by getting a list of other actors, processing that list,
     * getting locations to move to, selecting one of them, and moving to the
     * selected location.
     */
    public void act() {
        if (getGrid() == null)
            return;
        ArrayList<Actor> actors = getActors();
        boolean isKilled = processActors(actors);
        if (!isKilled) {
            randomAct();
        }
    }

    /**
     * Processes the elements of <code>actors</code>. New actors may be added
     * to empty locations. Implemented to "eat" (i.e. remove) selected actors
     * that are not rocks or critters. Override this method in subclasses to
     * process actors in a different way. <br />
     * Postcondition: (1) The state of all actors in the grid other than this
     * critter and the elements of <code>actors</code> is unchanged. (2) The
     * location of this critter is unchanged.
     *
     * @param actors the actors to be processed
     */
    public boolean processActors(ArrayList<Actor> actors) {
        boolean mark = false;
        for (Actor a : actors) {
            if (a instanceof Creature) {
                a.setEnergyLevel(0);
                moveTo(a.getLocation());
                mark = true;
                break;
            }
        }
        return mark;
    }
}
