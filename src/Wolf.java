import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.awt.Color;

/**
 * A simple model of a wolf.
 * Wolves are carnivorous diurnal animals.
 * They live long lives, but shorter than lynxes
 * However, they can last longer without eating.
 *
 * This is not an accurate model of real-life wolves,
 * who are mostly crepuscular. The differences between
 * lynxes and wolves also have no real-life basis.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class Wolf extends Animal
{
    // Characteristics shared by all wolves (class variables).

    // The age at which a wolf can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a wolf can live.
    private static final int MAX_AGE = 270;
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // Maximum number of steps a wolf can go before it has to eat again.
    private static final int MAX_STEPS_WITHOUT_EATING = 50;
    // How much food value it has for the predator eating the wolf (even though it has no predators).
    private static final int FOOD_VALUE = 69;
    // Handles randomization for the whole class.
    private static final Class[] EDIBLE_PREY = {Hamster.class, Deer.class};
    // The field where wolves look for food.
    private static Field huntingField;
    // The default color of all wolves.
    public static final Color DEFAULT_COLOR = Color.RED;

    /**
     * Create a new Wolf. A Wolf may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the Wolf will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * Set a field where Wolves look for food.
     * field A new hunting field.
     */
    public static void setHuntingField(Field field)
    {
        huntingField = field;
    }

    /**
     * Return a field where Wolves look for food.
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
     * Return the default maximum amount of steps a Wolf can survive without eating.
     * @return default maximum amount of steps for instances of the Wolf class.
     */
    protected int getDefaultMaxFoodLevel()
    {
        return MAX_STEPS_WITHOUT_EATING;
    }

    /**
     * Create a new instance of Wolf.
     * @param field A field of the new instance of Wolf.
     * @param loc A location in the specified field.
     * @return A new Wolf object.
     */
    protected Animal createNewborn(Field field, Location loc)
    {
        return new Wolf(false, field, loc);
    }

    /**
     * Return Minimum breeding age of Wolves.
     * @return Breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return how much energy this animal gives if eaten by default.
     * @return The Wolves's default food value.
     */
    protected int getDefaultFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Return the default probability that this Wolf will give birth if it meets a male Wolf.
     * @return The default breeding probability.
     */
    public double getDefaultBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum amount of newborns a Wolf can give birth to at a time.
     * @return The maximum amount of newborns.
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Return the default maximum age a Wolf can reach.
     * @return Default maximum age of Wolf.
     */
    protected int getDefaultMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return an array of Classes, whose instances a wolf eats.
     * @return An array of type Class.
     */
    protected Class[] retrieveEdible()
    {
        return EDIBLE_PREY;
    }

    /**
     * Returns the default color of a wolf.
     * @return default color.
     */
    public Color getDefaultColor()
    {
        return DEFAULT_COLOR;
    }
}