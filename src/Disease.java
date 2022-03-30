/**
 * Abstract class for diseases.
 * Diseases can infect animals and can have various effects on them.
 * Each disease can only infect certain species.
 * Diseases act on infected animals every time the animal itself acts.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public abstract class Disease
{
    /**
     * Creates an object of type Disease.
     */
    public Disease()
    {
        // Nothing to initialise.
    }

    /**
     * Check if the specified object can be infected by a disease.
     * @param animal An animal that is checked.
     * @return true if it can infect this animal.
     */
    public boolean canInfect(Object animal)
    {
        for(Class animalClass : getAnimalsItCanInfect()) {
            if(animal != null && animalClass.isInstance(animal)) {
                return true;
            }
        }
        return false;
    }

    // ABSTRACT METHODS

    /**
     * Used to act on a particular animal infected by a disease.
     * @param animal An animal this disease acts on.
     */
    public abstract void act(Animal animal);

    /**
     * Return the infection probability of the disease.
     * @return double
     */
    public abstract double getInfectionProbability();

    /**
     * Return the probability that a random animal will be infected by the disease.
     * @return double
     */
    public abstract double getRandomInfectionProbability();

    /**
     * Return an array of classes whose instances this disease can infect.
     * @return Class[]
     */
    protected abstract Class[] getAnimalsItCanInfect();
}