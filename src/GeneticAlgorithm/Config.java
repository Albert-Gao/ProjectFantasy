package GeneticAlgorithm;

/**
 * Created by camus on 7/05/2016.
 */
public class Config {
    //configuration of the world
    public static final int worldColumns = 60;//60;
    public static final int worldRows = 35;//35;
    public static final int strawNum = 55;
    public static final int mushroomNum = 15;
    public static final int monsterNum = 15;
    public static final int populationSize = 20;
    public static final int energyForStrawberry = 10;

    //parameters for creature's energy
    public static final int moveCostEnergy = 1;
    public static final int eatCostEnergy = 2;
    public static final int maxEnergy = 30;

    //parameters of the genetic procedure
    public static final double mutationRate = 0.001;
    public static final double crossoverRate = 0.7;
    public static final int stepsForEachRound = 25; //it should be less than (maxEnergy/moveCost)
    public static final double maxEvolutionNum = 3000;

    public static class encode {
        //parameters of the chromosome
        public static final int chromosomeLenth = 13;
        public static final int eat = 0;
        public static final int ignore = 1;
        public static final int towards = 2;
        public static final int away_from = 3;
        public static final int random = 4;
        public static final int north = 5;
        public static final int east = 6;
        public static final int south = 7;
        public static final int west = 8;
    }

    public static class hardCode {
        public static final int monster= 1;
        public static final int creature = 2;
        public static final int mushroom = 3;
        public static final int strawberry= 4;
    }
}
