package info.gridworld.actor;

import GeneticAlgorithm.Config;

/**
 * Created by camus on 9/05/2016.
 */
public abstract class ActorBase {
    public static void setFormerMushroom(Mushroom formerMushroom) {
        ActorBase.formerMushroom = formerMushroom;
    }

    public static void setFormerStrawberry(Strawberry formerStrawberry) {
        ActorBase.formerStrawberry = formerStrawberry;
    }

    public static void setFormerCreature(Creature formerCreature) {
        ActorBase.formerCreature = formerCreature;
    }

    public static void setIsMushroom(boolean isMushroom) {
        IsMushroom = isMushroom;
    }

    public static void setIsCreature(boolean isCreature) {
        IsCreature = isCreature;
    }

    public static void setIsStrawberry(boolean isStrawberry) {
        IsStrawberry = isStrawberry;
    }

    public static void setIsHoldInHand(boolean isHoldInHand) {
        IsHoldInHand = isHoldInHand;
    }

    private static Mushroom formerMushroom;
    private static Strawberry formerStrawberry;
    private static Creature formerCreature;
    private static boolean IsMushroom;

    public static boolean isMushroom() {
        return IsMushroom;
    }

    public static boolean isCreature() {
        return IsCreature;
    }

    public static boolean isStrawberry() {
        return IsStrawberry;
    }

    public static boolean isHoldInHand() {
        return IsHoldInHand;
    }

    private static boolean IsCreature;
    private static boolean IsStrawberry;
    private static boolean IsHoldInHand;

    private int energyLevel = Config.maxEnergy;
    public int getEnergyLevel() {
        return energyLevel;
    }
    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    /**
     * Constructs a blue actor that is facing north.
     */
    public ActorBase() {
        formerMushroom = null;
        formerStrawberry = null;
        formerCreature = null;
        IsHoldInHand = false;
        IsMushroom = false;
        IsStrawberry = false;
        IsCreature = false;
    }

    public void holdMushroomInHand(Mushroom outsider) {
        formerMushroom = outsider;
        formerCreature = null;
        formerStrawberry = null;
        IsHoldInHand = true;
        IsCreature = false;
        IsStrawberry = false;
        IsMushroom = true;
    }

    public void holdStrawberryInHand(Strawberry outsider) {
        formerStrawberry = outsider;
        formerCreature = null;
        formerMushroom = null;
        IsHoldInHand = true;
        IsMushroom = false;
        IsCreature = false;
        IsStrawberry = true;
    }

    public void holdCreatureInHand(Creature outsider) {
        formerCreature = outsider;
        formerMushroom = null;
        formerStrawberry = null;
        IsHoldInHand = true;
        IsMushroom = false;
        IsCreature = false;
        IsStrawberry = true;
    }

    public void cleanHands() {
        formerMushroom = null;
        formerStrawberry = null;
        formerCreature = null;
        IsHoldInHand = false;
        IsMushroom = false;
        IsStrawberry = false;
        IsCreature = false;
    }

    public static Strawberry getFormerStrawberry() {
        return formerStrawberry;
    }

    public static Mushroom getFormerMushroom() {
        return formerMushroom;
    }

    public static Creature getFormerCreature() {
        return formerCreature;
    }

    public void setInHand(boolean InHand, boolean isCreature, boolean isStraw, boolean isMush) {
        this.IsHoldInHand = InHand;
        this.IsMushroom = isMush;
        this.IsStrawberry = isStraw;
        this.IsCreature = isCreature;
    }
}
