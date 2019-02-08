package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
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
import co.codemaestro.punchclock_beta_v003.ViewModel.TimerViewModel;

//Todo: Figure out why this "Broadcast of Intent" error with all the percentages is happening(ITS HAPPENING!!! *RON PAUL GIF*)

public class DetailActivity extends AppCompatActivity {
    private CategoryViewModel categoryVM;
    private TimerViewModel timerVM;

    // Initiates RecView Chronometer, buttons, timerView and creating a Handler for the runnable
    private RecyclerView detailRecyclerView;
    private ToggleButton startButton, pauseButton, resetButton, commitButton, favoriteIcon;
    public TextView categoryView, timerView;

    // Variables for Database and UI
    boolean nightModeEnabled;
    int categoryID;
    private Category currentCategory;

    // Create formatMillis class instance
    FormatMillis form = new FormatMillis();

    // Variables for sharedPrefs
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";
    private static final String TAG = "DetailActivity";

    Handler handler;

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

        // Creates references for recView, chronometer, textViews and buttons
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        timerView = findViewById(R.id.detailTimer);
        categoryView = findViewById(R.id.detail_activity_category_title);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        commitButton = findViewById(R.id.commitButton);

        // Get intent
        categoryID = getIntent().getIntExtra("category_id", 0);

        /**
         * RecyclerView
         */
        // RecyclerView
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setHasFixedSize(false);
        final DetailTimeBankAdapter adapter = new DetailTimeBankAdapter();
        detailRecyclerView.setAdapter(adapter);

        // Runnable for timer
        handler = new Handler();


        /**
         * ViewModel
         */

        // Get a link to the ViewModels
        timerVM = ViewModelProviders.of(this).get(TimerViewModel.class);
        categoryVM = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryVM.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                Log.e(TAG, "getAllCategoriesObserver");
                // Get the current Category
                currentCategory = categories.get(categoryID-1);

                // Set categoryView text
                categoryView.setText(currentCategory.getCategory());

                if (currentCategory.isTimerRunning()) {
                    handler.postDelayed(timerRun, 1000 );// to work on mainThread, consider new Thread instead
                    StartEnabledButtons();
                }

                // Set buttons enabled
                if(currentCategory.getDisplayTime() > 0){
                    PauseEnabledButtons();
                } else {
                    DefaultEnabledButtons();
                }

                // Set the favoriteIcon correctly
                if (currentCategory.isFavorite()){
                    favoriteIcon.setChecked(true);
                } else {
                    favoriteIcon.setChecked(false);
                }
            }
        });

        // Observer - Updates data set for detailRecyclerView
        categoryVM.getCategoryTimeBanks(categoryID).observe(this, new Observer<List<TimeBank>>() {
            @Override
            public void onChanged(@Nullable List<TimeBank> timeBanks) {
                adapter.setTimeBanks(timeBanks);
            }
        });

        timerVM.getTimerTime().observe(DetailActivity.this , new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long time) {
                timerView.setText(form.FormatMillisIntoHMS(time));
                currentCategory.setDisplayTime(time);

            }
        });


        /**
         * Favorites
         */
        // Initiates Favorite Icon and Animation
        favoriteIcon = findViewById(R.id.detail_activity_favicon);
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
                    currentCategory.setFavorite(true);
                } else {
                    currentCategory.setFavorite(false);
                }
            }
        });
    } // End of onCreate


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Todo: Review options
        // Set TimerViewModel data and update Category in database

        if (currentCategory.isTimerRunning()) {
            currentCategory.setTimeAtDeath(SystemClock.elapsedRealtime());
            handler.removeCallbacks(timerRun);
        } else {
            currentCategory.setTimeAtDeath(0);
        }
        categoryVM.updateCategory(currentCategory);
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
                    prefs.edit().putBoolean(nightModeBooleanKey, true).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    prefs.edit().putBoolean(nightModeBooleanKey, false).apply();
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
     * Timer Buttons onClick
     */

    public void startButton(View view) {
        // Get the initialTime and start the Timer
        currentCategory.setTimerRunning(true);
        currentCategory.setTimeAtDeath(0);
        handler.postDelayed(timerRun, 1000);
        StartEnabledButtons();
        if (currentCategory.getDisplayTime() == 0) {
            currentCategory.setStartTime(new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date()));
        }
    }

    public void pauseButton(View view) {
        // Cancel the timer
        handler.removeCallbacks(timerRun);
        currentCategory.setTimerRunning(false);
        currentCategory.setTimeAtDeath(0);
        PauseEnabledButtons();

    }

    public void resetButton(View view) {
        // Reset timer
        currentCategory.setDisplayTime(0);
        currentCategory.setTimeAtDeath(0);
        timerView.setText(form.FormatMillisIntoHMS(currentCategory.getDisplayTime()));
        DefaultEnabledButtons();
    }

    public void commitButton(View view) {
        // Create a timeBank object with relevant endTime/Date and insert it into the database
        TimeBank timeBank = new TimeBank(currentCategory.getDisplayTime(),
                                         currentCategory.getStartTime(), new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date()),
                                         new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date()), categoryID);
        categoryVM.insertTimeBank(timeBank);

        // Update the totalTime
        currentCategory.setTotalTime(currentCategory.getTotalTime() + currentCategory.getDisplayTime());

        // Reset timer
        currentCategory.setDisplayTime(0);
        timerView.setText(form.FormatMillisIntoHMS(currentCategory.getDisplayTime()));
        DefaultEnabledButtons();
    }

    /**
     * SetEnabled Timer Button Methods - Three states of buttons
     */

    public void StartEnabledButtons() {
        // timer Started
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resetButton.setEnabled(false);
        commitButton.setEnabled(false);
    }

    public void PauseEnabledButtons() {
        // timer Paused
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);
        commitButton.setEnabled(true);
    }

    public void DefaultEnabledButtons() {
        // timer Reset
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(false);
        commitButton.setEnabled(false);
    }

    //StopWatch Logic
    public Runnable timerRun = new Runnable() {
        public void run() {
            timerVM.setTimer(currentCategory);
            handler.postDelayed(this, 1000);
        }
    };
}
