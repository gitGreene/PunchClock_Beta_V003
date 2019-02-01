package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
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
//Todo: //? now means this line is in review

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
    private long initialTime;
    private CountDownTimer timer;
    private Category currentCategory;

    // Create formatMillis class instance
    FormatMillis form = new FormatMillis();

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

        // Creates references for recView, chronometer, textViews and buttons
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        timerView = findViewById(R.id.detailTimer);
        categoryView = findViewById(R.id.categoryView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        commitButton = findViewById(R.id.commitButton);



        // Get intent
        GetIntentExtras();

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

        // Get a link to the ViewModels
        timerVM = ViewModelProviders.of(this).get(TimerViewModel.class);
        categoryVM = ViewModelProviders.of(this).get(CategoryViewModel.class);



        categoryVM.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                // Avoid Null errors
                if(categories != null || categories.size() != 0) {

                    // Get the current Category
                    currentCategory = categories.get(categoryID-1);
                    timerVM.setCurrentCategory(currentCategory);

                    timerVM.getTimerTime().observe(DetailActivity.this , new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String time) {
                            if (time != null) {
                                timerView.setText(time);
                            } else {
                                timerView.setText(R.string.default_timer);
                            }
                        }
                    });

                    // Set categoryView text
                    categoryView.setText(currentCategory.getCategory());

                    // Logic based on timerRunning
                    if(currentCategory.isTimerRunning()) { // If timer was running, restart it with the correct values
                        // Get the time that the app was destroyed for
                        currentCategory.setTimeAfterLife(SystemClock.elapsedRealtime() - currentCategory.getTimeAfterLife());

                        // Set the xxxxx to the adjusted time and start the timer
                        initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime() - currentCategory.getTimeAfterLife();
                        timerVM.startTimer();
                        StartEnabledButtons();
                    } else { // If timer was not running, set the timerView and enable the right buttons
                        setTimer();
                        if(currentCategory.getDisplayTime() > 0){
                            PauseEnabledButtons();
                        } else {
                            DefaultEnabledButtons();
                        }
                    }

                    // Set the favoriteIcon correctly
                    if (currentCategory.isFavorite()){
                        favoriteIcon.setChecked(true);
                    } else {
                        favoriteIcon.setChecked(false);
                    }


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


        /**
         * Favorites
         */
        // Initiates Favorite Icon and Animation
        favoriteIcon = findViewById(R.id.category_card_favicon);
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
                   // categoryVM.updateCategory(currentCategory);
                } else {
                    currentCategory.setFavorite(false);
                   // categoryVM.updateCategory(currentCategory);
                }
            }
        });
    } // End of onCreate

    /**
     * onPause/onDestroy
     */

    @Override
    protected void onPause() {
        super.onPause();
        // Todo: Review options
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Todo: Review options
        // Set TimerViewModel data and update Category in database
        currentCategory.setTimeAfterLife(SystemClock.elapsedRealtime());
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
     * Timer Buttons onClick
     */

    public void startButton(View view) {
        // Get the initialTime and start the Timer
        initialTime = SystemClock.elapsedRealtime() - currentCategory.getDisplayTime();
        timerVM.startTimer();
        StartEnabledButtons();
        if (currentCategory.getDisplayTime() == 0) {
            currentCategory.setStartTime(new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date()));
        }
        currentCategory.setTimerRunning(true);
    }

    public void pauseButton(View view) {
        // Cancel the timer
        currentCategory.setTimerRunning(false);
        timerVM.pauseTimer();
        PauseEnabledButtons();

    }

    public void resetButton(View view) {
        // Reset timer
        currentCategory.setDisplayTime(0);
        setTimer();
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
        setTimer();
        DefaultEnabledButtons();
    }











    /**
     * Timer Methods
     */

    public void setTimer() {
        timerView.setText(form.FormatMillisIntoHMS(currentCategory.getDisplayTime()));
    }
//
//    public void startTimer() {
//        timer = new CountDownTimer(86400000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                currentCategory.setDisplayTime(SystemClock.elapsedRealtime() - initialTime);
//                setTimer(currentCategory.getDisplayTime());
//            }
//            @Override
//            public void onFinish() {
//                Toast.makeText(getApplicationContext(), "I wonder if any user will ever see this?", Toast.LENGTH_LONG).show();
//            }
//        }.start();
//    }
//
//
//

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

    /**
     * Additional Methods
     */

    public void GetIntentExtras() {
        // Get extras from Intent
        categoryID = getIntent().getIntExtra("category_id", 0);
    }
}
