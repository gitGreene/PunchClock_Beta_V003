package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Adapters.DetailTimeBankAdapter;
import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

// Insignificant change detected by github sensors
public class DetailActivity extends AppCompatActivity {
    private CategoryViewModel detailViewModel;

    // Initiates RecView Chronometer, buttons, timerView and creating a Handler for the runnable
    private RecyclerView detailRecyclerView;
    private Chronometer chronometer;
    private ToggleButton startButton, pauseButton, resetButton;
    private Button commitButton;
    private ToggleButton favoriteIcon;
    public TextView categoryView;

    // Variables for timer code
    private long totalTime, timeAfterLife, timeOnDestroy, timeOnCreate, commitTime;
    Boolean timerRunning = false;
    String categoryTitleString, timeStr;
    int categoryID;


    // Variables for sharedPrefs
    private static final String PREFS_FILE_DETAIL = "DetailSharedPreferences";
    private static final int PREFS_MODE_DETAIL = Context.MODE_PRIVATE;
    private static final String totalTimeKey = "co.codemaestro.punchclock_beta_v003.GreenKey";
    private static final String timerRunningKey = "co.codemaestro.punchclock_beta_v003.BlueKey";
    private static final String timeOnDestroyKey = "co.codemaestro.punchclock_beta_v003.RedKey";
    private static final String categoryTitleKey = "co.codemaestro.punchclock_beta_v003.PurpleKey";


    private Category currentCategory;

    // Create formatMillis class instance
    FormatMillis format = new FormatMillis();

    // Long for storing the baseTime of chronometer for updating the view
    long baseMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get extras from Intent
        categoryID = getIntent().getIntExtra("category_id", 0);
        categoryTitleString = getIntent().getStringExtra("category_title");

        // Initiates Shared Preferences
        // Use sharedPrefs to get saved data or set them to defaults
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        totalTime = prefs.getLong(totalTimeKey, 0);
        timeOnDestroy = prefs.getLong(timeOnDestroyKey, 0);
        timerRunning = prefs.getBoolean(timerRunningKey, false);

        // Creates references for recView, chronometer, textViews and buttons
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        chronometer = findViewById(R.id.detailChronometer);
        categoryView = findViewById(R.id.categoryView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        commitButton = findViewById(R.id.commitButton);


        /**
         * Favorites
         */
        // Initiates Favorite Icon and Animation
        favoriteIcon = findViewById(R.id.favorite_icon);
        final ScaleAnimation scaleAnimation =
                new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.7f,
                        Animation.RELATIVE_TO_SELF, 0.7f);

        scaleAnimation.setDuration(200);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);



        favoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);

                if(isChecked) {
                    detailViewModel.setAsFavorite(categoryID);
                } else {
                    detailViewModel.setAsNotFavorite(categoryID);
                }
            }
        });


        /**
         * Logic Based on the Timer Running
         */

        // Timer Logic
        if (timerRunning) {

            // Make timeAfterlife equal to the time the app was terminated
            timeOnCreate = SystemClock.elapsedRealtime();
            timeAfterLife = timeOnCreate - timeOnDestroy;

            // Set chronometer base to current elapsedRT minus the totalTime before app termination minus
            // Thus allowing our timer to be accurate after app reincarnation
            chronometer.setBase(SystemClock.elapsedRealtime() - totalTime - timeAfterLife);
            baseMillis = SystemClock.elapsedRealtime() - totalTime - timeAfterLife;
            chronometer.start();

            // If timer is running, disable startButton
            startButton.setEnabled(false);
            resetButton.setEnabled(false);

        } else {
            // if timer is not running, setEnabled buttons
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);

            // Set baseMillis
            baseMillis = SystemClock.elapsedRealtime() - totalTime;

            // If totalTime is zero, then the timer has no value, else set timer to saved time
            if(totalTime == 0) {
                chronometer.setText(R.string.default_timer);
                resetButton.setEnabled(false);
            } else {
                chronometer.setText(format.FormatMillisIntoHMS(SystemClock.elapsedRealtime() - baseMillis));
                resetButton.setEnabled(true);
            }
        }

        /**
         * Chronometer Listener
         */
        // Chronometer onTick Listener and Time Formatting
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long millisInTimer;
                millisInTimer = SystemClock.elapsedRealtime() - baseMillis;
                chronometer.setText(format.FormatMillisIntoHMS(millisInTimer));
            }
        });

        /**
         * RecyclerView
         */
        // RecyclerView
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setHasFixedSize(false);
        final DetailTimeBankAdapter adapter = new DetailTimeBankAdapter();
        detailRecyclerView.setAdapter(adapter);


        /**
         * ViewModel
         */
        // Get a link to the ViewModel
        detailViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        // Observer for categoryView
        detailViewModel.getCategoryByTitle(categoryTitleString).observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category category) {
                //Set categoryView text
                categoryView.setText(category.getCategory());
            }
        });

        // Observer for detailRecyclerView
        detailViewModel.getCategoryTimeBanks(categoryID).observe(this, new Observer<List<TimeBank>>() {
            @Override
            public void onChanged(@Nullable List<TimeBank> timeBanks) {
                adapter.setTimeBanks(timeBanks);
            }
        });

        // Observer for updating the totalTime
        detailViewModel.getCategoryTimeSum(categoryID).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long sumTime) {

                // Avoid null exceptions when there is no entries into the timebank for that categoryId
                if (sumTime == null) {
                    sumTime = 0L;
                }
                // Update the category with the correct sumTime aka totalTime
                detailViewModel.updateCategory(new Category(categoryID, categoryTitleString, sumTime));
            }

        });
    } // End of onCreate


    /**
     * Buttons onClick
     *
     */
    public void startButton(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime() - totalTime);
        baseMillis = SystemClock.elapsedRealtime() - totalTime;
        chronometer.start();
        // timer Started
        timerRunning = true;
//        categoryDao.updateTimerRunningBoolean();


        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resetButton.setEnabled(false);
    }


    public void pauseButton(View view) {
        // Get the time from timer, format it into a string
        commitTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        timeStr = format.FormatMillisIntoHMS(commitTime);

        totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.stop();
        // timer Paused
        timerRunning = false;

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);


    }

    public void resetButton(View view) {
        resetTimer();
    }

    public void commitButton(View view) {
        //Todo: Change the Query to only post times by category
        // If timer is running get new time, otherwise it should use the time we got from pause
        if (timerRunning) {
            // Get the time from timer, format it into a string
            commitTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeStr = format.FormatMillisIntoHMS(commitTime);
        }

        if (commitTime > 0) {
            // Create a timeBank object
            final TimeBank timeBank = new TimeBank(commitTime, categoryID);

            // Insert the data
            detailViewModel.insertTimeBank(timeBank);
        }
        resetTimer();
    }

    public void resetTimer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setText(R.string.default_timer);
        // timer Paused
        timerRunning = false;

        totalTime = 0;
        timeOnDestroy = 0;
        timeOnCreate = 0;
        timeAfterLife = 0;
        baseMillis = 0;
        commitTime = 0;

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    /**
     * SharedPreferences
     */
    // Saved stuff to sharedPrefs
    @Override
    protected void onPause() {
        super.onPause();
        saveToSharedPreferences();
    }

    // Saved stuff to sharedPrefs
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveToSharedPreferences();
    }


    public void saveToSharedPreferences() {
        if (timerRunning) {
            totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeOnDestroy = SystemClock.elapsedRealtime();
        }
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(totalTimeKey, totalTime);
        editor.putLong(timeOnDestroyKey, timeOnDestroy);
        editor.putBoolean(timerRunningKey, timerRunning);
        editor.putString(categoryTitleKey, categoryTitleString);
        editor.apply();
    }

}
