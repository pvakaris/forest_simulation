/**
 * Abstract class representing weather.
 * Weather can affect living beings in different ways.
 * Weather "acts" on living beings every time it changes
 * (that is, every time the simulation changes from one
 * weather type to another).
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public abstract class Weather
{
    /**
     * Creates an object of type Weather.
     */
    public Weather()
    {
        // nothing to initialise here
    }

    /**
     * Return that the weather is unknown if none is specified in the simulation.
     * @return "Unknown".
     */
    public String toString()
    {
        return "Unknown";
    }

    // ABSTRACT METHODS

    /**
     * Used to act on a particular living being in the simulation.
     * @param livingBeing A living being (animal/plant) weather acts on.
     */
    abstract public void act(LivingBeing livingBeing);
}
