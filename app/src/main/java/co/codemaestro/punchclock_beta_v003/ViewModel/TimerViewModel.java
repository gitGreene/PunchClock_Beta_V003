package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;

public class TimerViewModel extends AndroidViewModel {

    /**
     * Variables for the transient data we need in Detail Activity
     */
    private int categoryID;
    private String categoryTitleString;
    private long displayTime;
    private long timeAfterLife;
    private boolean timerRunning;
    private boolean isFavorite;

    public TimerViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Getters/Setters
     */
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitleString() {
        return categoryTitleString;
    }

    public void setCategoryTitleString(String categoryTitleString) {
        this.categoryTitleString = categoryTitleString;
    }

    public long getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(long displayTime) {
        this.displayTime = displayTime;
    }

    public long getTimeAfterLife() {
        return timeAfterLife;
    }

    public void setTimeAfterLife(long timeAfterLife) {
        this.timeAfterLife = timeAfterLife;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
