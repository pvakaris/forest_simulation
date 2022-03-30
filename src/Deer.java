import java.util.List;
import java.util.Random;
import java.awt.Color;

/**
 * A simple model of a deer.
 * Deer are diurnal herbivores that live long lives.
 * They do not breed as much as other animals, and start
 * breeding later than hamsters. They do have a higher food
 * value for carnivores, due to their larger size.
 *
 * This is not an accurate model of real-life deer, who
 * are mostly crepuscular.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class Deer extends Animal
{
    // Characteristics shared by all deer (class variables).

    // The age at which a deer can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a deer can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a deer breeding.
    private static final double BREEDING_PROBABILITY = 0.30;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // How much food value it has for the predator eating the deer.
    private static final int FOOD_VALUE = 30;
    // A shared random number generator to control breeding.
    private static final int MAX_STEPS_WITHOUT_EATING = 15;
    // What kinds of plants deer eat.
    private static final Class[] EDIBLE_VEGETATION = {Plant.class};
    // The field where deer look for food.
    private static Field huntingField;
    // The default color of all deer.
    public static final Color DEFAULT_COLOR = Color.GREEN;


    /**
     * Create a new deer. A deer may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the deer will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deer(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * Set a field where deer look for food.
     * field A new hunting field.
     */
    public static void setHuntingField(Field field)
    {
        huntingField = field;
    }

    /**
     * Return a field where deer look for food.
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
     * Return the default maximum amount of steps a deer can survive without eating.
     * @return default maximum amount of steps for instances of the Deer class.
     */
    protected int getDefaultMaxFoodLevel()
    {
        return MAX_STEPS_WITHOUT_EATING;
    }

    /**
     * Create a new instance of Deer.
     * @param field A field of the new instance of Deer.
     * @param loc A location in the specified field.
     * @return A new Deer object.
     */
    protected Animal createNewborn(Field field, Location loc)
    {
        return new Deer(false, field, loc);
    }

    /**
     * Return Minimum breeding age of deer.
     * @return Breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return how much energy this animal gives if eaten by default.
     * @return The deer's default food value.
     */
    protected int getDefaultFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Return the default probability that this deer will give birth if it meets a male deer.
     * @return The default breeding probability.
     */
    public double getDefaultBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum amount of newborns a deer can give birth to at a time.
     * @return The maximum amount of newborns.
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Return the default maximum age a deer can reach.
     * @return Default maximum age of deer.
     */
    protected int getDefaultMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return an array of Classes, whose instances a deer eats.
     * @return An array of type Class.
     */
    protected Class[] retrieveEdible()
    {
        return EDIBLE_VEGETATION;
    }

    /**
     * Returns the default color of a deer.
     * @return default color.
     */
    public Color getDefaultColor()
    {
        return DEFAULT_COLOR;
    }
}