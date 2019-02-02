package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import co.codemaestro.punchclock_beta_v003.Database.Category;

//Todo: MutableLiveData is used if you want to change the livedata outside of the viewmodel

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

    public LiveData<Long> getTimerTime() {
        return timeToShow;
    }


    public void startTimer(final Category category) {
        initialTime = SystemClock.elapsedRealtime() - category.getDisplayTime();
        timer = new CountDownTimer(86400000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((MutableLiveData<Long>) timeToShow).setValue(SystemClock.elapsedRealtime() - initialTime);
                category.setDisplayTime(SystemClock.elapsedRealtime() - initialTime);
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }
    public void continueTimer(Category category) {
//Todo: Does countdown timer survive configuration changes
        if (category.isTimerRunning()) {

            // Gives us the time the activity related to this category was dead
            category.setTimeAfterLife(SystemClock.elapsedRealtime() - category.getTimeAfterLife());

            initialTime = SystemClock.elapsedRealtime() - category.getDisplayTime() - category.getTimeAfterLife();
            timer = new CountDownTimer(86400000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ((MutableLiveData<Long>) timeToShow).setValue(SystemClock.elapsedRealtime() - initialTime);
                }

                @Override
                public void onFinish() {
                }
            }.start();

        } else {
            ((MutableLiveData<Long>) timeToShow).setValue(category.getDisplayTime());
        }
    }

    public void pauseTimer() {
        timer.cancel();
    }

}
