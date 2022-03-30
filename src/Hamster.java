import java.util.List;
import java.util.Random;
import java.awt.Color;

/**
 * A simple model of a hamster.
 * Hamsters are diurnal animals that live short lives,
 * but breed a lot, and from an early age. They are herbivores,
 * therefore, they only eat plants. Due to their small size,
 * they do not offer much nourishment to predators.
 *
 * This is not an accurate model of real-life hamsters, who
 * are crepuscular, omnivorous and remain underground during 
 * the day to avoid being caught by predators.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class Hamster extends Animal
{
    // Characteristics shared by all Hamsters (class variables).

    // The age at which a Hamster can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a Hamster can live.
    private static final int MAX_AGE = 70;
    // The likelihood of a Hamster breeding.
    private static final double BREEDING_PROBABILITY = 0.40;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // How much food value it has for the predator eating the Hamster.
    private static final int FOOD_VALUE = 12;
    // A shared random number generator to control breeding.
    private static final int MAX_STEPS_WITHOUT_EATING = 15;
    // What kinds of plants Hamsters eat.
    private static final Class[] EDIBLE_VEGETATION = {Plant.class};
    // The field where foxes look for food.
    private static Field huntingField;
    // The default color of all Hamsters.
    public static final Color DEFAULT_COLOR = Color.YELLOW;

    /**
     * Create a new Hamster. A Hamster may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the Hamster will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hamster(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * Set a field where Hamsters look for food.
     * field A new hunting field.
     */
    public static void setHuntingField(Field field)
    {
        huntingField = field;
    }

    /**
     * Return a field where Hamsters look for food.
     * @return A hunting field
     */
    public Field getHuntingField()
    {
        return huntingField;
    }

    /**
     * Return whether the animal is nocturnal.
     * @return true if it is nocturnal.
     */
    protected boolean isNocturnal()
    {
        return false;
    }

    /**
     * Return the default maximum amount of steps a Hamster can survive without eating.
     * @return default maximum amount of steps for instances of the Hamster class.
     */
    protected int getDefaultMaxFoodLevel()
    {
        return MAX_STEPS_WITHOUT_EATING;
    }

    /**
     * Create a new instance of Hamster.
     * @param field A field of the new instance of Hamster.
     * @param loc A location in the specified field.
     * @return A new Hamster object.
     */
    protected Animal createNewborn(Field field, Location loc)
    {
        return new Hamster(false, field, loc);
    }

    /**
     * Return Minimum breeding age of Hamsters.
     * @return Breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return how much energy this animal gives if eaten by default.
     * @return The Hamsters's default food value.
     */
    protected int getDefaultFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Return the default probability that this Hamster will give birth if it meets a male Hamster.
     * @return The default breeding probability.
     */
    public double getDefaultBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum amount of newborns a Hamster can give birth to at a time.
     * @return The maximum amount of newborns.
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Return the default maximum age a Hamster can reach.
     * @return Default maximum age of Hamster.
     */
    protected int getDefaultMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return an array of Classes, whose instances a Hamster eats.
     * @return An array of type Class.
     */
    protected Class[] retrieveEdible()
    {
        return EDIBLE_VEGETATION;
    }

    /**
     * Returns the default color of a Hamster.
     * @return default color.
     */
    public Color getDefaultColor()
    {
        return DEFAULT_COLOR;
    }
}