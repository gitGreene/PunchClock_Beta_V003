package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import co.codemaestro.punchclock_beta_v003.Database.Category;

//TODO: STAY ON TRACK
public class TimerViewModel extends AndroidViewModel {
    private static final String TAG = "TimerViewModel";
    /**
     * Variables for the transient data we need in Detail Activity
     */
    private LiveData<Long> timeToShow = new MutableLiveData<>();
    private long initialTime, timeAfterLife;
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    // Method that returns liveData object which can be observed
    public LiveData<Long> getTimerTime() {
        return timeToShow;
    }

    // Method for setting the timer value
    public void setTimer(Category currentCategory) {
        if (currentCategory.getTimeAtDeath() > 0) {
            // Gives us the time the activity related to this category was dead
            timeAfterLife = SystemClock.elapsedRealtime() - currentCategory.getTimeAtDeath();
            initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime() - timeAfterLife;
        } else {
            initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime();
        }
        ((MutableLiveData<Long>) timeToShow).setValue(SystemClock.elapsedRealtime() - initialTime);
        Log.e(TAG, "setTimer: " + (SystemClock.elapsedRealtime() - initialTime));
    }
}
