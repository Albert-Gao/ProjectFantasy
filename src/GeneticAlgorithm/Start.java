package GeneticAlgorithm;

import info.gridworld.actor.*;
import info.gridworld.actor.Creature;
import info.gridworld.actor.Mushroom;
import info.gridworld.actor.Strawberry;

/**
 * This class runs a world that contains crab critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class Start
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Mushroom());
        world.add(new Mushroom());
        world.add(new Mushroom());
        world.add(new Mushroom());
        world.add(new Strawberry());
        world.add(new Strawberry());
        world.add(new Strawberry());
        world.add(new Strawberry());
        world.add(new info.gridworld.actor.Creature());
        world.add(new Creature());
        world.show();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.step();
        world.getParentFrame().repaint();
        world.step();
        world.step();
    }
}