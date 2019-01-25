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
    public int getCategoryIDTimer() {
        return categoryID;
    }

    public void setCategoryIDTimer(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitleStringTimer() {
        return categoryTitleString;
    }

    public void setCategoryTitleStringTimer(String categoryTitleString) {
        this.categoryTitleString = categoryTitleString;
    }

    public long getDisplayTimeTimer() {
        return displayTime;
    }

    public void setDisplayTimeTimer(long displayTime) {
        this.displayTime = displayTime;
    }

    public long getTimeAfterLifeTimer() {
        return timeAfterLife;
    }

    public void setTimeAfterLifeTimer(long timeAfterLife) {
        this.timeAfterLife = timeAfterLife;
    }

    public boolean isTimerRunningTimer() {
        return timerRunning;
    }

    public void setTimerRunningTimer(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    public boolean isFavoriteTimer() {
        return isFavorite;
    }

    public void setFavoriteTimer(boolean favorite) {
        isFavorite = favorite;
    }
}
