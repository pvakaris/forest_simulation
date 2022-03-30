import java.util.Random;
import java.util.List;

/**
 * Represents an instance of a plant.
 * Plants are living beings that do not move.
 * They only age, spread and die.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class Plant extends LivingBeing
{
    // The age to which a plant can live.
    private static final int MAX_AGE = 40;
    // The likelihood that the plant will spread to neighbouring locations.
    private static final double BREEDING_PROBABILITY = 0.12;
    // How much food value it has for the herbivores
    private static final int FOOD_VALUE = 8;

    /**
     * Create a new plant at a location in the field.
     *
     * @param randomSize true if the size should be randomly selected for this plant.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(boolean randomSize, Field field, Location location)
    {
        super(randomSize, field, location);
    }

    /**
     * Make this plant act - that is make it grow and spread.
     * @param newPlants A list to receive newgrown animals.
     */
    protected void act(List<LivingBeing> newPlants)
    {
        super.act(newPlants);
        if(isAlive()) {
            spread(newPlants);
        }
    }

    /**
     * Return the probability that a plant will spread at a particular step.
     * @return The spread/breeding probability.
     */
    public double getDefaultBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the default food value of plant.
     * @return Default food value.
     */
    protected int getDefaultFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Return the default maximum age of a plant.
     * @return Default maximum age.
     */
    protected int getDefaultMaxAge()
    {
        return MAX_AGE;
    }

    // PRIVATE METHODS

    /**
     * Used to simulate the spread of the plant.
     * New plants are put to the newPlants list.
     *
     * @param newPlants A list to put the new plants to
     */
    private void spread(List<LivingBeing> newPlants)
    {
        Field field = getField();
        List<Location> freeAdjacent = field.getFreeAdjacentLocations(getLocation());
        Random rand = getRand();
        // Puts newbgrowns in the free adjacent locations.
        // If there are no free adjacent locations, no new plants will grow.
        for(Location loc : freeAdjacent) {
            if(rand.nextDouble() <= getDefaultBreedingProbability())
            {
                newPlants.add(new Plant(false, field, loc));
            }
        }
    }
}