package info.gridworld.actor;

import GeneticAlgorithm.Config;
import GeneticAlgorithm.Utility;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>Creature</code> is an actor that can move and turn. It drops flowers as
 * it moves. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class Creature extends Actor {
    private double[] Chromosome = new double[Config.encode.chromosomeLenth];

    public void setChromosome(double[] chromosome) {
        Chromosome = chromosome;
    }

    public double[] getChromosome() {
        return Chromosome;
    }

    /**
     * Constructs a creature with chromosome.
     *
     * @param chromosome given Chromosome
     */
    public Creature(double[] chromosome) {
        Chromosome = chromosome;
    }

    /**
     * Constructs a creature with random chromosome.
     */
    public Creature() {
        Chromosome = Utility.randomChromosome();
        setColor(Color.RED);
    }

    /**
     * Constructs a creature of a given color.
     *
     * @param creatureColor the color for this bug
     */
    public Creature(Color creatureColor) {
        Chromosome = Utility.randomChromosome();
        setColor(creatureColor);
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    public void act() {
//        if (canMove()) {
//            move();
//        } else {
//            randomAct();
//        }
        move();
    }

    /**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn() {
        setDirection(getDirection() + Location.HALF_LEFT);
    }

    public void turn(int newDirection) {
        int nowDirection = getDirection();
        int direction = newDirection - nowDirection;
        int times = 0;
        if (direction > 0) {
            //right
            while (nowDirection < newDirection) {
                nowDirection += Location.HALF_RIGHT;
                times++;
            }
            for (int i = 0; i < times; i++) {
                setDirection(getDirection() + Location.HALF_RIGHT);
            }
        } else {
            //left
            while (nowDirection > newDirection) {
                nowDirection -= Location.HALF_LEFT;
                times++;
            }
            for (int i = 0; i < times; i++) {
                setDirection(getDirection() + Location.HALF_LEFT);
            }
        }
    }

    /**
     * Moves the bug forward, putting a flower into the location it previously
     * occupied.
     */
    public void move() {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
/*        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next)) {
            moveTo(next);
        } else {
            removeSelfFromGrid();
        }*/

        //1. getNeighbours
        //2. if no neighbour, than random
        ArrayList<Actor> nearbyActors = getActors();
        if (nearbyActors.isEmpty()) {
            randomAct();
            return;
        }

        //3. if yes, PickRightActions
        String nextMoveBasedOn = nextMove();

        //4. go to the place, if it is strawberry or mushroom than check chance
        //5. that means, hold it it hands first, if not it, next move, put them back
        switch (nextMoveBasedOn) {
            case "mushroom":
                Mushroom returnActor = getNextActor(Mushroom.class);
                boolean isEat = actOnNearby(getRightChromosome(Config.hardCode.mushroom), returnActor.getLocation());
                if (isEat) {
                    actOnMushroom(returnActor);
                }
                break;
            case "strawberry":
                Strawberry returnActor1 = getNextActor(Strawberry.class);
                boolean isEat1 = actOnNearby(getRightChromosome(Config.hardCode.strawberry), returnActor1.getLocation());
                if (isEat1) {
                    actOnStrawberry(returnActor1);
                }
                break;
            case "creature":
                Creature returnActor2 = getNextActor(Creature.class);
                boolean isEat2 = actOnNearby(getRightChromosome(Config.hardCode.creature), returnActor2.getLocation());
                if (isEat2) {
                    actOnCreature(returnActor2);
                }
                break;
            case "monster":
                Monster returnActor3 = getNextActor(Monster.class);
                boolean isEat3 = actOnNearby(getRightChromosome(Config.hardCode.monster), returnActor3.getLocation());
                if (isEat3) {
                    actOnMonster();
                } else {
                    if (getLocation() != null) {
                        randomAct();
                    }
                }
                break;
        }
    }

    private double getRightChromosome(int creatureType) {
        double returnValue = 1.0;
        switch (creatureType) {
            case Config.hardCode.creature:
                returnValue = getChromosome()[4];
                break;
            case Config.hardCode.monster:
                returnValue = getChromosome()[5];
                break;
            case Config.hardCode.strawberry:
                returnValue = getChromosome()[3];
                break;
            case Config.hardCode.mushroom:
                returnValue = getChromosome()[2];
                break;
        }
        return returnValue;
    }

    private void actOnMonster() {
        setEnergyLevel(0);
        removeSelfFromGrid();
    }

    private boolean actOnNearby(double gene, Location loc) {
        boolean isEat;
        if (gene == Config.encode.random) {
            int choice = randomChooseFromActions();
            isEat = actionsOnObjectWithoutRandom(choice, loc);
        } else {
            isEat = actionsOnObjectWithoutRandom(gene, loc);
        }
        return isEat;
    }

    private boolean actionsOnObjectWithoutRandom(double action, Location loc) {
        boolean isEat = false;
        if (action == Config.encode.away_from) {
            moveAway(loc);
        }
        if (action == Config.encode.ignore) {
            randomAct();
        }
        if (action == Config.encode.towards) {
            isEat = true;
        }
        return isEat;
    }

    private void actOnCreature(Creature returnActor2) {
        randomAct();
    }

    private void actOnStrawberry(Strawberry returnActor) {
        double choice = getChromosome()[0];
        if (choice == Config.encode.eat) {
            int energy = getEnergyLevel();
            energy += Config.energyForStrawberry;
            setEnergyLevel(energy);
            moveTo(returnActor.getLocation());
        } else {
            randomAct();
        }
    }

    private int randomChooseFromActions() {
        return Utility.randomIntFromRange(1, 3);
    }

    private void moveAway(Location target) {
        int myRow = getLocation().getRow();
        int myCol = getLocation().getCol();
        int targetRow = target.getRow();
        int targetCol = target.getCol();

        ArrayList<Location> myNearbyEmpty = getGrid().getEmptyAdjacentLocations(getLocation());
        Location newLocation = getLocation();
        if (targetRow < myRow) {
            //it is above me
            if (targetCol < myCol) {
                //upleft
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    int tempCol = x.getCol();
                    if (tempRow > targetRow & tempCol >= targetCol) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            } else if (target.getCol() > myCol) {
                //upright
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    int tempCol = x.getCol();
                    if (tempRow > targetRow & tempCol <= targetCol) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            } else {
                //middle
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    if (tempRow > targetRow) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            }
        } else if (target.getRow() > myRow) {
            //it is below me
            if (targetCol < myCol) {
                //downleft
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    int tempCol = x.getCol();
                    if (tempRow < targetRow & tempCol >= targetCol) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            } else if (target.getCol() > myCol) {
                //downright
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    int tempCol = x.getCol();
                    if (tempRow < targetRow & tempCol <= targetCol) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            } else {
                //middle
                for (Location x : myNearbyEmpty) {
                    int tempRow = x.getRow();
                    if (tempRow < targetRow) {
                        newLocation = x;
                    }
                }
                moveTo(newLocation);
            }
        } else if (target.getRow() == myRow) {
            //same row with me
            for (Location x : myNearbyEmpty) {
                int tempCol = x.getCol();

                if (targetCol > myCol) {
                    //right
                    if (tempCol < myCol) {
                        newLocation = x;
                    }
                } else {
                    //left
                    if (tempCol > myCol) {
                        newLocation = x;
                    }
                }
            }
            moveTo(newLocation);
        }
    }

    private void putThingDownAndMove(Location oldLoc, Location newLoc) {
        justPutThingDown(oldLoc);
        putSelfInGrid(getGrid(), newLoc);
        cleanHands();
    }

    private void actOnMushroom(Mushroom returnActor) {
        double choice = getChromosome()[0];
        if (choice == Config.encode.eat) {
            returnActor.removeSelfFromGrid();
            setEnergyLevel(0);
            removeSelfFromGrid();
        } else {
            randomAct();
        }
    }

    /**
     * Tests whether this bug can move forward into a location that is empty or
     * contains a Strawberry.
     *
     * @return true if this bug can move.
     */
    public boolean canMove() {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Strawberry);
    }

    private String nextMove() {
        ArrayList<Actor> nearbyActors = getActors();
        int[] foundActor = findNearbyActors(nearbyActors);
        HashMap<String, Double> ActionList = listNextMove(foundActor);
        Map.Entry<String, Double> move = findNextMove(ActionList);
        return move.getKey();
    }

    private Map.Entry<String, Double> findNextMove(HashMap<String, Double> ActionList) {
        Map.Entry<String, Double> maxEntry = null;
        for (Map.Entry<String, Double> entry : ActionList.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    /**
     * list the next move which is available.
     *
     * @param foundActor The nearby Actors.
     * @return Available move.
     */
    private HashMap<String, Double> listNextMove(int[] foundActor) {
        HashMap<String, Double> ActionList = new HashMap<>();
        //mush,star/core/mons
        if (foundActor[0] == 1) {
            ActionList.put("mushroom", getChromosome()[9]);
        }
        if (foundActor[1] == 1) {
            ActionList.put("strawberry", getChromosome()[10]);
        }
        if (foundActor[2] == 1) {
            ActionList.put("creature", getChromosome()[11]);
        }
        if (foundActor[3] == 1) {
            ActionList.put("monster", getChromosome()[12]);
        }
        return ActionList;
    }

    private <T extends Actor> T getNextActor(Class<T> type) {
        ArrayList<Actor> nearbyActors = getActors();

        for (Actor a : nearbyActors) {
            if (type.isAssignableFrom(a.getClass())) {
                return (T) a;
            }
        }
        return null;
    }
}
