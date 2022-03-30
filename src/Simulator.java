import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A predator-prey simulator, based on a rectangular field
 * containing different species in a "forest" habitat.
 *
 * This is the main class of the application. It holds the fields, the weather, the
 * time tracker, the living beings themselves and the species simulated.
 *
 * The simulation works in "steps". Every step, all actors will act in a certain way.
 *
 * @author David J. Barnes, Michael Kölling, Flavio Melinte Citea and Vakaris Paulavičius
 * @version 5.0
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // A randomizer object for the simulation
    private static final Random rand = Randomizer.getRandom();

    // List of animals in the field.
    private List<LivingBeing> livingBeings;
    // A list of diseases animals can get infected with.
    private List<Disease> diseases;
    // A list of the types of weather that can occur.
    private List<Weather> typesOfWeather;
    // A chance that at any step the wether will change.
    private static final double chanceOfWeatherChange = 0.1;
    // The field where depicted animals are.
    private Field animalField;
    // The field where invisible plants grow.
    private Field plantField;
    // Current weather in the simulation.
    private Weather weather;
    // Time tracker for the simulation
    private TimeTracker timeTracker;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // Maps classes of animal species to their initial creation probability.
    private Map<Class, Double> animalSpecies;
    // Maps classes of plant species to their initial creation probability.
    private Map<Class, Double> plantSpecies;

    public static void main(String[] args)
    {
        Simulator simulator = new Simulator();
        simulator.runLongSimulation();
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        setSpecies();
        setDiseases();
        setTypesOfWeather();

        // By default the weather is sunny
        weather = new SunnyWeather();
        timeTracker = new TimeTracker(0, 24, 21, 5);
        // Initialize fields and animal list
        livingBeings = new ArrayList<>();
        animalField = new Field(depth, width, timeTracker);
        plantField = new Field(depth, width, timeTracker);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

        setHuntingFields();

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(animalField); step++) {
            simulateOneStep();
            delay(100);
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        increaseTimeOfDay();

        changeWeather();

        // Provide space for newborn animals.
        List<LivingBeing> newLivingBeings = new ArrayList<>();
        // Let all rabbits act.
        for(Iterator<LivingBeing> it = livingBeings.iterator(); it.hasNext(); ) {
            LivingBeing livingBeing = it.next();
            randomlyInfect(livingBeing);
            livingBeing.act(newLivingBeings);
            if(! livingBeing.isAlive()) {
                it.remove();
            }
        }

        // Add the new living beings to the list.
        livingBeings.addAll(newLivingBeings);

        view.showStatus(step, timeTracker.toString(), weather.toString(), animalField);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        livingBeings.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, timeTracker.toString(), weather.toString(), animalField);
    }

    /**
     * Return a field where plants grow.
     * @return The plant field.
     */
    public Field getPlantField()
    {
        return plantField;
    }

    /**
     * Return a field where depicted animals are.
     * @return The animal field.
     */
    public Field getAnimalField()
    {
        return animalField;
    }

    // PRIVATE METHODS

    /**
     * Determines the species simulated, as well as their initial creation probability.
     */
    private void setSpecies()
    {
        animalSpecies = new HashMap<>();
        plantSpecies = new HashMap<>();

        // Animals
        animalSpecies.put(Lynx.class, 0.02);
        animalSpecies.put(Wolf.class, 0.02);
        animalSpecies.put(Deer.class, 0.08);
        animalSpecies.put(Hamster.class, 0.08);
        // Plants
        plantSpecies.put(Plant.class, 0.20);
    }

    /**
     * Randomly populate the fields with animals and plants, respectively.
     */
    private void populate()
    {
        populateField(animalField, animalSpecies);
        populateField(plantField, plantSpecies);
    }

    /**
     * Populates a field with living beings of certain species.
     * @param field The field to be populated.
     * @param species A map between classes of species and their creation probabilities.
     */
    private void populateField(Field field, Map<Class, Double> species)
    {
        field.clear();

        Set<Class> speciesSet = species.keySet();

        // Take every location.
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                // Take every species.
                for(Class speciesClass : speciesSet) {
                    // Decide if a being is placed in the location, based on its species' creation probability.
                    if(rand.nextDouble() <= species.get(speciesClass)) {
                        // Only living beings are allowed.
                        if(LivingBeing.class.isAssignableFrom(speciesClass)) {
                            Location location = new Location(row, col);
                            // Try to create a new living being.
                            try {
                                LivingBeing newBeing = (LivingBeing) speciesClass
                                        .getConstructor(boolean.class, Field.class, Location.class)
                                        .newInstance(true, field, location);
                                livingBeings.add(newBeing);
                                break;
                            }
                            catch (Exception e) {
                                // In case of an exception, don't populate.
                                System.out.println("Field " + field.toString() + " not populated because exception " +
                                        e.toString() + " was caught ");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Used to increment the TimeTracker
     */
    private void increaseTimeOfDay()
    {
        timeTracker.increaseTime();
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    /**
     * Try to infect a living being at every step with all of the available diseases.
     * @param livingBeing A living being which to infect.
     */
    private void randomlyInfect(LivingBeing livingBeing)
    {
        // Check if it is an animal, because in this version of the simulation, only animals can have diseases.
        if(livingBeing instanceof Animal)
        {
            // Cast it to Animal class
            Animal animal = (Animal) livingBeing;
            for(Disease disease : diseases)
            {
                // Try to infect it with every disease (the probability is very low)
                Random rand = animal.getRand();
                if(disease.canInfect(livingBeing) && rand.nextDouble() <= disease.getRandomInfectionProbability())
                {
                    animal.infectWith(disease);
                }
            }
        }
    }

    /**
     * Used to set the hunting fields for all animal classes.
     */
    private void setHuntingFields()
    {
        Hamster.setHuntingField(plantField);
        Lynx.setHuntingField(animalField);
        Wolf.setHuntingField(animalField);
        Deer.setHuntingField(plantField);
    }

    /**
     * Used to specify the diseases that are available in this simulation.
     */
    private void setDiseases()
    {
        diseases = new ArrayList<>();

        Disease wolfFlu = new WolfFlu();
        Disease animalFlu = new AnimalFlu();

        diseases.add(wolfFlu);
        diseases.add(animalFlu);
    }

    /**
     * Used to specify all the available types of weather in this simulation.
     */
    private void setTypesOfWeather()
    {
        typesOfWeather = new ArrayList<>();

        Weather sunnyWeather = new SunnyWeather();
        Weather rainyWeather = new RainyWeather();

        typesOfWeather.add(rainyWeather);
        typesOfWeather.add(sunnyWeather);
    }

    /**
     * Used to change the current simulation weather.
     */
    private void changeWeather()
    {
        double newDouble = rand.nextDouble();
        int newInt = rand.nextInt(typesOfWeather.size());

        if(newDouble <= chanceOfWeatherChange) {
            setWeather(typesOfWeather.get(newInt));
            weatherAffectAll();
        }
    }

    /**
     * Used when the weather changes. This method affects the behaviour of a single living being given the new weather.
     */
    private void weatherAffectOne(LivingBeing livingBeing)
    {
        if(weather != null && livingBeing != null) {
            weather.act(livingBeing);
        }
    }

    /**
     * Used when the weather changes. This method affects the behaviour of all the living beings that are in the simulation.
     */
    private void weatherAffectAll()
    {
        for(LivingBeing being : livingBeings) {
            weatherAffectOne(being);
        }
    }

    /**
     * Used to set a new weather.
     * @param newWeather A new weather set change to.
     */
    private void setWeather(Weather newWeather)
    {
        if(newWeather != null) {
            weather = newWeather;
        }
    }
}
