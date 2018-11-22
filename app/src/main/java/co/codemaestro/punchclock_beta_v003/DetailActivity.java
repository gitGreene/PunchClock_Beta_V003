package co.codemaestro.punchclock_beta_v003;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
//    private CategoryViewModel DetailViewModel;

    // References for buttons, timerView and creating a Handler for the runnable
    private Button startButton, pauseButton, resetButton, commitButton;
    private TextView categoryView, timerView;
    private Handler handler;

    // Variables for timer code
    private long startTime, millisecondsLong;
    private int seconds, minutes, hours;

    String categoryString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        DetailViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);


        // Initiate buttons/textViews and Handler
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        commitButton = findViewById(R.id.commitButton);
        timerView = findViewById(R.id.timerView);
        categoryView = findViewById(R.id.categoryView);
        handler = new Handler();

        // Get intent that gives use the category
        categoryString = getIntent().getStringExtra("category_title");

        // Set categoryView as the proper category
        categoryView.setText(categoryString);


        // Method that sets OnClickListeners for all four buttons(better then XML because we can do .setEnabled)
        ButtonsOnClick();

//      TODO: Do we need this?
//      catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }


    public Runnable runForrestRun = new Runnable() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {

            millisecondsLong = SystemClock.elapsedRealtime() - startTime;


            //Turn millisecondsLong into ints and h/m/s
            seconds = (int) (millisecondsLong/1000);
            minutes = seconds/60;
            hours = minutes/60;

            // Mod those ints to keep them from 0-59
            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 60;


            // Update the timer and do String.format magic
            timerView.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));



            // Rerun the forever
            handler.post(runForrestRun);
        }
    };

    public void ButtonsOnClick() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get the startTime - elapsedRealTime in millis since computer boot
                startTime = SystemClock.elapsedRealtime();

                handler.post(runForrestRun);
                startButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: Stop timer and restart it, persisting the state of the timer
                Toast.makeText(DetailActivity.this, "This pause button doesn't work you jabroni", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: Fix other buttons enabled states

                // Stops the runnable
                handler.removeCallbacks(runForrestRun);

                //Sets the timer to 00:00:00
                timerView.setText(R.string.default_timer);

                // Reset start button to Enabled in case it isn't
                startButton.setEnabled(true);
            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: Stop timer and save it in the database
                Toast.makeText(DetailActivity.this, "commit doesn't work rn", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
