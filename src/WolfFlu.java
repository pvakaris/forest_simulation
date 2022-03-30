/**
 * A disease that only infects wolves.
 * It decreases the infected wolf's lifespan by one step
 * every time it acts.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class WolfFlu extends Disease
{
    // An array of classes whose animals this disease can infect.
    private static final Class[] CAN_INFECT = {Wolf.class};
    // How much this disease reduces the lifespan of an animal by.
    private static final int DECREASE_LIFESPAN_BY = 1;
    // Infection probability of this disease.
    private static final double INFECTION_PROBABILITY = 0.02;
    // Probability that this disease will infect a random animal in the simulation.
    private static final double RANDOM_INFECTION_PROBABILITY = 0.005;

    /**
     * Creates an object of type WolfFlu.
     */
    public WolfFlu()
    {
        super();
    }

    /**
     * Used to act on a particular wolf infected with the WolfFlu.
     * @param animal A wolf this disease acts on.
     */
    public void act(Animal animal)
    {
        if(!canInfect(animal)) return;
        animal.setMaxAge(animal.getMaxAge() - DECREASE_LIFESPAN_BY);
    }

    /**
     * Return the infection probability of the WolfFlu.
     * @return INFECTION_PROBABILITY
     */
    public double getInfectionProbability()
    {
        return INFECTION_PROBABILITY;
    }

    /**
     * Return the probability that a random wolf in the simulation will be infected with the WolfFlu.
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
