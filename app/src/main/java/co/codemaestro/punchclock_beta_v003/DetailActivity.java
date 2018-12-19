package co.codemaestro.punchclock_beta_v003;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
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
//    private CategoryViewModel DetailViewModel;

    // References for Chronometer, buttons, timerView and creating a Handler for the runnable
    private Chronometer chronometer;
    private Button startButton, pauseButton, resetButton, commitButton;
    private ToggleButton favoriteIcon;
    private boolean startPauseToggle;
    private TextView categoryView;

    // Variables for timer code
    private long totalTime, timeAfterLife, timeOnDestroy, timeOnCreate, totalTimeToCommit;
    Boolean timerRunning = false;
    String categoryString;

    // Variables for sharedPrefs
    private static final String PREFS_FILE_DETAIL = "DetailSharedPreferences";
    private static final int PREFS_MODE_DETAIL = Context.MODE_PRIVATE;
    private static final String totalTimeKey = "co.codemaestro.punchclock_beta_v003.GreenKey";
    private static final String timerRunningKey = "co.codemaestro.punchclock_beta_v003.BlueKey";
    private static final String timeOnDestroyKey = "co.codemaestro.punchclock_beta_v003.RedKey";

    FormatMillis format = new FormatMillis();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        DetailViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);


        // Initiate buttons/textViews and Handler
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        startPauseToggle = false;
        resetButton = findViewById(R.id.resetButton);

        commitButton = findViewById(R.id.commitButton);
        chronometer = findViewById(R.id.detailChronometer);
        categoryView = findViewById(R.id.categoryView);

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

        // Get intent that gives use the category
        categoryString = getIntent().getStringExtra("category_title");

        // Set categoryView as the proper category
        categoryView.setText(categoryString);

        // Use sharedPrefs to get saved data or set them to defaults
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        totalTime = prefs.getLong(totalTimeKey, 0);
        timeOnDestroy = prefs.getLong(timeOnDestroyKey, 0);
        timerRunning = prefs.getBoolean(timerRunningKey, false);

        // If timer is running
        if (timerRunning) {

            // Timer is running, startButton setEnabled to false
            startButton.setEnabled(false);

            // Make timeAfterlife equal to the time the app was terminated
            timeOnCreate = SystemClock.elapsedRealtime();
            timeAfterLife = timeOnCreate - timeOnDestroy;

            // Set chronometer base to current elapsedRT minus the totalTime before app termination minus
            // Thus allowing our timer to be accurate after app reincarnation
            chronometer.setBase(SystemClock.elapsedRealtime() - totalTime - timeAfterLife);
            chronometer.start();
        } else {
            // If timer is not running, display the accurately paused time
            chronometer.setBase(SystemClock.elapsedRealtime() - totalTime);
        }


        // Method that sets OnClickListeners for all four buttons(better then XML because we can do .setEnabled)
        ButtonsOnClick();

//      TODO: Do we need this?
//      catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }

    public void startPause(View view) {


    }



    public void ButtonsOnClick() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chronometer.setBase(SystemClock.elapsedRealtime() - totalTime);
                chronometer.start();

                // timer Started
                timerRunning = true;
                startButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();

                // timer Paused
                timerRunning = false;
                startButton.setEnabled(true);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());

                totalTime = 0;
                timeOnDestroy = 0;
                timeOnCreate = 0;
                timeAfterLife = 0;

                // timer Paused
                timerRunning = false;
            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: Stop timer and save it in the database
                totalTimeToCommit = SystemClock.elapsedRealtime() - chronometer.getBase();
                Toast.makeText(DetailActivity.this, "If this worked, it would commit the number " + format.FormatMillisIntoHMS(totalTimeToCommit), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Saved stuff to sharedPrefs
    @Override
    protected void onPause() {
        super.onPause();

        // If timer is running
        if (timerRunning) {
            totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeOnDestroy = SystemClock.elapsedRealtime();
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(totalTimeKey, totalTime);
        editor.putLong(timeOnDestroyKey, timeOnDestroy);
        editor.putBoolean(timerRunningKey, timerRunning);
        editor.apply();
    }

    // Saved stuff to sharedPrefs
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timerRunning) {
            totalTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeOnDestroy = SystemClock.elapsedRealtime();
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_DETAIL, PREFS_MODE_DETAIL);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(totalTimeKey, totalTime);
        editor.putLong(timeOnDestroyKey, timeOnDestroy);
        editor.putBoolean(timerRunningKey, timerRunning);
        editor.apply();
    }

}
