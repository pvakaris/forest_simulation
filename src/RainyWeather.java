/**
 * Represents rainy weather, where animals have their probability
 * to breed halved, while plants have their proobability to spread
 * doubled.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class RainyWeather extends Weather
{
    /**
     * Creates an object of type RainyWeather.
     */
    public RainyWeather()
    {
        super();
    }

    /**
     * Return the description of the weather.
     * @return "Raining".
     */
    public String toString()
    {
        return "Raining";
    }

    /**
     * Used to act on a particular living being in the simulation.
     *
     * RainyWeather reduces the breeding of animals by a half and doubles
     * the plants breeding probability.
     *
     * @param livingBeing A living being (animal/plant) rainy weather acts on.
     */
    public void act(LivingBeing livingBeing)
    {
        if(livingBeing instanceof Animal) {
            livingBeing.setBreedingProbability(livingBeing.getBreedingProbability() / 2);
        }
        else if(livingBeing instanceof Plant) {
            livingBeing.setBreedingProbability(livingBeing.getBreedingProbability() * 2);
        }
    }
}
