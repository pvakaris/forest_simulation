import java.awt.Color;

/**
 * Interface for "drawable" actors in the simulation.
 * That is, objects with a color. Has a method to
 * retrieve the instance's color, as well as the
 * "base" color of the entire concrete class.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public interface Drawable
{
    /**
     * Return the current color of the actor who can be drawn in the simulator view.
     * @return Color of the drawable instance.
     */
    Color getColor();

    /**
     * Return the default color that belongs to the class of the actor.
     * @return Default color of drawable instance's class.
     */
    Color getDefaultColor();
}