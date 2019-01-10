package co.codemaestro.punchclock_beta_v003;

class FormatMillis {
    String time;

    String FormatMillisIntoHMS(long milliseconds) {

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

    String FormatLongIntoHMS(Long milliseconds) {

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


}
