package GeneticAlgorithm;

import java.util.Random;

/**
 * Created by camus on 8/05/2016.
 */
public class Utility {
    public static double[] randomChromosome() {
        double[] chromosome = new double[Config.encode.chromosomeLenth];
        chromosome[0] = createGene(2);
        chromosome[1] = createGene(2);
        chromosome[2] = createGene(4);
        chromosome[3] = createGene(4);
        chromosome[4] = createGene(4);
        chromosome[5] = createGene(4);
        chromosome[6] = createGene(5);
        chromosome[7] = randomDoubleLessThanOne();
        chromosome[8] = randomDoubleLessThanOne();
        chromosome[9] = randomDoubleLessThanOne();
        chromosome[10] = randomDoubleLessThanOne();
        chromosome[11] = randomDoubleLessThanOne();
        chromosome[12] = randomDoubleLessThanOne();
        return chromosome;
    }

    public static int randomIntFromRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static double randomDoubleLessThanOne() {
        Random rand = new Random();
        double randomValue = 0 + (1 - 0) * rand.nextDouble();
        return randomValue;
    }

    private static int createGene(int digits) {
        if (digits == 2) {
            return randomIntFromRange(0, 1); //for eat and ingore
        } else if (digits == 4) {
            return randomIntFromRange(1, 4); //for ingore,random,towards,runaway
        } else {
            return randomIntFromRange(4, 8); //for the direction
        }
    }
}
