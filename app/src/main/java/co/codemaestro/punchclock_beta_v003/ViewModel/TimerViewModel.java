package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import co.codemaestro.punchclock_beta_v003.Database.Category;

public class TimerViewModel extends AndroidViewModel {
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Variables for the transient data we need in Detail Activity
     */
    private LiveData<Long> timeToShow = new MutableLiveData<>();
    private CountDownTimer timer;
    private long initialTime;
    private Category currentCategory;


    // Method that returns liveData object which can be observed
    public LiveData<Long> getTimerTime() {
        return timeToShow;
    }


    // Method for starting the timer
    public void startTimer() {

        if (currentCategory.isTimerRunning() && currentCategory.getTimeAfterLife() > 0 ){
            // Gives us the time the activity related to this category was dead
            currentCategory.setTimeAfterLife(SystemClock.elapsedRealtime() - currentCategory.getTimeAfterLife());
            initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime() - currentCategory.getTimeAfterLife();
        } else {
            initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime();
        }

        timer = new CountDownTimer(86400000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((MutableLiveData<Long>) timeToShow).setValue(SystemClock.elapsedRealtime() - initialTime);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    // Method for setting the timer value
    public void setTimer() {
        ((MutableLiveData<Long>) timeToShow).setValue(currentCategory.getDisplayTime());
    }

    // Method for killing the timer aka pausing it
    public void pauseTimer() {
        timer.cancel();
    }

    // Setter for currentCategory
    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }
}
