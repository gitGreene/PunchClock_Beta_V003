package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;

//Todo: MutableLiveData is used if you want to change the livedata outside of the viewmodel

public class TimerViewModel extends AndroidViewModel {
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Variables for the transient data we need in Detail Activity
     */

    private LiveData<String> timeToShow = new MutableLiveData<>();
    private CountDownTimer timer;
    private Category currentCategory;
    private long initialTime;
    private FormatMillis form = new FormatMillis();

    public LiveData<String> getTimerTime() {

        return timeToShow;
    }


    public void startTimer() {
        initialTime = SystemClock.elapsedRealtime();
        timer = new CountDownTimer(86400000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentCategory.setDisplayTime(SystemClock.elapsedRealtime() - initialTime);
                ((MutableLiveData<String>) timeToShow).setValue(form.FormatMillisIntoHMS(currentCategory.getDisplayTime()));

            }
            @Override
            public void onFinish() {
            }
        }.start();
    }

    public void pauseTimer() {
        timer.cancel();
    }

    /**
     * Getters/Setters
     */

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

}
