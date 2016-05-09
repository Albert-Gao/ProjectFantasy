package GeneticAlgorithm;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Creature;

/**
 * This class runs a world that contains crab critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class Start {
    public static void main(String[] args) {
        myGA ga = new myGA();
        ActorWorld world = new ActorWorld(ga);
        world.show();
    }

    private static void IsEatMushroom(Creature x) {
        double[] chro = x.getChromosome();
        double choice = chro[2];
        System.out.println(choice);
        if (choice == Config.encode.towards) {
            System.out.println("It moves towards mushroom.");
        }
        if (chro[0] == Config.encode.eat) {
            System.out.println("It eats mushroom.");
        }
    }

    private static void IsEatStrawberry(Creature x) {
        double[] chro = x.getChromosome();
        double choice = chro[3];
        System.out.println(choice);
        if (choice == Config.encode.towards) {
            System.out.println("It moves towards strawberry.");
        }
        if (chro[1] == Config.encode.eat) {
            System.out.println("It eats strawberry.");
        }
    }
}