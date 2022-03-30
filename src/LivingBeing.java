import java.util.Random;
import java.util.List;

/**
 * A class representing shared characteristics of living beings.
 * Living beings can age, breed, be eaten and die.
 * They have a field and location in said field.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 3.0
 */
public abstract class LivingBeing
{
    // A maximum age this living being can reach
    private int maxAge;
    // The current food value of this living being
    private int foodValue;
    // Set breeding probability of this living being.
    private double breedingProbability;
    // Current age.
    private int age;
    // Current location in a field.
    private Location location;
    // A field that this living being is in.
    private Field field;
    // Current status of this living being.
    private boolean alive;
    // Randomizer for living beings
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new living being (animal/plant).
     *
     * @param randomAge true if the age should be randomly selected for this living being.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public LivingBeing(boolean randomAge, Field field, Location location)
    {
        // At first give the instance fields the default values.
        maxAge = getDefaultMaxAge();
        foodValue = getDefaultFoodValue();
        breedingProbability = getDefaultBreedingProbability();

        this.field = field;
        setLocation(location);

        alive = true;
        if(randomAge) {
            Random rand = getRand();
            age = rand.nextInt(getMaxAge());
        }
        else {
            age = 0;
        }
    }

    /**
     * Make this living being act. This method is overriden in the subclasses using the super call to this method.
     * The only common action all animals do is get older.
     * @param newBeings A list to receive newborn animals and new plants.
     */
    protected void act(List<LivingBeing> newBeings)
    {
        incrementAge();
    }

    /**
     * Return the maximum this living being can reach.
     * @return Maximum age.
     */
    public int getMaxAge()
    {
        return maxAge;
    }

    /**
     * Set the maximum age of this living being to a new value.
     * @param newMaxAge A new value to replace with.
     */
    public void setMaxAge(int newMaxAge)
    {
        if(newMaxAge < age) {
            newMaxAge = age;
        }
        else if(newMaxAge <= 0) {
            newMaxAge = getDefaultMaxAge();
        }

        maxAge = newMaxAge;
    }

    /**
     * Return the breeding probability of this living being.
     * @return The breeding probability.
     */
    public double getBreedingProbability()
    {
        return breedingProbability;
    }

    /**
     * Set the breeding probability of this living being to a new value.
     * @return newProbability A new value to replace with.
     */
    public void setBreedingProbability(double newProbability)
    {
        if(newProbability <= 0.0) {
            newProbability = getDefaultBreedingProbability();
        }
        else if(newProbability > 1.0) {
            newProbability = 1.0;
        }

        breedingProbability = newProbability;
    }

    /**
     * Return the food value of this living being.
     * @return Food value.
     */
    protected int getFoodValue()
    {
        return foodValue;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the current age of the living being.
     */
    protected int getAge()
    {
        return age;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increase the age.
     * This could result in the death of the living being.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Return the being's location.
     * @return The location of the living being.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Return the being's field.
     * @return The field of the living being.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Set all the instance attributes, that have a default static value, to the default value.
     */
    public void setValuesToDefault()
    {
        setBreedingProbability(getDefaultBreedingProbability());
        setMaxAge(getDefaultMaxAge());
        foodValue = getDefaultFoodValue();
    }

    /**
     * Return a randomizer object for this living being.
     * @return An object of type Random.
     */
    protected Random getRand()
    {
        return rand;
    }

    // ABSTRACT METHODS

    /**
     * Return the default breeding probability for this living being.
     * @return Default breeding probability from the subclass.
     */
    abstract public double getDefaultBreedingProbability();

    /**
     * Return the default maximum age of this living being.
     * @return Default maximum age from the subclass.
     */
    abstract protected int getDefaultMaxAge();

    /**
     * Return the default food value of this living being.
     * @return Default food value of this being from the subclass.
     */
    abstract protected int getDefaultFoodValue();
}
