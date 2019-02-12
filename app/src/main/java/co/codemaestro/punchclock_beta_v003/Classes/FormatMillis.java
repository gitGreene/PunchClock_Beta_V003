package co.codemaestro.punchclock_beta_v003.Classes;

public class FormatMillis {
    private String time;

    public String FormatMillisIntoHMS(long milliseconds) {

        int seconds, minutes, hours;

        //Turn millisecondsLong into ints and h/m/s
        seconds = (int) (milliseconds/1000);
        minutes = seconds/60;
        hours = minutes/60;
        // Mod those ints to keep them from 0-59
        seconds = seconds % 60;
        minutes = minutes % 60;

        // Update the timer and do String.format magic
        time = (String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        return time;
    }

    public String FormatMillisIntoDHM(long milliseconds) {
        int minutes, hours, days;
        minutes = (int)(milliseconds/60000);
        hours = minutes/60;
        days = hours/24;
        minutes = minutes % 60;
        hours = hours % 24;
        time = days+"d"+hours+"h"+minutes+"m";
        return time;
    }
}
