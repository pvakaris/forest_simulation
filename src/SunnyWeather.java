/**
 * Represents the "default" weather, where all living beings
 * have their probability to breed reset to its default value.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class SunnyWeather extends Weather
{
    /**
     * Creates an object of type SunnyWeather.
     */
    public SunnyWeather()
    {
        super();
    }

    /**
     * Return the description of the weather.
     * @return "Sunny".
     */
    public String toString()
    {
        return "Sunny";
    }

    /**
     * Used to act on a particular living being in the simulation.
     *
     * SunnyWeather restores the default breeding probability value for the specified living being.
     *
     * @param livingBeing A living being sunny weather acts on.
     */
    public void act(LivingBeing livingBeing)
    {
        livingBeing.setBreedingProbability(livingBeing.getDefaultBreedingProbability());
    }
}
