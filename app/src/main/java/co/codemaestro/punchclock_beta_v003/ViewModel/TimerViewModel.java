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
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    // Method that returns liveData object which can be observed
    public LiveData<Long> getTimerTime() {
        return timeToShow;
    }

    // Method for setting the timer value
    public void setTimer(long displayTime) {
        ((MutableLiveData<Long>) timeToShow).setValue(displayTime);
        Log.e(TAG, "setTimer: " + (displayTime));
    }
}
