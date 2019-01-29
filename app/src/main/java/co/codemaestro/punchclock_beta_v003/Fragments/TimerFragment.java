package co.codemaestro.punchclock_beta_v003.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryViewHolder;
import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String displayTimeKey = "co.codemaestro.punchclock_beta_v003.displayTimeKey";
    private static final String timerRunningKey = "co.codemaestro.punchclock_beta_v003.timerRunningKey";
    private static final String timeAfterLifeKey = "co.codemaestro.punchclock_beta_v003.timeAfterLifeKey";
    private CategoryViewModel categoryVM;
    private ToggleButton startButton, pauseButton, resetButton, commitButton;
    private Spinner categorySpinner;
    private TextView mainTimerView;
    private FormatMillis format = new FormatMillis();
    private long startTime, displayTime, timeAfterLife;
    private CountDownTimer timer;
    private boolean timerRunning;


    public TimerFragment() {
        // Required empty public constructor
    }

    private static TimerFragment newInstance() {
        return new TimerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        // Get our saved values
        GetSharedPrefs();

        // Get references for views
        mainTimerView = view.findViewById(R.id.mainTimer);
        startButton = view.findViewById(R.id.mainStartButton);
        pauseButton = view.findViewById(R.id.mainPauseButton);
        resetButton = view.findViewById(R.id.mainResetButton);
        commitButton = view.findViewById(R.id.mainCommitButton);
        categorySpinner = view.findViewById(R.id.spinner);

        // set onClickListeners
        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        commitButton.setOnClickListener(this);


        // Create an array of Strings
        final ArrayList<String> categoryList = new ArrayList<String>();
        // Creating adapter for spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categoryList);
        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Observable for getting all Categories
        categoryVM = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryVM.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {

                // Avoid Null errors
                if(categories == null || categories.size() == 0) {
                    // No data in your database, call your api for data
                } else {
                    // Turn the list into an array we can use for the spinner
                    categoryList.clear();
                    for (int i = 0; i < categories.size(); i++) {
                        final Category current = categories.get(i);
                        categoryList.add(current.getCategory());
                    }
                }
                // Attaching data adapter to spinner
                categorySpinner.setAdapter(adapter);
            }
        });

        // Spinner
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Toast.makeText(getContext(), "Category: " + categorySpinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Toast.makeText(getContext(), "Do nothing I guess", Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * Logic Based on the Timer Running/isFavorite
         */

        if(timerRunning) { // If timer was running, restart it with the correct values
            // Get the time that the app was destroyed for
            timeAfterLife = SystemClock.elapsedRealtime() - timeAfterLife;

            // Set the startTime to the adjusted time and start the timer
            startTime = SystemClock.elapsedRealtime() - displayTime - timeAfterLife;
            startTimer();
            StartEnabledButtons();

        } else { // If timer was not running, set the timerView and enable the right buttons
            setTimer(displayTime);
            if(displayTime > 0){
                PauseEnabledButtons();
            } else {
                DefaultEnabledButtons();
            }
        }

        return view;
    } // End onCreate View

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Save our values
        timeAfterLife = SystemClock.elapsedRealtime();
        SaveSharedPrefs();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Save our values
        timeAfterLife = SystemClock.elapsedRealtime();
        SaveSharedPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainStartButton:
                // Get the startTime and start the Timer
                startTime = SystemClock.elapsedRealtime() - displayTime;
                startTimer();
                timerRunning = true;
                StartEnabledButtons();
                break;
            case R.id.mainPauseButton:
                // Cancel the timer
                timer.cancel();
                timerRunning = false;
                PauseEnabledButtons();
                break;
            case R.id.mainResetButton:
                // Reset timer
                displayTime = 0;
                setTimer(0);
                DefaultEnabledButtons();
                break;
            case R.id.mainCommitButton:

                Toast.makeText(getContext(), "This doesn't do anything", Toast.LENGTH_SHORT).show();
                // Reset timer
                displayTime = 0;
                setTimer(0);
                DefaultEnabledButtons();
                break;
        }
    }

    /**
     * Timer Methods
     */

    public void startTimer() {
        timer = new CountDownTimer(86400000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                displayTime = SystemClock.elapsedRealtime() - startTime;
                setTimer(displayTime);
            }
            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }


    public void setTimer(long displayTime) {
        mainTimerView.setText(format.FormatMillisIntoHMS(displayTime));
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

    /**
     * SharedPreferences
     */

    public void GetSharedPrefs() {
        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_FILE, PREFS_MODE);
        displayTime = prefs.getLong(displayTimeKey, 0);
        timerRunning = prefs.getBoolean(timerRunningKey, false);
        timeAfterLife = prefs.getLong(timeAfterLifeKey, 0);
    }

    public void SaveSharedPrefs(){
        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_FILE, PREFS_MODE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(displayTimeKey, displayTime);
        editor.putBoolean(timerRunningKey, timerRunning);
        editor.putLong(timeAfterLifeKey, timeAfterLife);
        editor.apply();
    }
}
