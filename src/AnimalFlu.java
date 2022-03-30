/**
 * A disease that can infect any animal.
 * It decreases the animal's food level (increases hunger)
 * every time the animal acts.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class AnimalFlu extends Disease
{
    // An array of classes whose animals this disease can infect.
    private static final Class[] CAN_INFECT = {Animal.class};
    // How much this disease reduces the food level of an animal by.
    private static final int DECREASE_FOOD_LEVEL_BY = 1;
    // Infection probability of this disease.
    private static final double INFECTION_PROBABILITY = 0.08;
    // Probability that this disease will infect a random animal in the simulation.
    private static final double RANDOM_INFECTION_PROBABILITY = 0.005;

    /**
     * Creates an object of type WolfFlu.
     */
    public AnimalFlu()
    {
        super();
    }

    /**
     * Used to act on a particular animal infected with the COVID420.
     * @param animal An animal this disease acts on.
     */
    public void act(Animal animal)
    {
        animal.setFoodLevel(animal.getFoodLevel() - DECREASE_FOOD_LEVEL_BY);
    }

    /**
     * Return the infection probability of the COVID420.
     * @return INFECTION_PROBABILITY
     */
    public double getInfectionProbability()
    {
        return INFECTION_PROBABILITY;
    }

    /**
     * Return the probability that a random animal in the simulation will be infected with the COVID420.
     * @return RANDOM_INFECTION_PROBABILITY.
     */
    public double getRandomInfectionProbability()
    {
        return RANDOM_INFECTION_PROBABILITY;
    }

    /**
     * Return an array of classes whose instances this disease can infect.
     * @return An array of Classes.
     */
    protected Class[] getAnimalsItCanInfect()
    {
        return CAN_INFECT;
    }
}