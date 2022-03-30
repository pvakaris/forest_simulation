/**
 * A class that tracks time. The number of "times" (can be seen as hours)
 * can be defined. Every time of day can be either part of "day" or "night".
 * The time at which night starts and ends can also be defined.
 *
 * @author Flavio Melinte Citea and Vakaris Paulavičius
 * @version 1.0
 */
public class TimeTracker
{
    // Current time of the day.
    private int timeOfDay;
    // How many hours a day has.
    private int maxTimeOfDay;
    private int nightStartingTime;
    private int nightEndingTime;

    /**
     * Create a new time tracker.
     * @param initialTime Hour to initialise tracker at.
     * @param maxTimeOfDay How long a day is.
     * @param nightStartingTime An hour when the night starts.
     * @param nightEndingTime An hour when the night ends.
     */
    public TimeTracker(int initialTime, int maxTimeOfDay, int nightStartingTime, int nightEndingTime)
    {


        timeOfDay = (initialTime >= 0) ? initialTime : (-initialTime);
        timeOfDay %= maxTimeOfDay; // keep value below maximum
        this.maxTimeOfDay = (maxTimeOfDay >= 0) ? maxTimeOfDay : (-maxTimeOfDay);
        // Can be any value, to allow for days with only night and viceversa, for example.
        this.nightStartingTime = nightStartingTime;
        this.nightEndingTime = nightEndingTime;
    }

    /**
     * Return the current time of the day.
     * @return Current time.
     */
    public int getTimeOfDay()
    {
        return timeOfDay;
    }

    /**
     * Increase the time by one hour.
     */
    public void increaseTime()
    {
        timeOfDay++;
        timeOfDay %= maxTimeOfDay;
    }

    /**
     * Return if it is currently a night.
     * @return true if it is a night.
     */
    public boolean isNight()
    {
        if(nightStartingTime < nightEndingTime) {
            return timeOfDay >= nightStartingTime && timeOfDay <= nightEndingTime;
        }
        else {
            return timeOfDay >= nightStartingTime || timeOfDay <= nightEndingTime;
        }
    }

    /**
     * Return the time in a readable String format
     * @return "Day" if it is day, "Night" if it is a night
     */
    public String toString()
    {
        if(isNight()) {
            return "Night";
        }
        else {
            return "Day";
        }
    }
}
