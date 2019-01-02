package co.codemaestro.punchclock_beta_v003;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity {
    private CategoryViewModel detailViewModel;

    // References for Chronometer, buttons, timerView and creating a Handler for the runnable
    private Chronometer chronometer;
    private ToggleButton startButton, pauseButton, resetButton;
    private Button commitButton;
    private ToggleButton favoriteIcon;
    public TextView categoryView;

    // Variables for timer code
    private long totalTime, timeAfterLife, timeOnDestroy, timeOnCreate, totalTimeToCommit;
    Boolean timerRunning = false;
    String categoryTitleString;
    CategoryDao categoryDao;
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

        // TODO:
        // when activity is created, use ID to check how much time is in bank
        // display time bank value
        // display timer if running
        // display zeros if not

        categoryID = getIntent().getIntExtra("category_id", 0);
        detailViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        currentCategory = detailViewModel.getById(categoryID);

        categoryView = findViewById(R.id.categoryView);


        Toast.makeText(this, "", Toast.LENGTH_LONG).show();

        //Initiates the Category Title from Intent Data
//        categoryView = findViewById(R.id.categoryView);
//        categoryTitleString = getIntent().getStringExtra("category_title");
//        categoryView.setText(categoryTitleString);




//      TODO: Find out how ViewModel should integrate with DetailActivity
//        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
//        DetailViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);


        // Initiates Chronometer
        chronometer = findViewById(R.id.detailChronometer);

        // Initiates Start Button
        startButton = findViewById(R.id.startButton);

        // Initiates Pause Button
        pauseButton = findViewById(R.id.pauseButton);

        // Initiates Reset Button
        resetButton = findViewById(R.id.resetButton);

        // Initiates Commit Button
        commitButton = findViewById(R.id.commitButton);


        // Initiates Favorite Icon and Animation
        favoriteIcon = findViewById(R.id.favorite_icon);
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(200);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        favoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);
            }
        });


        // Initiates Shared Preferences
        // Use sharedPrefs to get saved data or set them to defaults
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        totalTime = prefs.getLong(totalTimeKey, 0);
        timeOnDestroy = prefs.getLong(timeOnDestroyKey, 0);
        timerRunning = prefs.getBoolean(timerRunningKey, false);
//        currentCategory = prefs.getString(categoryTitleKey, null);



        /*Logic Based on the Timer Running*/

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

        // Chronometer onTick Listener and Time Formatting

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long millisInTimer;
                millisInTimer = SystemClock.elapsedRealtime() - baseMillis;
                chronometer.setText(format.FormatMillisIntoHMS(millisInTimer));
            }
        });
    }

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
        totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.stop();
        // timer Paused
        timerRunning = false;

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);
    }

    public void resetButton(View view) {
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

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    public void commitButton(View view) {
        //Todo: Stop timer and save it in the database
        totalTimeToCommit = SystemClock.elapsedRealtime() - chronometer.getBase();
        Toast.makeText(DetailActivity.this, "If this worked, it would commit the number " + format.FormatMillisIntoHMS(totalTimeToCommit), Toast.LENGTH_SHORT).show();
    }

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
