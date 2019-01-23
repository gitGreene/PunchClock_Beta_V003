package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.codemaestro.punchclock_beta_v003.Adapters.DetailTimeBankAdapter;
import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

//Todo: Go into the theme editor and figure out what the fuck is going on with some of these resources, praticularly nav bar color
//Todo: Figure out why this "Broadcast of Intent" error with all the percentages is happening(ITS HAPPENING!!! *RON PAUL GIF*)
//Todo: //? now means this line is in review

// Insignificant change detected by github sensors
public class DetailActivity extends AppCompatActivity {
    private CategoryViewModel detailViewModel;

    // Initiates RecView Chronometer, buttons, timerView and creating a Handler for the runnable
    private RecyclerView detailRecyclerView;
    private Chronometer chronometer;
    private ToggleButton startButton, pauseButton, resetButton;
    //Todo: Make this button real
    private Button commitButton;
    private ToggleButton favoriteIcon;
    public TextView categoryView, testTextView;

    // Variables for timer code
    private long categoryCurrentTime, categoryTimeAfterLife, sumOfTimes;
    private Category currentCategory;
    boolean timerRunning, nightModeEnabled, isFavorite;
    String categoryTitleString, timeStr, currentDate;
    int categoryID;

    WeakReference<TextView> testTextViewReference;

    // Create formatMillis class instance
    FormatMillis format = new FormatMillis();

    // Variables for sharedPrefs
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Use sharedPrefs to get saved data or set them to defaults
        final SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
        nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);
        if (nightModeEnabled) {
            // Set the night mode theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get extras from Intent
        categoryID = getIntent().getIntExtra("category_id", 0);
        categoryTitleString = getIntent().getStringExtra("category_title");
        categoryCurrentTime = getIntent().getLongExtra("category_current_time", 0l);
        categoryTimeAfterLife = getIntent().getLongExtra("pleasework", 0l);
        timerRunning = getIntent().getBooleanExtra("category_is_running", false);

        // Creates references for recView, chronometer, textViews and buttons
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        chronometer = findViewById(R.id.detailChronometer);
        categoryView = findViewById(R.id.categoryView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        commitButton = findViewById(R.id.commitButton);
        testTextView = findViewById(R.id.tempView4);

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

        // Set categoryView to Category Title String
        detailViewModel.testGetCategoryTitleString(categoryView, categoryID);
        detailViewModel.setCategoryTitle(categoryID);



        // Observer for categoryView
        detailViewModel.getCategoryByTitle(categoryTitleString).observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category category) {

                // Set currentCategory
                currentCategory = category;

                // Set the favoriteIcon correctly
                if (category.isFavorite()){
                    favoriteIcon.setChecked(true);
                } else {
                    favoriteIcon.setChecked(false);
                }
            }
        });

        // Observer - Updates data set for detailRecyclerView
        detailViewModel.getCategoryTimeBanks(categoryID).observe(this, new Observer<List<TimeBank>>() {
            @Override
            public void onChanged(@Nullable List<TimeBank> timeBanks) {
                adapter.setTimeBanks(timeBanks);
            }
        });

        // Observer - Gives us the sumTime of current category in a public variable sumOfTime
        detailViewModel.getCategoryTimeSum(categoryID).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long sumTime) {

                // Avoid null exceptions when there is no entries into the timebank for that categoryId
                if (sumTime == null) {
                    sumTime = 0L;
                }
                sumOfTimes = sumTime;
            }
        });


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
                    isFavorite = true;
                } else {
                    isFavorite = false;
                }
            }
        });


        /**
         * Logic Based on the Timer Running
         */

        // Timer Logic
        if (timerRunning) { // Only runs on relaunch when the timer is running

            // Use categoryTimeAfterLife and categoryCurrentTime to set the proper base and start the chrono
            categoryTimeAfterLife = SystemClock.elapsedRealtime() - categoryTimeAfterLife;
            chronometer.setBase(SystemClock.elapsedRealtime() - categoryCurrentTime - categoryTimeAfterLife);
            chronometer.start();

            // If timer is running, disable startButton
            startButton.setEnabled(false);
            resetButton.setEnabled(false);

        } else {
            // if timer is not running, setEnabled buttons
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);

            // If totalTime is zero, then the timer has no value, else set timer to saved time
            if(categoryCurrentTime == 0) {
                chronometer.setText(R.string.default_timer);
                resetButton.setEnabled(false);
            } else {
                chronometer.setText(format.FormatMillisIntoHMS(categoryCurrentTime));
                resetButton.setEnabled(true);
            }
        }

        /**
         * Chronometer Listener
         */
        // Chronometer onTick Listener and Time Formatting
        // This allows us to use the functionality of the Chronometer but to format it the way we desire
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometer.setText(format.FormatMillisIntoHMS(SystemClock.elapsedRealtime() - chronometer.getBase()));

                // Set the afterLife time to adjust the timer on restart while the timer isRunning
                categoryTimeAfterLife = SystemClock.elapsedRealtime();

            }
        });
    } // End of onCreate


    /**
     * Buttons onClick
     *
     */
    public void startButton(View view) {
        // Set the base accurate to our currentTime and start the timer
        chronometer.setBase(SystemClock.elapsedRealtime() - categoryCurrentTime);
        chronometer.start();

        // timer Started
        timerRunning = true;
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resetButton.setEnabled(false);
    }


    public void pauseButton(View view) {
        // Get the date as a string(In case they commit during pause)
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Get the time from timer, format it into a string
        categoryCurrentTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        timeStr = format.FormatMillisIntoHMS(categoryCurrentTime);

        // timer Paused
        chronometer.stop();
        timerRunning = false;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);
    }

    public void resetButton(View view) {
        resetTimer();
    }

    public void commitButton(View view) {
        // If timer is running get new time, otherwise it should use the time we got from pause
        if (timerRunning) {
            // Get the current date
            currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            // Get the time from timer, format it into a string
            categoryCurrentTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            timeStr = format.FormatMillisIntoHMS(categoryCurrentTime);
        }

        if (categoryCurrentTime > 0) {
            // Create a timeBank object
            TimeBank timeBank = new TimeBank(categoryCurrentTime, currentDate, categoryID);

            // Insert the data
            detailViewModel.insertTimeBank(timeBank);

            // Update the category
            currentCategory.setTimeAfterLife(0);
            if (sumOfTimes == 0) {
                sumOfTimes = categoryCurrentTime;
            }
            currentCategory.setTotalTime(sumOfTimes);
            detailViewModel.updateCategory(currentCategory);
        }
        resetTimer();
    }

    public void resetTimer() {
        // Timer stops, shows 00:00:00 and timerRunning false
        chronometer.stop();
        chronometer.setText(R.string.default_timer);
        timerRunning = false;

        // Update the database category with the accurate data, currentTime to become "0"
        categoryCurrentTime = 0;

        // Enabled/Disable buttons
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(false);
    }


    /**
     *
     * Options menu
     *
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    // Differs slightly from the version in the MainActivity with the intent at the end
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nightMode:
                Toast.makeText(this, "Night Mode Activate!", Toast.LENGTH_SHORT).show();

                // Get shared Prefs reference and toggle NightMode
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
                if (!nightModeEnabled) {
                    nightModeEnabled = true;
                } else {
                    nightModeEnabled = false;
                }

                // Save the correct nightModeEnabled value to preferences and change the app to nightMode
                if (nightModeEnabled) {
                    //Save "nightModeOn = true" to sharedPref and....
                    prefs.edit().putBoolean(nightModeBooleanKey, true).apply();

                    // Set the night mode theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    //Save "nightModeOn = false" to sharedPref and...
                    prefs.edit().putBoolean(nightModeBooleanKey, false).apply();

                    // Set the theme as not being night mode yo
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Differs from Main activity so that we start from the Main Activity on reload
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * onPause/onDestroy
     */
    // Saved stuff to sharedPrefs
    @Override
    protected void onPause() {
        super.onPause();

        // Todo: Find a better way?
        // Prevent null - Update the category with the correct sumTime aka totalTime
        // if (currentCategory == null) { currentCategory = new Category("CategoryFormerlyKnownAsNull", -100000, -100000, 0, false, false); }
        currentCategory.setCurrentTime(categoryCurrentTime);
        currentCategory.setTimeAfterLife(categoryTimeAfterLife);
        currentCategory.setTimerRunning(timerRunning);
        currentCategory.setFavorite(isFavorite);
        detailViewModel.updateCategory(currentCategory);
    }

    // Saved stuff to sharedPrefs
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
