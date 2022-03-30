import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.HashSet;
import java.awt.Color;

/**
 * A class representing shared characteristics of animals.
 * Apart from what they have in common with other living beings,
 * animals can eat, be infected by disease and starve.
 * Animals are also drawable and, therefore, have a color.
 * Animals can be either male or female.
 *
 * @author David J. Barnes, Michael Kölling, Flavio Melinte Citea and Vakaris Paulavičius
 * @version 5.0
 */
public abstract class Animal extends LivingBeing implements Drawable
{
    // A maximum amount of energy an animal can have.
    private int maxFoodLevel;
    // Current energy level of the animal.
    private int foodLevel;
    // An flag to determine the gender.
    private boolean isFemale;
    // A set of diseases that this animal is currently infected with.
    private HashSet<Disease> diseases;
    // The color of this animal.
    private Color color;

    /**
     * Create a new animal at a location in the field.
     *
     * @param randomAge true if the age should be randomly selected for this animal.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        // Initially an animal has no diseases
        diseases = new HashSet<>();
        // Maximum food level is retrieved from a static variable that belongs to a particular animal class
        maxFoodLevel = getDefaultMaxFoodLevel();

        if(randomAge) {
            Random rand = getRand();
            foodLevel = rand.nextInt(getMaxFoodLevel());
        }
        else {
            foodLevel = getMaxFoodLevel();
        }

        isFemale = determineGender();

        setColor(getProperColor());
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    protected void act(List<LivingBeing> newAnimals)
    {
        super.act(newAnimals);
        diseasesAct();
        incrementHunger();

        Field field = getField();
        if(isAlive() && (isNocturnal() == field.isNight())) {
            giveBirth(newAnimals);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Return current food level of this animal.
     * @return Current food level (HUNGER).
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * Change the current food level to a new food level.
     * @param newFoodLevel A new value to replace with.
     */
    public void setFoodLevel(int newFoodLevel)
    {
        if(newFoodLevel > maxFoodLevel) {
            foodLevel = maxFoodLevel;
        }
        else if(newFoodLevel < 0) {
            foodLevel = 0;
        }
        else {
            foodLevel = newFoodLevel;
        }
    }

    /**
     * Infect this animal with a specified disease.
     * @param newDisease A new disease this animal is infected with.
     */
    public void infectWith(Disease newDisease)
    {
        if(newDisease != null) diseases.add(newDisease);
        this.setColor(getProperColor());
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Return if the animal's sex is female.
     * @return true if the animal belongs to the female gender.
     */
    protected boolean isFemale()
    {
        return isFemale;
    }

    /**
     * Returns the color of this animal
     * @return Color object of the animal.
     */
    public Color getColor()
    {
        return color;
    }

    // PRIVATE METHODS

    /**
     * Sets the color of this animal.
     * @param newColor The color we want to set this animal to.
     */
    private void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Returns the proper color the animal should have,
     * @return The proper color.
     */
    private Color getProperColor()
    {
        Color properColor = getDefaultColor();

        if(properColor == null) {
            return null;
        }

        // Males have a darker color than females.
        if(!isFemale) {
            properColor = properColor.darker();
        }

        if(!diseases.isEmpty()) {
            properColor = getSickColor(properColor);
        }

        return properColor;
    }

    /**
     * Returns the color an animal with a certain color will have if sick.
     * @param color The color of the animal.
     * @return Color of the sick animal.
     */
    private Color getSickColor(Color color)
    {
        if(color == null) {
            return null;
        }

        float r = (float) color.getRed();
        r = r / 255;

        float g = (float) color.getGreen();
        g = g / 255;

        float b = (float) color.getBlue();
        b = b / 255;

        // Set the transparency of a sick animal.
        // 0.0 completely transparent, 1.0 fully opaque.
        // Healthy animals - 1.0
        float a = (float) 0.3;

        return new Color(r, g, b, a);
    }

    /**
     * Return the maximum food level this animal can reach
     * @return Maximum food level.
     */
    private int getMaxFoodLevel()
    {
        return maxFoodLevel;
    }

    /**
     * Change the maximum food level to a new maximum food level.
     * @param newMaxFoodLevel A new value to replace with.
     */
    private void setMaxFoodLevel(int newMaxFoodLevel)
    {
        if(newMaxFoodLevel < foodLevel) {
            newMaxFoodLevel = foodLevel;
        }
        else if(newMaxFoodLevel <= 0) {
            newMaxFoodLevel = getDefaultMaxFoodLevel();
        }

        maxFoodLevel = newMaxFoodLevel;
    }

    /**
     * Look for prey/plants reachable from the current location.
     * Only the first live prey/plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(){
        Field field = getField();
        Field huntingField = getHuntingField();

        List<Location> reachableLocations = huntingField.adjacentLocations(getLocation());
        // The location of the animal is added as well (rabbits can eat plants that grow in their location).
        reachableLocations.add(getLocation());

        // Iterates over the reachable locations.
        Iterator<Location> it = reachableLocations.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object possibleFood = huntingField.getObjectAt(where);

            // Checks if the object found is food.
            if(possibleFood != null && isEdible(possibleFood.getClass())) {
                // First check if it is the same field
                // If no, it means that it is a herbivore looking for food in the plant field
                // Then if there is no object at that location in the animal field it can move there and eat the plant that grows there
                // If there already is an animal, it checks whether it's the same location that this.Animal stands in.
                // If it is true, then it means that a herbivore is in the same location as ther plan and it can eat it.
                if(field == huntingField || field.getObjectAt(where) == null || where == getLocation()) {
                    LivingBeing prey = (LivingBeing) possibleFood;

                    // Checks if prey is alive.
                    if(prey.isAlive()) {
                        // eats prey.
                        prey.setDead();
                        foodLevel += prey.getFoodValue();

                        // Food value cannot go beyond its limit.
                        if(foodLevel > getMaxFoodLevel()) {
                            foodLevel = getMaxFoodLevel();
                        }

                        return where;
                    }

                }
            }
        }

        return null;
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Checks if the animal eats instances of a class.
     * @param objectClass Class of the instances.
     * @return true if the animal does eat instances of the specified class.
     */
    private boolean isEdible(Class objectClass)
    {
        Class[] menu = retrieveEdible();
        for(int i = 0; i < menu.length; i++)
        {
            if(objectClass.equals(menu[i])) return true;
        }
        return false;
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    private void giveBirth(List<LivingBeing> newAnimals)
    {
        Field field = getField();

        // Checks if there is a male around.
        if(isFemale() && canBreed() && thereIsMaleOfBreedingAge(field.adjacentLocations(getLocation()), field))
        {
            // Get a list of adjacent free locations.
            // New animals may be born into free adjacent locations (number might be zero).
            List<Location> free = field.getFreeAdjacentLocations(getLocation());

            // Generates a random number of births based on class-defined probabilities.
            int births = breed();

            // Puts newborns in the free adjacent locations.
            // If there are no free adjacent locations, no young will be born.
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Animal young = createNewborn(field, loc);
                newAnimals.add(young);
            }
        }
    }

    /**
     * Generate a number representing the number of births.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        Random rand = getRand();
        if(rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Checks whether or not there is a male of the same species of breeding age in a list of locations.
     * @param locations Locations where we want to see if there is a male.
     * @param field The field the locations belong to.
     * @return true If there is at least one male.
     */
    private boolean thereIsMaleOfBreedingAge(List<Location> locations, Field field)
    {
        if(field == null) {
            return false;
        }

        // Iterate through locations.
        for(Location location : locations) {
            if(location != null) {
                Object object = field.getObjectAt(location.getRow(), location.getCol());

                // Check if animals are of the same species.
                if(object != null && object.getClass().equals(this.getClass())) {
                    Animal animal = (Animal) object;

                    // Check if animal is male of breeding age.
                    if(!animal.isFemale() && animal.canBreed()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Used to make all the diseases, that this animal is infected with, act on this animal.
     */
    private void diseasesAct()
    {
        for(Disease disease : diseases)
        {
            infectNeighbours(disease);
            disease.act(this);
        }
    }

    /**
     * Used to randomly select a gender for the newborn animal.
     * @return true if it is the female gender.
     */
    private boolean determineGender()
    {
        Random rand = getRand();
        return rand.nextInt(2) == 1;
    }

    /**
     * Used to try to infect neighbouring animals with the disease.
     * @param disease A disease to try to infect with.
     */
    private void infectNeighbours(Disease disease)
    {
        if(this.isAlive()) {
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            // Check all the neighbours to check whether they can be infected
            for(Location location : adjacent)
            {
                Object animalObject = field.getObjectAt(location);
                if(disease.canInfect(animalObject))
                {
                    Animal animal = (Animal) animalObject;
                    Random rand = animal.getRand();

                    if(rand.nextDouble() <= disease.getInfectionProbability())
                    {
                        animal.infectWith(disease);
                    }
                }
            }
        }
    }

    /**
     * Set all the instance attributes, that have a default static value, to the default value.
     */
    public void setValuesToDefault()
    {
        super.setValuesToDefault();
        setMaxFoodLevel(getDefaultMaxFoodLevel());
    }

    // ABSTRACT METHODS

    /**
     * Return if the animal is nocturnal.
     * @return true if it is nocturnal.
     */
    abstract protected boolean isNocturnal();

    /**
     * Return the field which this animal looks for food in.
     * @return The the field where this animal hunts.
     */
    abstract protected Field getHuntingField();

    /**
     * Return the default maximum food level.
     * @return Default maximu food level.
     */
    abstract protected int getDefaultMaxFoodLevel();

    /**
     * Return the age from which this animal can participate in the breeding process.
     * @return Breeding age.
     */
    abstract protected int getBreedingAge();

    /**
     * Return the maximum amount of newborns this animal can produce at a single time.
     * @return A maximum possible amount of newborns.
     */
    abstract protected int getMaxLitterSize();

    /**
     * Return an array of classes whose instances this animal eats.
     * @return A Class array of edible species.
     */
    abstract protected Class[] retrieveEdible();

    /**
     * Used to create a new animal of this type.
     * @param field A field to put the animal in.
     * @param loc The location of this new animal in that field.
     * @return A freshly born animal
     */
    abstract protected Animal createNewborn(Field field, Location loc);
}