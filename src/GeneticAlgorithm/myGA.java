package GeneticAlgorithm;

import info.gridworld.actor.*;
import info.gridworld.grid.Grid;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by camus on 9/05/2016.
 */
public class myGA {
    public void setPopulation(ArrayList<Creature> population) {
        this.population = population;
    }

    public void setRoundCounts(int roundCounts) {
        this.roundCounts = roundCounts;
    }

    public void setLivedNum(int livedNum) {
        this.livedNum = livedNum;
    }

    public ArrayList<Creature> getPopulation() {

        return population;
    }

    public int getRoundCounts() {
        return roundCounts;
    }

    public int getLivedNum() {
        return livedNum;
    }

    private LinkedList<Integer> fitnessList;

    public void setFitnessList(LinkedList<Integer> fitnessList) {
        this.fitnessList = fitnessList;
    }

    public LinkedList<Integer> getFitnessList() {
        return fitnessList;
    }

    private ArrayList<Creature> population;
    private ArrayList<Monster> monsters;
    private ArrayList<Mushroom> mushrooms;
    private ArrayList<Strawberry> strawberries;
    private int roundCounts;
    private int livedNum; //population still alive

    public myGA() {
        this.fitnessList = new LinkedList<>();
        this.population = new ArrayList<>();
        this.roundCounts = 0;
        this.livedNum = Config.populationSize;
        this.monsters = new ArrayList<>();
        this.mushrooms = new ArrayList<>();
        this.strawberries = new ArrayList<>();
    }

    public void generateCreatures() {
        ArrayList<Creature> newPopulation = new ArrayList<>();

        for (int i = 0; i < Config.populationSize; i++) {
            Creature a, b;
            //choose 2 parents using roulette wheel
            do {
                a = this.selectMemberUsingRouletteWheel();
                b = this.selectMemberUsingRouletteWheel();
            } while (a == b);

            //pick up the winner and the loser, when crossover, winner will get more DNA
            Creature winner, loser;
            if (a.getEnergyLevel() >= b.getEnergyLevel()) {
                winner = a;
                loser = b;
            } else {
                winner = b;
                loser = a;
            }

            //check if crossover
            double choice = Utility.randomDoubleLessThanOne();
            Creature newKid;
            if (choice < Config.crossoverRate) {
                newKid = singlePointCrossover(winner, loser);
            } else {
                newKid = winner;
            }

            //check if mutation
            choice = Utility.randomDoubleLessThanOne();
            if (choice < Config.mutationRate) {
                newKid.setChromosome(doMutation(newKid.getChromosome()));
            }
            //add to the population
            Creature realNew = new Creature(newKid.getChromosome());
            newPopulation.add(realNew);
        }
        if (monsters.size() != 0) {
            cleanAll();
        }
        this.population = newPopulation;
    }

    public void generateRandomCreatures() {
        for (int i = 0; i < Config.populationSize; i++) {
            this.population.add(new Creature());
        }
    }

    private double[] doMutation(double[] oldChromosome) {
        //choose 2 bits to change, make sure it's different
        int a, b;
        do {
            a = Utility.randomIntFromRange(0, 12);
            b = Utility.randomIntFromRange(0, 12);
        } while (a == b);
        changeBits(oldChromosome, a);
        changeBits(oldChromosome, b);
        return oldChromosome;
    }

    private void changeBits(double[] oldChromosome, int a) {
        if (a > 6) { //fill with double [0,1]
            double old = oldChromosome[a];
            double randomNum = Utility.randomDoubleLessThanOne();
            oldChromosome[a] = Math.abs(old - randomNum);
        } else { //fill with int
            if (a == 0 || a == 1) {
                oldChromosome[a] = Utility.randomIntFromRange(0, 1);
            } else if (a >= 2 & a <= 5) {
                oldChromosome[a] = Utility.randomIntFromRange(1, 4);
            } else if (a == 6) {
                oldChromosome[a] = Utility.randomIntFromRange(4, 8);
            }
        }
    }

    private Creature singlePointCrossover(Creature winner, Creature loser) {
        int crossPoint = Utility.randomIntFromRange(1, 11);
        double[] chromosome = new double[Config.encode.chromosomeLenth];
        for (int i = 0; i < Config.encode.chromosomeLenth; i++) {
            chromosome[i] = 0.5;
        }

        double[] winnerChromosome = winner.getChromosome();
        double[] loserChromosome = loser.getChromosome();
        if (crossPoint > 6) {
            //left is longer
            //copy the winner first, longer
            for (int i = 0; i < crossPoint; i++) {
                chromosome[i] = winnerChromosome[i];
            }
            //copy the loser second, shorter
            for (int i = crossPoint; i < Config.encode.chromosomeLenth; i++) {
                chromosome[i] = loserChromosome[i];
            }
        } else {
            //right is longer
            //copy the loser first, shorter
            for (int i = 0; i < crossPoint; i++) {
                chromosome[i] = loserChromosome[i];
            }
            //copy the winner second, longer
            for (int i = crossPoint; i < Config.encode.chromosomeLenth; i++) {
                chromosome[i] = winnerChromosome[i];
            }
        }
        return new Creature(chromosome);
    }

    private Creature selectMemberUsingRouletteWheel() {
        int totalSum = 0;
        for (int x = this.population.size() - 1; x >= 0; x--) {
            totalSum += population.get(x).getEnergyLevel();
        }
        //System.out.println("totSum: "+totalSum);
        int rand = Utility.randomIntFromRange(0, totalSum);
        int partialSum = 0;
        for (int x = population.size() - 1; x >= 0; x--) {
            partialSum += population.get(x).getEnergyLevel();
            if (partialSum > rand) {
                return population.get(x);
            }
        }

        //if no, than return a one whose life is not zero
        ArrayList<Creature> noZeroList = new ArrayList<>();
        for (Creature c : population) {
            if (c.getEnergyLevel() != 0) {
                noZeroList.add(c);
            }
        }
        if (noZeroList.size() != 0) {
            int randomNum = Utility.randomIntFromRange(0,noZeroList.size()-1);
            return noZeroList.get(randomNum);
        } else {
            int randomNum = Utility.randomIntFromRange(0,Config.populationSize-1);
            return population.get(randomNum);
        }
    }

    public boolean isAllDead() {
        int count = 0;
        for (Creature a : population) {
            if (a.getEnergyLevel() == 0) {
                count++;
            }
        }
        return count == Config.populationSize;
    }

    public void arrangeWorld(ActorWorld world) {
        //fill the creatures
        for (int i = 0; i < Config.populationSize; i++) {
            world.add(population.get(i));
        }

        //fill the monsters
        for (int i = 0; i < Config.monsterNum; i++) {
            Monster x = new Monster();
            monsters.add(x);
            world.add(x);
        }

        //fill the strawberries
        for (int i = 0; i < Config.strawNum; i++) {
            Strawberry x = new Strawberry();
            strawberries.add(x);
            world.add(x);
        }

        //fill the mushroom
        for (int i = 0; i < Config.mushroomNum; i++) {
            Mushroom x = new Mushroom();
            mushrooms.add(x);
            world.add(x);
        }
    }

    private void cleanAll() {
        for (Creature c : population) {
            Grid grid = c.getGrid();
            if (grid != null) {
                c.removeSelfFromGrid();
            }
        }
        for (Monster m : monsters) {
            Grid grid = m.getGrid();
            if (grid != null) {
                m.removeSelfFromGrid();
            }
        }
        for (Strawberry s : strawberries) {
            Grid grid = s.getGrid();
            if (grid != null) {
                s.removeSelfFromGrid();
            }
        }
        for (Mushroom m : mushrooms) {
            Grid grid = m.getGrid();
            if (grid != null) {
                m.removeSelfFromGrid();
            }
        }
        //this.population = null;
        this.monsters = null;
        this.strawberries = null;
        this.mushrooms = null;
        //this.population = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.strawberries = new ArrayList<>();;
        this.mushrooms = new ArrayList<>();;
    }

    public void printResult() {
        int totalFitness = 0;
        for (Creature c : population) {
            totalFitness += c.getEnergyLevel();
        }
        fitnessList.addLast(totalFitness);

        //generate the whole fitness list;
        StringBuilder sb1 = new StringBuilder();
        for (Integer i : fitnessList) {
            sb1.append(i);
            sb1.append(",");
        }

        //Display on the MessageBox
        StringBuilder sb = new StringBuilder();
        sb.append("Round:");
        sb.append(roundCounts);
        sb.append(";  ");
        sb.append("Current Fitness:");
        sb.append(fitnessList.getLast());
        sb.append(";  ");
        sb.append("Total by far: ");
        sb.append(sb1.toString());

        //Display in the console
        System.out.println("================================");
        System.out.println("Round: " + roundCounts);
        System.out.println("Current Fitness: " + fitnessList.getLast());
        System.out.println("Total by far:" + sb1.toString());
    }
}
