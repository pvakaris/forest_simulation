import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.awt.Color;

/**
 * A simple model of a lynx.
 * Lynxes are carnivorous nocturnal animals.
 * They live long lives, longer than other animals.
 * They do not last as long without food as wolves,
 * however.
 *
 * This is not an accurate model of real-life wolves
 * and the differences between lynxes and wolves
 * have no real-life basis.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class Lynx extends Animal
{
    // Characteristics shared by all Lynxes (class variables).

    // The age at which a Lynx can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a Lynx can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a Lynx breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // Maximum number of steps a Lynx can go before it has to eat again.
    private static final int MAX_STEPS_WITHOUT_EATING = 40;
    // How much food value it has for the predator eating the lynx (even though it has no predators).
    private static final int FOOD_VALUE = 69;
    // What kind of prey Lynxes eat.
    private static final Class[] EDIBLE_PREY = {Hamster.class, Deer.class};
    // The field where Lynxes look for food.
    private static Field huntingField;
    // The default color of all Lynxes.
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * Create a new Lynx. A Lynx may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the Lynx will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lynx(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * Set a field where Lynxes look for food.
     * field A new hunting field.
     */
    public static void setHuntingField(Field field)
    {
        huntingField = field;
    }

    /**
     * Return a field where Lynxes look for food.
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
        return true;
    }

    /**
     * Return the default maximum amount of steps a Lynx can survive without eating.
     * @return default maximum amount of steps for instances of the Lynx class.
     */
    protected int getDefaultMaxFoodLevel()
    {
        return MAX_STEPS_WITHOUT_EATING;
    }

    /**
     * Create a new instance of Lynx.
     * @param field A field of the new instance of Lynx.
     * @param loc A location in the specified field.
     * @return A new Lynx object.
     */
    protected Animal createNewborn(Field field, Location loc)
    {
        return new Lynx(false, field, loc);
    }

    /**
     * Return Minimum breeding age of Lynxes.
     * @return Breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return how much energy this animal gives if eaten by default.
     * @return The Lynxes's default food value.
     */
    protected int getDefaultFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Return the default probability that this Lynx will give birth if it meets a male Lynx.
     * @return The default breeding probability.
     */
    public double getDefaultBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum amount of newborns a Lynx can give birth to at a time.
     * @return The maximum amount of newborns.
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Return the default maximum age a Lynx can reach.
     * @return Default maximum age of Lynx.
     */
    protected int getDefaultMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return an array of Classes, whose instances a Lynx eats.
     * @return An array of type Class.
     */
    protected Class[] retrieveEdible()
    {
        return EDIBLE_PREY;
    }

    /**
     * Returns the default color of a Lynx.
     * @return default color.
     */
    public Color getDefaultColor()
    {
        return DEFAULT_COLOR;
    }
}