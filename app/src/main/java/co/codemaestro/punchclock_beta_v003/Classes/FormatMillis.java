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
        hours = hours % 60;
        // Update the timer and do String.format magic
        time = (String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        return time;
    }

    public String FormatMillisIntoHMMeridiem(long milliseconds) {
        int hours, minutes;

        //Turn milliseconds into ints and h/m
        minutes = (int)(milliseconds/60000);
        hours = minutes/60;

        minutes = minutes % 60;

        time = (String.format("%02d", hours) + ":" + String.format("%02d", minutes));

        if (milliseconds >= 43200000) {
            time = time + "am";
        } else {
            time = time+ "pm";
        }
        return time;
    }
}
